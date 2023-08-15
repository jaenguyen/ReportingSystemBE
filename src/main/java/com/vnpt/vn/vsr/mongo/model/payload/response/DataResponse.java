package com.vnpt.vn.vsr.mongo.model.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataResponse {
    @JsonProperty(value = "err_code")
    private int errCode;

    @JsonProperty(value = "err_msg")
    private String errMsg;

    private Object data;

    public DataResponse(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
