package com.vnpt.vn.vsr.mongo.controller;


import com.vnpt.vn.vsr.mongo.model.impl.OrAttribute;
import com.vnpt.vn.vsr.mongo.model.impl.OrDataRow;
import com.vnpt.vn.vsr.mongo.model.payload.response.Response;
import com.vnpt.vn.vsr.mongo.service.impl.AttributeServiceImpl;
import com.vnpt.vn.vsr.mongo.service.impl.OrDataRowServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/dataRow/v1")
public class OrDataRowController {

    private OrDataRowServiceImpl orDataRowService;

    @RequestMapping(value = "/createOrDataRow", method = RequestMethod.POST)
    public Response createAttribute(@RequestBody List<OrDataRow>orDataRows) {
        return orDataRowService.createDataRow(orDataRows);
    }
    @RequestMapping(value = "/getOrDataRow/{objId}/{tenantID}/{orgId}", method = RequestMethod.GET)
    public Response listFile(@PathVariable String objId, @PathVariable String tenantID, @PathVariable String orgId) {
        return orDataRowService.findDataRow(objId,tenantID,orgId);
    }

    @RequestMapping(value = "/insertDataRowInput/{objId}/{tenantID}/{orgId}/{timeId}", method = RequestMethod.GET)
    public Response insertDataRowInput(@PathVariable String objId, @PathVariable String tenantID, @PathVariable String orgId, @PathVariable String timeId)  {
        return orDataRowService.insertDataRowInput(objId,tenantID,orgId,timeId);
    }

    @RequestMapping(value = "/getDataAndPreTime/{tenantId}/{objId}/{i_attrId}/{i_indId}/{orgId}/{timeId}/{submitType}/{timeIdPre}", method = RequestMethod.GET)
    public Response getDataAndPreTime(@PathVariable String tenantId,
                                      @PathVariable String objId,
                                      @PathVariable String i_attrId,
                                      @PathVariable String i_indId,
                                      @PathVariable String orgId,
                                      @PathVariable String timeId,
                                      @PathVariable int submitType,
                                      @PathVariable String timeIdPre) throws Exception {
        return orDataRowService.getDataAndPreTime(tenantId, objId, i_attrId, i_indId, orgId, timeId, submitType, timeIdPre);
    }

    @RequestMapping(value = "/getData/{tenantId}/{objId}/{i_attrId}/{i_indId}/{orgId}/{timeId}/{submitType}/{timeIdPre}", method = RequestMethod.GET)
    public Response getData(@PathVariable String tenantId,
                            @PathVariable String objId,
                            @PathVariable String i_attrId,
                            @PathVariable String i_indId,
                            @PathVariable String orgId,
                            @PathVariable String timeId,
                            @PathVariable int submitType) throws Exception {
        return orDataRowService.getData(tenantId, objId, i_attrId, i_indId, orgId, timeId, submitType);
    }
}
