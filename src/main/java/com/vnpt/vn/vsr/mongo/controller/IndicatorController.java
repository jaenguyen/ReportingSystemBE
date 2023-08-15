package com.vnpt.vn.vsr.mongo.controller;

import com.vnpt.vn.vsr.mongo.model.impl.Indicator;
import com.vnpt.vn.vsr.mongo.model.payload.response.Response;
import com.vnpt.vn.vsr.mongo.repository.IndicatorRepository;
import com.vnpt.vn.vsr.mongo.service.impl.IndicatorServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ind/v1")
@AllArgsConstructor
public class IndicatorController {

    private IndicatorServiceImpl indicatorService;

    @RequestMapping(value = "/createIndicator", method = RequestMethod.POST)
    public Response createInpData(@RequestBody List<Indicator> indicator) {
        return indicatorService.createIndicator(indicator);
    }

    @RequestMapping(value = "/getIndicator/{objId}/{tenantID}", method = RequestMethod.GET)
    public Response listFile(@PathVariable String objId, @PathVariable String tenantID) {
        return indicatorService.findIndicator(objId,tenantID);
    }
}
