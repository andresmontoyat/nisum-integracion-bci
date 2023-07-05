package com.nisum.integration.bci.domain.model.user;

import lombok.Data;

import java.util.List;

@Data
public class CreateUser {
    private String name;

    private String email;

    private String password;

    private List<CreatePhone> phones;
}
