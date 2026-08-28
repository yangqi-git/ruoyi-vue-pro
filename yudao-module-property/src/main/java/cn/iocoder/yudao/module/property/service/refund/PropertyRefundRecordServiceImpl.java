package cn.iocoder.yudao.module.property.service.refund;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.refund.vo.PropertyRefundRecordSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.refund.PropertyRefundRecordDO;
import cn.iocoder.yudao.module.property.dal.mysql.refund.PropertyRefundRecordMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyRefundRecordServiceImpl implements PropertyRefundRecordService {

    @Resource private PropertyRefundRecordMapper refundMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override public Long createRefund(PropertyRefundRecordSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        PropertyRefundRecordDO refund = BeanUtils.toBean(reqVO, PropertyRefundRecordDO.class);
        refundMapper.insert(refund);
        return refund.getId();
    }

    @Override public void updateRefund(PropertyRefundRecordSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateRefundExists(reqVO.getId());
        PropertyRefundRecordDO refund = BeanUtils.toBean(reqVO, PropertyRefundRecordDO.class);
        refundMapper.updateById(refund);
    }

    @Override public void deleteRefund(Long id) {
        validateRefundExists(id);
        refundMapper.deleteById(id);
    }

    @Override public PropertyRefundRecordDO getRefund(Long id) {
        return refundMapper.selectById(id);
    }

    @Override public PageResult<PropertyRefundRecordDO> getRefundPage(Long projectId, String refundNo, Long cashierRecordId, Long billId, Long houseId, Integer refundType, Integer refundStatus, Integer approvalStatus, Integer pageNo, Integer pageSize) {
        return refundMapper.selectPage(projectId, refundNo, cashierRecordId, billId, houseId, refundType, refundStatus, approvalStatus, pageNo, pageSize);
    }

    @Override public List<PropertyRefundRecordDO> getRefundList() {
        return refundMapper.selectList();
    }

    @Override public List<PropertyRefundRecordDO> getRefundListByCashierRecordId(Long cashierRecordId) {
        return refundMapper.selectListByCashierRecordId(cashierRecordId);
    }

    @Override public List<PropertyRefundRecordDO> getRefundListByBillId(Long billId) {
        return refundMapper.selectListByBillId(billId);
    }

    @Override public List<PropertyRefundRecordDO> getRefundListByHouseId(Long houseId) {
        return refundMapper.selectListByHouseId(houseId);
    }

    @Override public List<PropertyRefundRecordDO> getRefundListByRefundStatus(Integer refundStatus) {
        return refundMapper.selectListByRefundStatus(refundStatus);
    }

    private void validateRefundExists(Long id) {
        if (refundMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.REFUND_RECORD_NOT_EXISTS);
        }
    }
}
