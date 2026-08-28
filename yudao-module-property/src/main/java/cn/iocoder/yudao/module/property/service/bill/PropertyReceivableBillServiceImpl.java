package cn.iocoder.yudao.module.property.service.bill;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.bill.vo.PropertyReceivableBillSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.bill.PropertyReceivableBillDO;
import cn.iocoder.yudao.module.property.dal.mysql.bill.PropertyReceivableBillMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyReceivableBillServiceImpl implements PropertyReceivableBillService {

    @Resource private PropertyReceivableBillMapper billMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override public Long createBill(PropertyReceivableBillSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        PropertyReceivableBillDO bill = BeanUtils.toBean(reqVO, PropertyReceivableBillDO.class);
        billMapper.insert(bill);
        return bill.getId();
    }

    @Override public void updateBill(PropertyReceivableBillSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateBillExists(reqVO.getId());
        PropertyReceivableBillDO bill = BeanUtils.toBean(reqVO, PropertyReceivableBillDO.class);
        billMapper.updateById(bill);
    }

    @Override public void deleteBill(Long id) {
        validateBillExists(id);
        billMapper.deleteById(id);
    }

    @Override public PropertyReceivableBillDO getBill(Long id) {
        return billMapper.selectById(id);
    }

    @Override public PageResult<PropertyReceivableBillDO> getBillPage(String billNo, Long houseId, Long communityId, Long projectId, Integer billType, Integer payStatus, Integer billStatus, Integer pageNo, Integer pageSize) {
        return billMapper.selectPage(billNo, houseId, communityId, projectId, billType, payStatus, billStatus, pageNo, pageSize);
    }

    @Override public List<PropertyReceivableBillDO> getBillList() {
        return billMapper.selectList();
    }

    @Override public List<PropertyReceivableBillDO> getBillListByHouseId(Long houseId) {
        return billMapper.selectListByHouseId(houseId);
    }

    @Override public List<PropertyReceivableBillDO> getBillListByCommunityId(Long communityId) {
        return billMapper.selectListByCommunityId(communityId);
    }

    @Override public List<PropertyReceivableBillDO> getBillListByPayStatus(Integer payStatus) {
        return billMapper.selectListByPayStatus(payStatus);
    }

    @Override public List<PropertyReceivableBillDO> getBillListByBillStatus(Integer billStatus) {
        return billMapper.selectListByBillStatus(billStatus);
    }

    private void validateBillExists(Long id) {
        if (billMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.RECEIVABLE_BILL_NOT_EXISTS);
        }
    }
}
