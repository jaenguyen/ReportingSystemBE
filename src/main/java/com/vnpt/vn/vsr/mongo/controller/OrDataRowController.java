package com.vnpt.vn.vsr.mongo.controller;

import com.vnpt.vn.vsr.mongo.model.payload.response.Response;
import com.vnpt.vn.vsr.mongo.service.impl.OrDataRowServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/dataRow/v1")
public class OrDataRowController {

    private OrDataRowServiceImpl orDataRowService;

    @GetMapping(value = "/getDataAndPreTime/{objId}/{tenantID}/{orgId}/{timeId}/{submitType}/{timeIdPre}")
    public Response getDataAndPreTime(@PathVariable String objId,
                                      @PathVariable String tenantID,
                                      @PathVariable String orgId,
                                      @PathVariable String timeId,
                                      @PathVariable int submitType,
                                      @PathVariable String timeIdPre) throws Exception {
        return orDataRowService.getDataAndPreTime(objId, tenantID, orgId, timeId, submitType, timeIdPre);
    }
}
