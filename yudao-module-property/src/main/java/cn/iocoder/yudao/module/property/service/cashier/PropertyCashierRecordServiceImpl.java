package cn.iocoder.yudao.module.property.service.cashier;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.cashier.vo.PropertyCashierRecordSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.cashier.PropertyCashierRecordDO;
import cn.iocoder.yudao.module.property.dal.mysql.cashier.PropertyCashierRecordMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyCashierRecordServiceImpl implements PropertyCashierRecordService {

    @Resource private PropertyCashierRecordMapper recordMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override public Long createRecord(PropertyCashierRecordSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        PropertyCashierRecordDO record = BeanUtils.toBean(reqVO, PropertyCashierRecordDO.class);
        recordMapper.insert(record);
        return record.getId();
    }

    @Override public void updateRecord(PropertyCashierRecordSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateRecordExists(reqVO.getId());
        PropertyCashierRecordDO record = BeanUtils.toBean(reqVO, PropertyCashierRecordDO.class);
        recordMapper.updateById(record);
    }

    @Override public void deleteRecord(Long id) {
        validateRecordExists(id);
        recordMapper.deleteById(id);
    }

    @Override public PropertyCashierRecordDO getRecord(Long id) {
        return recordMapper.selectById(id);
    }

    @Override public PageResult<PropertyCashierRecordDO> getRecordPage(Long projectId, String recordNo, Long billId, Long houseId, Long communityId, Integer payType, Integer payStatus, Integer recordStatus, Integer pageNo, Integer pageSize) {
        return recordMapper.selectPage(projectId, recordNo, billId, houseId, communityId, payType, payStatus, recordStatus, pageNo, pageSize);
    }

    @Override public List<PropertyCashierRecordDO> getRecordList() {
        return recordMapper.selectList();
    }

    @Override public List<PropertyCashierRecordDO> getRecordListByBillId(Long billId) {
        return recordMapper.selectListByBillId(billId);
    }

    @Override public List<PropertyCashierRecordDO> getRecordListByHouseId(Long houseId) {
        return recordMapper.selectListByHouseId(houseId);
    }

    @Override public List<PropertyCashierRecordDO> getRecordListByCommunityId(Long communityId) {
        return recordMapper.selectListByCommunityId(communityId);
    }

    @Override public List<PropertyCashierRecordDO> getRecordListByPayStatus(Integer payStatus) {
        return recordMapper.selectListByPayStatus(payStatus);
    }

    private void validateRecordExists(Long id) {
        if (recordMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CASHIER_RECORD_NOT_EXISTS);
        }
    }
}
