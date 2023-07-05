package com.nisum.integration.bci.domain.model.user;

import lombok.Data;

@Data
public class CreatePhone {
    private String number;
    private String cityCode;
    private String countryCode;
}
