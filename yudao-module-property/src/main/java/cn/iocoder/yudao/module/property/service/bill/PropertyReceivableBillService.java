package cn.iocoder.yudao.module.property.service.bill;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.bill.PropertyReceivableBillDO;
import cn.iocoder.yudao.module.property.controller.admin.bill.vo.PropertyReceivableBillSaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyReceivableBillService {

    Long createBill(@Valid PropertyReceivableBillSaveReqVO reqVO);

    void updateBill(@Valid PropertyReceivableBillSaveReqVO reqVO);

    void deleteBill(Long id);

    PropertyReceivableBillDO getBill(Long id);

    PageResult<PropertyReceivableBillDO> getBillPage(String billNo, Long houseId, Long communityId, Long projectId, Integer billType, Integer payStatus, Integer billStatus, Integer pageNo, Integer pageSize);

    List<PropertyReceivableBillDO> getBillList();

    List<PropertyReceivableBillDO> getBillListByHouseId(Long houseId);

    List<PropertyReceivableBillDO> getBillListByCommunityId(Long communityId);

    List<PropertyReceivableBillDO> getBillListByPayStatus(Integer payStatus);

    List<PropertyReceivableBillDO> getBillListByBillStatus(Integer billStatus);
}