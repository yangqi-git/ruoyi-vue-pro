package cn.iocoder.yudao.module.property.service.parking;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.parking.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.parking.PropertyParkingLeaseDO;
import cn.iocoder.yudao.module.property.dal.mysql.parking.PropertyParkingLeaseMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyParkingLeaseServiceImpl implements PropertyParkingLeaseService {

    @Resource private PropertyParkingLeaseMapper parkingLeaseMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override
    public Long createParkingLease(PropertyParkingLeaseSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        PropertyParkingLeaseDO parkingLease = BeanUtils.toBean(reqVO, PropertyParkingLeaseDO.class);
        parkingLeaseMapper.insert(parkingLease);
        return parkingLease.getId();
    }

    @Override
    public void updateParkingLease(PropertyParkingLeaseSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateParkingLeaseExists(reqVO.getId());
        PropertyParkingLeaseDO parkingLease = BeanUtils.toBean(reqVO, PropertyParkingLeaseDO.class);
        parkingLeaseMapper.updateById(parkingLease);
    }

    @Override
    public void deleteParkingLease(Long id) {
        validateParkingLeaseExists(id);
        parkingLeaseMapper.deleteById(id);
    }

    @Override
    public PropertyParkingLeaseDO getParkingLease(Long id) {
        return parkingLeaseMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyParkingLeaseDO> getParkingLeasePage(Long projectId, Long parkingSpotId, Long communityId,
            Long residentId, Integer leaseType, Integer leaseStatus, Integer pageNo, Integer pageSize) {
        return parkingLeaseMapper.selectPage(projectId, parkingSpotId, communityId, residentId, leaseType, leaseStatus, pageNo, pageSize);
    }

    @Override
    public List<PropertyParkingLeaseDO> getParkingLeaseListByParkingSpotId(Long parkingSpotId) {
        return parkingLeaseMapper.selectListByParkingSpotId(parkingSpotId);
    }

    @Override
    public List<PropertyParkingLeaseDO> getParkingLeaseListByResidentId(Long residentId) {
        return parkingLeaseMapper.selectListByResidentId(residentId);
    }

    @Override
    public void approveParkingLease(Long id, Long approverId, Integer approvalStatus, String approvalRemark) {
        validateParkingLeaseExists(id);
        PropertyParkingLeaseDO parkingLease = new PropertyParkingLeaseDO();
        parkingLease.setId(id);
        parkingLease.setApprovalStatus(approvalStatus);
        parkingLeaseMapper.updateById(parkingLease);
    }

    private void validateParkingLeaseExists(Long id) {
        if (parkingLeaseMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.PARKING_LEASE_NOT_EXISTS);
        }
    }
}
