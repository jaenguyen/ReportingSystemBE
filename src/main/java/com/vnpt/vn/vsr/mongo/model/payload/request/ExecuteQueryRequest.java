package com.vnpt.vn.vsr.mongo.model.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteQueryRequest {
    @JsonProperty(value = "fcode")
    private String fCode;
    private String func;
    private List<Option> options;
    private List<String> params;
    private String uuid;
}
