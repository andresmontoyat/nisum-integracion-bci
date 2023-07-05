package com.nisum.integration.bci.api.mapper;

import com.nisum.integration.bci.api.model.request.CreateUserRequest;
import com.nisum.integration.bci.api.model.response.CreateUserResponse;
import com.nisum.integration.bci.domain.model.user.CreateUser;
import com.nisum.integration.bci.domain.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    CreateUser toCreateUser(CreateUserRequest createUserRequest);

    CreateUserResponse toCreateUserResponse(User user);

}
