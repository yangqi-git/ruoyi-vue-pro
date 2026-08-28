package cn.iocoder.yudao.module.property.service.bill;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.bill.vo.PropertyBillDetailSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.bill.PropertyBillDetailDO;
import cn.iocoder.yudao.module.property.dal.mysql.bill.PropertyBillDetailMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyBillDetailServiceImpl implements PropertyBillDetailService {

    @Resource private PropertyBillDetailMapper detailMapper;

    @Override public Long createDetail(PropertyBillDetailSaveReqVO reqVO) {
        PropertyBillDetailDO detail = BeanUtils.toBean(reqVO, PropertyBillDetailDO.class);
        detailMapper.insert(detail);
        return detail.getId();
    }

    @Override public void updateDetail(PropertyBillDetailSaveReqVO reqVO) {
        validateDetailExists(reqVO.getId());
        PropertyBillDetailDO detail = BeanUtils.toBean(reqVO, PropertyBillDetailDO.class);
        detailMapper.updateById(detail);
    }

    @Override public void deleteDetail(Long id) {
        validateDetailExists(id);
        detailMapper.deleteById(id);
    }

    @Override public PropertyBillDetailDO getDetail(Long id) {
        return detailMapper.selectById(id);
    }

    @Override public PageResult<PropertyBillDetailDO> getDetailPage(Long billId, Long itemId, Integer payStatus, Integer pageNo, Integer pageSize) {
        return detailMapper.selectPage(billId, itemId, payStatus, pageNo, pageSize);
    }

    @Override public List<PropertyBillDetailDO> getDetailList() {
        return detailMapper.selectList();
    }

    @Override public List<PropertyBillDetailDO> getDetailListByBillId(Long billId) {
        return detailMapper.selectListByBillId(billId);
    }

    @Override public List<PropertyBillDetailDO> getDetailListByItemId(Long itemId) {
        return detailMapper.selectListByItemId(itemId);
    }

    @Override public List<PropertyBillDetailDO> getDetailListByPayStatus(Integer payStatus) {
        return detailMapper.selectListByPayStatus(payStatus);
    }

    private void validateDetailExists(Long id) {
        if (detailMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.BILL_DETAIL_NOT_EXISTS);
        }
    }
}