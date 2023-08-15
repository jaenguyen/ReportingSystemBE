package com.vnpt.vn.vsr.mongo.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    private String name;
    private String value;
}
