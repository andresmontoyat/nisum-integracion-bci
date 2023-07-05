package com.nisum.integration.bci.security.jwt.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "application.jwt")
public class JwtProperties {

    private String publicKey;

    private String privateKey;

    private int expiration;
}
