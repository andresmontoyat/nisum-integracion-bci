package com.nisum.integration.bci.domain.model.user;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class User {
    private UUID id;

    private String name;

    private String email;

    private String password;

    private String token;

    private boolean isActive;

    private LocalDateTime created;

    private LocalDateTime modified;

    private LocalDateTime lastLogin;

    private List<Phone> phones;
}
