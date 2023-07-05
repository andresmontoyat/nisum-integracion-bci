package com.nisum.integration.bci.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PhoneEntity {
    private String number;
    private String cityCode;
    private String countryCode;
}