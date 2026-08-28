package cn.iocoder.yudao.module.property.service.discount;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.discount.vo.PropertyAdjustRecordSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.discount.PropertyAdjustRecordDO;
import cn.iocoder.yudao.module.property.dal.mysql.discount.PropertyAdjustRecordMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDateTime;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyAdjustRecordServiceImpl implements PropertyAdjustRecordService {

    @Resource private PropertyAdjustRecordMapper adjustRecordMapper;

    @Override
    public Long createAdjustRecord(PropertyAdjustRecordSaveReqVO reqVO) {
        PropertyAdjustRecordDO record = BeanUtils.toBean(reqVO, PropertyAdjustRecordDO.class);
        adjustRecordMapper.insert(record);
        return record.getId();
    }

    @Override
    public void updateAdjustRecord(PropertyAdjustRecordSaveReqVO reqVO) {
        validateAdjustRecordExists(reqVO.getId());
        PropertyAdjustRecordDO record = BeanUtils.toBean(reqVO, PropertyAdjustRecordDO.class);
        adjustRecordMapper.updateById(record);
    }

    @Override
    public void deleteAdjustRecord(Long id) {
        validateAdjustRecordExists(id);
        adjustRecordMapper.deleteById(id);
    }

    @Override
    public PropertyAdjustRecordDO getAdjustRecord(Long id) {
        return adjustRecordMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyAdjustRecordDO> getAdjustRecordPage(Long communityId, Long houseId, Long billId,
            Integer adjustType, Integer approvalStatus, Integer pageNo, Integer pageSize) {
        return adjustRecordMapper.selectPage(communityId, houseId, billId, adjustType, approvalStatus, pageNo, pageSize);
    }

    @Override
    public List<PropertyAdjustRecordDO> getAdjustRecordListByBillId(Long billId) {
        return adjustRecordMapper.selectList(PropertyAdjustRecordDO::getBillId, billId);
    }

    @Override
    public void approveAdjustRecord(Long id, Long approverId, Integer approvalStatus, String approvalRemark) {
        validateAdjustRecordExists(id);
        PropertyAdjustRecordDO record = new PropertyAdjustRecordDO();
        record.setId(id);
        record.setApproverId(approverId);
        record.setApprovalStatus(approvalStatus);
        record.setApprovalRemark(approvalRemark);
        record.setApprovalTime(LocalDateTime.now());
        adjustRecordMapper.updateById(record);
    }

    private void validateAdjustRecordExists(Long id) {
        if (adjustRecordMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.ADJUST_RECORD_NOT_EXISTS);
        }
    }
}