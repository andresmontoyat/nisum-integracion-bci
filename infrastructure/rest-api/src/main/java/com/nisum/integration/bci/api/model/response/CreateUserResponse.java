package com.nisum.integration.bci.api.model.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class CreateUserResponse {
    private UUID id;

    private String token;

    private boolean isActive;

    private LocalDateTime created;

    private LocalDateTime modified;

    private LocalDateTime lastLogin;
}
