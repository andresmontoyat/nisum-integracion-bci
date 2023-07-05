package com.nisum.integration.bci.domain.port.repository;

import com.nisum.integration.bci.domain.model.user.CreateUser;
import com.nisum.integration.bci.domain.model.user.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserByEmail(String email);

    User save(CreateUser createUser, String token);
}
