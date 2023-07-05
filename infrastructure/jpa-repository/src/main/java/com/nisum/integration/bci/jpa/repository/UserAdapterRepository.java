package com.nisum.integration.bci.jpa.repository;

import com.nisum.integration.bci.domain.model.user.CreateUser;
import com.nisum.integration.bci.domain.model.user.User;
import com.nisum.integration.bci.domain.port.repository.UserRepository;
import com.nisum.integration.bci.jpa.entity.UserEntity;
import com.nisum.integration.bci.jpa.mapper.UserAdapterMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserAdapterRepository implements UserRepository {
    private final UserJpaRepository repository;

    private final UserAdapterMapper mapper;

    @Override
    public Optional<User> findUserByEmail(String email) {
        return repository.findByEmail(email).flatMap(entity -> Optional.ofNullable(mapper.toUser(entity)));
    }

    @Override
    public User save(CreateUser createUser, String token) {
        UserEntity entity = mapper.toUserEntity(createUser, token);
        entity = repository.save(entity);
        return mapper.toUser(entity);
    }
}
