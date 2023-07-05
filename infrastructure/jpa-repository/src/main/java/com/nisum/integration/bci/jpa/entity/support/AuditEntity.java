package com.nisum.integration.bci.jpa.entity.support;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class AuditEntity {
    @CreatedDate
    private LocalDateTime created;

    @LastModifiedBy
    private LocalDateTime modified;
}
