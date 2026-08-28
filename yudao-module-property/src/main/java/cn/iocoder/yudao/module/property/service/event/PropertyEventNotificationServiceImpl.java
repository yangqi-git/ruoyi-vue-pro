package cn.iocoder.yudao.module.property.service.event;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventNotificationPageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventNotificationReceiptReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventNotificationDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventRelationDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventTimelineDO;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyResidentDO;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventNotificationMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventRelationMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventTimelineMapper;
import cn.iocoder.yudao.module.property.dal.mysql.space.PropertyResidentMapper;
import cn.iocoder.yudao.module.property.service.org.PropertyProjectService;
import cn.iocoder.yudao.module.system.api.sms.SmsSendApi;
import cn.iocoder.yudao.module.system.api.sms.dto.send.SmsSendSingleToUserReqDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_CONCURRENT_UPDATE;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_NOT_EXISTS;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_NOTIFICATION_NOT_EXISTS;

@Service
public class PropertyEventNotificationServiceImpl implements PropertyEventNotificationService {

    private static final DateTimeFormatter NUMBER_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String SMS_TEMPLATE_CODE = "property-event-status";

    @Resource
    private PropertyEventNotificationMapper notificationMapper;
    @Resource
    private PropertyEventMapper eventMapper;
    @Resource
    private PropertyEventRelationMapper relationMapper;
    @Resource
    private PropertyEventTimelineMapper timelineMapper;
    @Resource
    private PropertyResidentMapper residentMapper;
    @Resource
    private PropertyProjectService projectService;
    @Resource
    private SmsSendApi smsSendApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int enqueueStatusNotification(PropertyEventDO event, String notificationType, String content) {
        List<PropertyResidentDO> recipients = findRecipients(event);
        int created = 0;
        for (PropertyResidentDO resident : recipients) {
            if (StrUtil.isBlank(resident.getPhone())) {
                continue;
            }
            String idempotencyKey = event.getId() + ":" + notificationType + ":"
                    + resident.getId() + ":" + event.getVersion();
            if (notificationMapper.selectByIdempotencyKey(idempotencyKey) != null) {
                continue;
            }
            notificationMapper.insert(PropertyEventNotificationDO.builder()
                    .messageNo(generateNumber()).projectId(event.getProjectId()).eventId(event.getId())
                    .residentId(resident.getId()).recipientName(resident.getName())
                    .recipientMobile(resident.getPhone()).notificationType(notificationType)
                    .channel("SMS").templateCode(SMS_TEMPLATE_CODE).content(content)
                    .idempotencyKey(idempotencyKey).status(0).retryCount(0).version(0).build());
            created++;
        }
        if (created > 0) {
            timelineMapper.insert(PropertyEventTimelineDO.builder()
                    .eventId(event.getId()).projectId(event.getProjectId()).action("QUEUE_NOTIFICATION")
                    .fromStatus(event.getStatus()).toStatus(event.getStatus()).operatorUserId(getLoginUserId())
                    .detail(notificationType + " 已生成 " + created + " 条外部消息")
                    .eventVersion(event.getVersion()).build());
        }
        return created;
    }

    @Override
    public int processPendingNotifications() {
        int processed = 0;
        LocalDateTime now = LocalDateTime.now();
        for (PropertyEventNotificationDO notification : notificationMapper.selectPendingList(now)) {
            try {
                SmsSendSingleToUserReqDTO reqDTO = new SmsSendSingleToUserReqDTO();
                reqDTO.setMobile(notification.getRecipientMobile());
                reqDTO.setTemplateCode(notification.getTemplateCode());
                Map<String, Object> params = new HashMap<>();
                PropertyEventDO event = eventMapper.selectById(notification.getEventId());
                params.put("eventNo", event == null ? notification.getEventId() : event.getEventNo());
                params.put("status", notification.getNotificationType());
                params.put("content", notification.getContent());
                reqDTO.setTemplateParams(params);
                Long smsLogId = smsSendApi.sendSingleSmsToMember(reqDTO);
                notification.setStatus(10);
                notification.setSentTime(now);
                notification.setExternalMessageId(String.valueOf(smsLogId));
                notification.setFailureReason(null);
                notification.setNextRetryTime(null);
            } catch (Exception ex) {
                int retryCount = notification.getRetryCount() == null ? 1 : notification.getRetryCount() + 1;
                notification.setStatus(30);
                notification.setRetryCount(retryCount);
                notification.setFailureReason(StrUtil.maxLength(ex.getMessage(), 1000));
                notification.setNextRetryTime(now.plusMinutes((long) retryCount * retryCount));
            }
            notificationMapper.updateById(notification);
            processed++;
        }
        return processed;
    }

    @Override
    public PageResult<PropertyEventNotificationDO> getPage(PropertyEventNotificationPageReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        return notificationMapper.selectPage(reqVO);
    }

    @Override
    public List<PropertyEventNotificationDO> getListByEventId(Long eventId, Long projectId) {
        validateEvent(eventId, projectId);
        return notificationMapper.selectListByEventId(eventId);
    }

    @Override
    public void retry(Long id, Long projectId) {
        PropertyEventNotificationDO notification = notificationMapper.selectById(id);
        if (notification == null || !Objects.equals(notification.getProjectId(), projectId)) {
            throw exception(EVENT_NOTIFICATION_NOT_EXISTS);
        }
        notification.setStatus(0);
        notification.setRetryCount(0);
        notification.setNextRetryTime(null);
        notification.setFailureReason(null);
        if (notificationMapper.updateById(notification) == 0) {
            throw exception(EVENT_CONCURRENT_UPDATE);
        }
    }

    @Override
    public void recordReceipt(PropertyEventNotificationReceiptReqVO reqVO) {
        PropertyEventNotificationDO notification = notificationMapper.selectByMessageNo(reqVO.getMessageNo());
        if (notification == null || !Objects.equals(notification.getProjectId(), reqVO.getProjectId())) {
            throw exception(EVENT_NOTIFICATION_NOT_EXISTS);
        }
        notification.setExternalMessageId(StrUtil.blankToDefault(
                reqVO.getExternalMessageId(), notification.getExternalMessageId()));
        if (Boolean.TRUE.equals(reqVO.getDelivered())) {
            notification.setStatus(20);
            notification.setDeliveredTime(LocalDateTime.now());
            notification.setFailureReason(null);
        } else {
            notification.setStatus(30);
            notification.setFailureReason(reqVO.getFailureReason());
            notification.setNextRetryTime(LocalDateTime.now().plusMinutes(1));
        }
        if (notificationMapper.updateById(notification) == 0) {
            throw exception(EVENT_CONCURRENT_UPDATE);
        }
    }

    private List<PropertyResidentDO> findRecipients(PropertyEventDO event) {
        List<PropertyEventDO> affectedEvents = new ArrayList<>();
        affectedEvents.add(event);
        for (PropertyEventRelationDO relation : relationMapper.selectListByEventId(event.getId())) {
            if (Objects.equals(relation.getMainEventId(), event.getId())) {
                PropertyEventDO related = eventMapper.selectById(relation.getRelatedEventId());
                if (related != null) {
                    affectedEvents.add(related);
                }
            }
        }
        Map<Long, PropertyResidentDO> residents = new LinkedHashMap<>();
        for (PropertyEventDO affected : affectedEvents) {
            List<PropertyResidentDO> list = affected.getHouseId() != null
                    ? residentMapper.selectListByHouseId(affected.getHouseId())
                    : affected.getCommunityId() != null
                    ? residentMapper.selectListByCommunityId(affected.getCommunityId()) : List.of();
            list.stream().filter(item -> item.getStatus() == null || item.getStatus() == 0)
                    .forEach(item -> residents.put(item.getId(), item));
        }
        return new ArrayList<>(residents.values());
    }

    private PropertyEventDO validateEvent(Long eventId, Long projectId) {
        PropertyEventDO event = eventMapper.selectByIdAndProjectId(eventId, projectId);
        if (event == null) {
            throw exception(EVENT_NOT_EXISTS);
        }
        return event;
    }

    private String generateNumber() {
        return "MSG" + LocalDateTime.now().format(NUMBER_DATE_FORMATTER)
                + IdUtil.fastSimpleUUID().substring(0, 10).toUpperCase();
    }
}
