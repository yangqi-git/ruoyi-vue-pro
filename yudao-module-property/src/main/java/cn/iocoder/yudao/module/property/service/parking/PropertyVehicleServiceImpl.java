package cn.iocoder.yudao.module.property.service.parking;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.parking.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.parking.PropertyVehicleDO;
import cn.iocoder.yudao.module.property.dal.mysql.parking.PropertyVehicleMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyVehicleServiceImpl implements PropertyVehicleService {

    @Resource private PropertyVehicleMapper vehicleMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override
    public Long createVehicle(PropertyVehicleSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        PropertyVehicleDO vehicle = BeanUtils.toBean(reqVO, PropertyVehicleDO.class);
        vehicleMapper.insert(vehicle);
        return vehicle.getId();
    }

    @Override
    public void updateVehicle(PropertyVehicleSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateVehicleExists(reqVO.getId());
        PropertyVehicleDO vehicle = BeanUtils.toBean(reqVO, PropertyVehicleDO.class);
        vehicleMapper.updateById(vehicle);
    }

    @Override
    public void deleteVehicle(Long id) {
        validateVehicleExists(id);
        vehicleMapper.deleteById(id);
    }

    @Override
    public PropertyVehicleDO getVehicle(Long id) {
        return vehicleMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyVehicleDO> getVehiclePage(Long projectId, Long communityId, Long residentId,
            Integer vehicleStatus, Integer pageNo, Integer pageSize) {
        return vehicleMapper.selectPage(projectId, communityId, residentId, vehicleStatus, pageNo, pageSize);
    }

    @Override
    public List<PropertyVehicleDO> getVehicleListByResidentId(Long residentId) {
        return vehicleMapper.selectListByResidentId(residentId);
    }

    @Override
    public List<PropertyVehicleDO> getVehicleListByCommunityId(Long communityId) {
        return vehicleMapper.selectListByCommunityId(communityId);
    }

    @Override
    public PropertyVehicleDO getVehicleByPlateNo(String plateNo) {
        return vehicleMapper.selectByPlateNo(plateNo);
    }

    private void validateVehicleExists(Long id) {
        if (vehicleMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.VEHICLE_NOT_EXISTS);
        }
    }
}
