package cn.iocoder.yudao.module.property.service.parking;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.parking.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.parking.PropertyParkingLotDO;
import cn.iocoder.yudao.module.property.dal.mysql.parking.PropertyParkingLotMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyParkingLotServiceImpl implements PropertyParkingLotService {

    @Resource private PropertyParkingLotMapper parkingLotMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override
    public Long createParkingLot(PropertyParkingLotSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        if (reqVO.getCode() != null && parkingLotMapper.selectByCode(reqVO.getCode()) != null) {
            throw exception(ErrorCodeConstants.PARKING_LOT_NOT_EXISTS);
        }
        PropertyParkingLotDO parkingLot = BeanUtils.toBean(reqVO, PropertyParkingLotDO.class);
        parkingLotMapper.insert(parkingLot);
        return parkingLot.getId();
    }

    @Override
    public void updateParkingLot(PropertyParkingLotSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateParkingLotExists(reqVO.getId());
        PropertyParkingLotDO parkingLot = BeanUtils.toBean(reqVO, PropertyParkingLotDO.class);
        parkingLotMapper.updateById(parkingLot);
    }

    @Override
    public void deleteParkingLot(Long id) {
        validateParkingLotExists(id);
        parkingLotMapper.deleteById(id);
    }

    @Override
    public PropertyParkingLotDO getParkingLot(Long id) {
        return parkingLotMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyParkingLotDO> getParkingLotPage(Long projectId, Long communityId, Integer parkingType,
            Integer status, Integer pageNo, Integer pageSize) {
        return parkingLotMapper.selectPage(projectId, communityId, parkingType, status, pageNo, pageSize);
    }

    @Override
    public List<PropertyParkingLotDO> getParkingLotListByCommunityId(Long communityId) {
        return parkingLotMapper.selectListByCommunityId(communityId);
    }

    private void validateParkingLotExists(Long id) {
        if (parkingLotMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.PARKING_LOT_NOT_EXISTS);
        }
    }
}
