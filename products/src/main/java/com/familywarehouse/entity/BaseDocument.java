package com.familywarehouse.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BaseDocument {

    @Id
    private String id;

    @CreatedDate
    @Field("created_date")
    private LocalDateTime createdDate;

    @CreatedBy
    @Field("created_by")
    private String createdBy;

    @LastModifiedDate
    @Field("updated_date")
    private LocalDateTime updatedDate;

    @LastModifiedBy
    @Field("updated_by")
    private String updatedBy;

}
