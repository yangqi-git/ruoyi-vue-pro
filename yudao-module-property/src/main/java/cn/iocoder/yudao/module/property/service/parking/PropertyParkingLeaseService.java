package cn.iocoder.yudao.module.property.service.parking;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.parking.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.parking.PropertyParkingLeaseDO;
import java.util.List;

public interface PropertyParkingLeaseService {

    Long createParkingLease(PropertyParkingLeaseSaveReqVO reqVO);

    void updateParkingLease(PropertyParkingLeaseSaveReqVO reqVO);

    void deleteParkingLease(Long id);

    PropertyParkingLeaseDO getParkingLease(Long id);

    PageResult<PropertyParkingLeaseDO> getParkingLeasePage(Long projectId, Long parkingSpotId, Long communityId,
            Long residentId, Integer leaseType, Integer leaseStatus, Integer pageNo, Integer pageSize);

    List<PropertyParkingLeaseDO> getParkingLeaseListByParkingSpotId(Long parkingSpotId);

    List<PropertyParkingLeaseDO> getParkingLeaseListByResidentId(Long residentId);

    void approveParkingLease(Long id, Long approverId, Integer approvalStatus, String approvalRemark);
}
