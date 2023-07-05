package com.nisum.integration.bci.api.controller;

import com.nisum.integration.bci.api.mapper.UserMapper;
import com.nisum.integration.bci.api.model.request.CreateUserRequest;
import com.nisum.integration.bci.api.model.response.CreateUserResponse;
import com.nisum.integration.bci.domain.model.user.User;
import com.nisum.integration.bci.domain.usecase.UserRegisterUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User registration API")
@RestController
@RequestMapping("/v1/users/register")
@AllArgsConstructor
public class UserRegisterController {

    private final UserRegisterUseCase useCase;

    private final UserMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse register(@Validated @RequestBody CreateUserRequest createUserRequest) {
        User user = useCase.register(mapper.toCreateUser(createUserRequest));
        return mapper.toCreateUserResponse(user);
    }
}
