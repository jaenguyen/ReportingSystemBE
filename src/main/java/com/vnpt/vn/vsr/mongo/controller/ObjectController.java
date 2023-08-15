package com.vnpt.vn.vsr.mongo.controller;

import com.vnpt.vn.vsr.mongo.model.impl.OrAttribute;
import com.vnpt.vn.vsr.mongo.model.impl.Indicator;
import com.vnpt.vn.vsr.mongo.model.impl.Object;
import com.vnpt.vn.vsr.mongo.model.payload.response.DataResponse;
import com.vnpt.vn.vsr.mongo.service.impl.AttributeServiceImpl;
import com.vnpt.vn.vsr.mongo.service.impl.IndicatorServiceImpl;
import com.vnpt.vn.vsr.mongo.service.impl.ObjectServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mongo-service")
@AllArgsConstructor
public class ObjectController {

    private final ObjectServiceImpl objectService;
    private final AttributeServiceImpl attributeService;

    private final IndicatorServiceImpl indicatorService;

    @PostMapping("/saveObject")
    public ResponseEntity<?> saveObject() {
        Object object = new Object();
        object.setObjCode("1702.SL001");
        object.setFormCode("1702.SL001");
        object.setObjName("Báo cáo test");
        object.setObjTypeName("Báo cáo số liệu");
        object.setObjType("1");
        object.setIsGroup(0);
        object.setOrgId("734808");
        object.setOrgName("Tỉnh Lai Châu");
        object.setOrgType("2");
        object.setOrgTypeName("Tỉnh/Thành phố");
        object.setStatus(1);
        object.setSubmitType("5");
        object.setSubmitTypeName("Báo cáo ngày");
        object.setProgramId("1593");
        object.setProgramCode("06");
        object.setProgramName("Báo cáo Đề án 06");
        object.setTenantId("56");
        object.setTenantName("HỆ THỐNG BÁO CÁO TỈNH LAI CHÂU");
        object.setShowInAssignRpt(1);
        object.setNullTo0(0);

        objectService.save(object);
        return new ResponseEntity<>(new DataResponse(0, "Thành công"), HttpStatus.OK);
    }

    @GetMapping("/getObjectByProgram")
    public ResponseEntity<?> getObjectByProgram(@RequestParam("programId") String programId, @RequestParam("key") String key, @RequestParam("orgId") String orgId, @RequestParam("tenantId") String tenantId, @RequestParam("objType") String objType) {
        return objectService.getObjectByProgram(programId, key, orgId, tenantId, objType);
    }

    @PostMapping("/object/save")
    public int saveObject2(@RequestBody Object object, @RequestParam String i_uid, @RequestParam String i_hid, @RequestParam String i_sch, @RequestParam String i_ip) {
        if (object.getStatus() != 1) {
            return -2;
        }
        List<Object> objectListInDB = objectService.findByTenantIdAndObjCodeIgnoreCaseAndObjType(object.getTenantId(), object.getObjCode(), object.getObjType());
        if (objectListInDB.size() > 0) {
            return -1;
        }
        if (object.getParentId().equals(-1) || object.getParentId().equals(0)) {
            object.setParentId(null);
        }
        if (i_hid.toLowerCase().equals("_la")) {

        }
        object = objectService.save(object);

        if (object.getObjType().equals(1)) {
            if (i_hid.equals("_la")) {
                OrAttribute attribute = new OrAttribute();
                attribute.setObjId(object.getId());
                attribute.setTenantId(object.getTenantId());
                attribute.setTenantName(object.getTenantName());
                attribute.setAttrCode("-");
                attribute.setAttrName("Report properties");
                attribute.setAttrType(0);
                attribute = attributeService.save(attribute);

                Indicator indicator = new Indicator();
                indicator.setIndCode("-");
                indicator.setIndName("Reporting targets");
                indicator.setIsSumary(1);
                indicator.setIndType(0);
                indicator.setObjId(object.getId());
                indicator.setTenantId(object.getTenantId());
                indicator.setTenantName(object.getTenantName());
                indicator = indicatorService.save(indicator);
            }
        } else {

        }
        return 1;
    }
}
