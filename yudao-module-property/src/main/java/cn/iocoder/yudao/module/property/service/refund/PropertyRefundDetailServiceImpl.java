package cn.iocoder.yudao.module.property.service.refund;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.refund.vo.PropertyRefundDetailSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.refund.PropertyRefundDetailDO;
import cn.iocoder.yudao.module.property.dal.mysql.refund.PropertyRefundDetailMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyRefundDetailServiceImpl implements PropertyRefundDetailService {

    @Resource private PropertyRefundDetailMapper detailMapper;

    @Override public Long createDetail(PropertyRefundDetailSaveReqVO reqVO) {
        PropertyRefundDetailDO detail = BeanUtils.toBean(reqVO, PropertyRefundDetailDO.class);
        detailMapper.insert(detail);
        return detail.getId();
    }

    @Override public void updateDetail(PropertyRefundDetailSaveReqVO reqVO) {
        validateDetailExists(reqVO.getId());
        PropertyRefundDetailDO detail = BeanUtils.toBean(reqVO, PropertyRefundDetailDO.class);
        detailMapper.updateById(detail);
    }

    @Override public void deleteDetail(Long id) {
        validateDetailExists(id);
        detailMapper.deleteById(id);
    }

    @Override public PropertyRefundDetailDO getDetail(Long id) {
        return detailMapper.selectById(id);
    }

    @Override public PageResult<PropertyRefundDetailDO> getDetailPage(Long refundId, Long billDetailId, Integer refundStatus, Integer pageNo, Integer pageSize) {
        return detailMapper.selectPage(refundId, billDetailId, refundStatus, pageNo, pageSize);
    }

    @Override public List<PropertyRefundDetailDO> getDetailList() {
        return detailMapper.selectList();
    }

    @Override public List<PropertyRefundDetailDO> getDetailListByRefundId(Long refundId) {
        return detailMapper.selectListByRefundId(refundId);
    }

    @Override public List<PropertyRefundDetailDO> getDetailListByBillDetailId(Long billDetailId) {
        return detailMapper.selectListByBillDetailId(billDetailId);
    }

    @Override public List<PropertyRefundDetailDO> getDetailListByRefundStatus(Integer refundStatus) {
        return detailMapper.selectListByRefundStatus(refundStatus);
    }

    private void validateDetailExists(Long id) {
        if (detailMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.REFUND_DETAIL_NOT_EXISTS);
        }
    }
}