package com.vnpt.vn.vsr.mongo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AbstractBaseEntity implements Serializable {

    @Id
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AbstractBaseEntity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
