package com.vnpt.vn.vsr.mongo.model.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@NoArgsConstructor
public class Response implements Serializable {

    private int err_code;
    private String err_msg;
    private Object data;
}
