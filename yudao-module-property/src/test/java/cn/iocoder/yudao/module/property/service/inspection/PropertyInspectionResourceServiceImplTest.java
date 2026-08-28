package cn.iocoder.yudao.module.property.service.inspection;

import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionPointSaveReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionStandardSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionPointDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionStandardDO;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionPointMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionStandardMapper;
import cn.iocoder.yudao.module.property.service.org.PropertyProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyInspectionResourceServiceImplTest {
    @InjectMocks private PropertyInspectionResourceServiceImpl service;
    @Mock private PropertyInspectionStandardMapper standardMapper;
    @Mock private PropertyInspectionPointMapper pointMapper;
    @Mock private PropertyProjectService projectService;

    @Test
    void shouldCreateNextImmutableStandardVersion() {
        when(standardMapper.selectLatestByCode(10L, "FIRE-EXIT"))
                .thenReturn(PropertyInspectionStandardDO.builder().standardVersion(2).build());
        doAnswer(invocation -> {
            PropertyInspectionStandardDO standard = invocation.getArgument(0);
            standard.setId(8L);
            return 1;
        }).when(standardMapper).insert(any(PropertyInspectionStandardDO.class));

        PropertyInspectionStandardSaveReqVO reqVO = standardReq();
        assertEquals(8L, service.createStandard(reqVO));

        var captor = org.mockito.ArgumentCaptor.forClass(PropertyInspectionStandardDO.class);
        verify(standardMapper).insert(captor.capture());
        assertEquals(3, captor.getValue().getStandardVersion());
        assertFalse(captor.getValue().getPublished());
    }

    @Test
    void shouldRejectEditingPublishedVersion() {
        when(standardMapper.selectById(8L)).thenReturn(PropertyInspectionStandardDO.builder()
                .id(8L).projectId(10L).published(true).build());
        PropertyInspectionStandardSaveReqVO reqVO = standardReq();
        reqVO.setId(8L);

        assertThrows(RuntimeException.class, () -> service.updateStandard(reqVO));
        verify(standardMapper, never()).updateById(any(PropertyInspectionStandardDO.class));
    }

    @Test
    void shouldRejectDuplicatePointCodeInsideProject() {
        when(pointMapper.selectByProjectAndCode(10L, "P-1"))
                .thenReturn(PropertyInspectionPointDO.builder().id(1L).build());
        PropertyInspectionPointSaveReqVO reqVO = new PropertyInspectionPointSaveReqVO();
        reqVO.setProjectId(10L);
        reqVO.setPointCode("P-1");

        assertThrows(RuntimeException.class, () -> service.createPoint(reqVO));
        verify(pointMapper, never()).insert(any(PropertyInspectionPointDO.class));
    }

    private PropertyInspectionStandardSaveReqVO standardReq() {
        PropertyInspectionStandardSaveReqVO reqVO = new PropertyInspectionStandardSaveReqVO();
        reqVO.setProjectId(10L);
        reqVO.setStandardCode("FIRE-EXIT");
        reqVO.setName("安全出口畅通");
        reqVO.setSpecialty("安消");
        reqVO.setCheckMethod("现场检查");
        reqVO.setPassCriteria("无占用");
        reqVO.setRiskLevel(3);
        reqVO.setEvidenceTypes("PHOTO");
        reqVO.setRectificationHours(2);
        reqVO.setStatus(0);
        return reqVO;
    }
}
