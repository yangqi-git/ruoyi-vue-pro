package cn.iocoder.yudao.module.property.service.cashier;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.cashier.vo.PropertyWriteOffDetailSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.cashier.PropertyWriteOffDetailDO;
import cn.iocoder.yudao.module.property.dal.mysql.cashier.PropertyWriteOffDetailMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyWriteOffDetailServiceImpl implements PropertyWriteOffDetailService {

    @Resource private PropertyWriteOffDetailMapper detailMapper;

    @Override public Long createDetail(PropertyWriteOffDetailSaveReqVO reqVO) {
        PropertyWriteOffDetailDO detail = BeanUtils.toBean(reqVO, PropertyWriteOffDetailDO.class);
        detailMapper.insert(detail);
        return detail.getId();
    }

    @Override public void updateDetail(PropertyWriteOffDetailSaveReqVO reqVO) {
        validateDetailExists(reqVO.getId());
        PropertyWriteOffDetailDO detail = BeanUtils.toBean(reqVO, PropertyWriteOffDetailDO.class);
        detailMapper.updateById(detail);
    }

    @Override public void deleteDetail(Long id) {
        validateDetailExists(id);
        detailMapper.deleteById(id);
    }

    @Override public PropertyWriteOffDetailDO getDetail(Long id) {
        return detailMapper.selectById(id);
    }

    @Override public PageResult<PropertyWriteOffDetailDO> getDetailPage(Long recordId, Long billId, Integer writeOffType, Integer status, Integer pageNo, Integer pageSize) {
        return detailMapper.selectPage(recordId, billId, writeOffType, status, pageNo, pageSize);
    }

    @Override public List<PropertyWriteOffDetailDO> getDetailList() {
        return detailMapper.selectList();
    }

    @Override public List<PropertyWriteOffDetailDO> getDetailListByRecordId(Long recordId) {
        return detailMapper.selectListByRecordId(recordId);
    }

    @Override public List<PropertyWriteOffDetailDO> getDetailListByBillId(Long billId) {
        return detailMapper.selectListByBillId(billId);
    }

    @Override public List<PropertyWriteOffDetailDO> getDetailListByStatus(Integer status) {
        return detailMapper.selectListByStatus(status);
    }

    private void validateDetailExists(Long id) {
        if (detailMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.WRITE_OFF_DETAIL_NOT_EXISTS);
        }
    }
}