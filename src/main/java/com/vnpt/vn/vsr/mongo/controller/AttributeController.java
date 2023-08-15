package com.vnpt.vn.vsr.mongo.controller;


import com.vnpt.vn.vsr.mongo.model.impl.Indicator;
import com.vnpt.vn.vsr.mongo.model.impl.OrAttribute;
import com.vnpt.vn.vsr.mongo.model.payload.response.Response;
import com.vnpt.vn.vsr.mongo.service.impl.AttributeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/attr/v1")
public class AttributeController {

    private AttributeServiceImpl attributeServicelmpl;

    @RequestMapping(value = "/createAttribute", method = RequestMethod.POST)
    public Response createAttribute(@RequestBody List<OrAttribute>orAttributes) {
        return attributeServicelmpl.createAttribute(orAttributes);
    }
    @RequestMapping(value = "/getAttribute/{objId}/{tenantID}", method = RequestMethod.GET)
    public Response listFile(@PathVariable String objId, @PathVariable String tenantID) {
        return attributeServicelmpl.findAttribute(objId,tenantID);
    }
}
