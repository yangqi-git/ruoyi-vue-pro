package cn.iocoder.yudao.module.property.service.cashier;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.cashier.PropertyCashierRecordDO;
import cn.iocoder.yudao.module.property.controller.admin.cashier.vo.PropertyCashierRecordSaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyCashierRecordService {

    Long createRecord(@Valid PropertyCashierRecordSaveReqVO reqVO);

    void updateRecord(@Valid PropertyCashierRecordSaveReqVO reqVO);

    void deleteRecord(Long id);

    PropertyCashierRecordDO getRecord(Long id);

    PageResult<PropertyCashierRecordDO> getRecordPage(Long projectId, String recordNo, Long billId, Long houseId, Long communityId, Integer payType, Integer payStatus, Integer recordStatus, Integer pageNo, Integer pageSize);

    List<PropertyCashierRecordDO> getRecordList();

    List<PropertyCashierRecordDO> getRecordListByBillId(Long billId);

    List<PropertyCashierRecordDO> getRecordListByHouseId(Long houseId);

    List<PropertyCashierRecordDO> getRecordListByCommunityId(Long communityId);

    List<PropertyCashierRecordDO> getRecordListByPayStatus(Integer payStatus);
}
