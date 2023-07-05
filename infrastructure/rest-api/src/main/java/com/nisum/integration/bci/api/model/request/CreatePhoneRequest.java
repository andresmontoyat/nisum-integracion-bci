package com.nisum.integration.bci.api.model.request;

import lombok.Data;

@Data
public class CreatePhoneRequest {
    private String number;
    private String cityCode;
    private String countryCode;
}
