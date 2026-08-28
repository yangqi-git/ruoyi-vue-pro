package cn.iocoder.yudao.module.property.service.discount;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.discount.vo.PropertyDiscountRecordSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.discount.PropertyDiscountRecordDO;
import cn.iocoder.yudao.module.property.dal.mysql.discount.PropertyDiscountRecordMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDateTime;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyDiscountRecordServiceImpl implements PropertyDiscountRecordService {

    @Resource private PropertyDiscountRecordMapper discountRecordMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override
    public Long createDiscountRecord(PropertyDiscountRecordSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        PropertyDiscountRecordDO record = BeanUtils.toBean(reqVO, PropertyDiscountRecordDO.class);
        discountRecordMapper.insert(record);
        return record.getId();
    }

    @Override
    public void updateDiscountRecord(PropertyDiscountRecordSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateDiscountRecordExists(reqVO.getId());
        PropertyDiscountRecordDO record = BeanUtils.toBean(reqVO, PropertyDiscountRecordDO.class);
        discountRecordMapper.updateById(record);
    }

    @Override
    public void deleteDiscountRecord(Long id) {
        validateDiscountRecordExists(id);
        discountRecordMapper.deleteById(id);
    }

    @Override
    public PropertyDiscountRecordDO getDiscountRecord(Long id) {
        return discountRecordMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyDiscountRecordDO> getDiscountRecordPage(Long projectId, Long communityId, Long houseId, Long billId,
            Integer discountType, Integer approvalStatus, Integer pageNo, Integer pageSize) {
        return discountRecordMapper.selectPage(projectId, communityId, houseId, billId, discountType, approvalStatus, pageNo, pageSize);
    }

    @Override
    public List<PropertyDiscountRecordDO> getDiscountRecordListByBillId(Long billId) {
        return discountRecordMapper.selectList(PropertyDiscountRecordDO::getBillId, billId);
    }

    @Override
    public void approveDiscountRecord(Long id, Long approverId, Integer approvalStatus, String approvalRemark) {
        validateDiscountRecordExists(id);
        PropertyDiscountRecordDO record = new PropertyDiscountRecordDO();
        record.setId(id);
        record.setApproverId(approverId);
        record.setApprovalStatus(approvalStatus);
        record.setApprovalRemark(approvalRemark);
        record.setApprovalTime(LocalDateTime.now());
        discountRecordMapper.updateById(record);
    }

    private void validateDiscountRecordExists(Long id) {
        if (discountRecordMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.DISCOUNT_RECORD_NOT_EXISTS);
        }
    }
}
