package cn.iocoder.yudao.module.property.service.parking;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.parking.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.parking.PropertyParkingSpotDO;
import cn.iocoder.yudao.module.property.dal.mysql.parking.PropertyParkingSpotMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyParkingSpotServiceImpl implements PropertyParkingSpotService {

    @Resource private PropertyParkingSpotMapper parkingSpotMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override
    public Long createParkingSpot(PropertyParkingSpotSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        PropertyParkingSpotDO parkingSpot = BeanUtils.toBean(reqVO, PropertyParkingSpotDO.class);
        parkingSpotMapper.insert(parkingSpot);
        return parkingSpot.getId();
    }

    @Override
    public void updateParkingSpot(PropertyParkingSpotSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateParkingSpotExists(reqVO.getId());
        PropertyParkingSpotDO parkingSpot = BeanUtils.toBean(reqVO, PropertyParkingSpotDO.class);
        parkingSpotMapper.updateById(parkingSpot);
    }

    @Override
    public void deleteParkingSpot(Long id) {
        validateParkingSpotExists(id);
        parkingSpotMapper.deleteById(id);
    }

    @Override
    public PropertyParkingSpotDO getParkingSpot(Long id) {
        return parkingSpotMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyParkingSpotDO> getParkingSpotPage(Long projectId, Long parkingLotId, Long communityId,
            Integer spotType, Integer spotStatus, Integer pageNo, Integer pageSize) {
        return parkingSpotMapper.selectPage(projectId, parkingLotId, communityId, spotType, spotStatus, pageNo, pageSize);
    }

    @Override
    public List<PropertyParkingSpotDO> getParkingSpotListByParkingLotId(Long parkingLotId) {
        return parkingSpotMapper.selectListByParkingLotId(parkingLotId);
    }

    @Override
    public List<PropertyParkingSpotDO> getAvailableSpots(Long communityId) {
        return parkingSpotMapper.selectAvailableSpots(communityId);
    }

    private void validateParkingSpotExists(Long id) {
        if (parkingSpotMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.PARKING_SPOT_NOT_EXISTS);
        }
    }
}
