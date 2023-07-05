package com.nisum.integration.bci.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.nisum.integration.bci.api.mapper.UserMapper;
import com.nisum.integration.bci.api.model.request.CreateUserRequest;
import com.nisum.integration.bci.api.model.response.CreateUserResponse;
import com.nisum.integration.bci.domain.model.user.CreateUser;
import com.nisum.integration.bci.domain.model.user.User;
import com.nisum.integration.bci.domain.usecase.UserRegisterUseCase;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.UUID;

@WebMvcTest(UserRegisterController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserMapper mapper;

    @MockBean
    private UserRegisterUseCase userRegisterUseCase;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Faker faker = new Faker();

    @Test
    void given_empty_contract_when_call_user_registration_then_return_bad_request() throws Exception {
        CreateUserRequest request = new CreateUserRequest();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();


    }

    @Test
    void given_email_is_empty_when_call_user_registration_then_return_bad_request() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setName(faker.harryPotter().character());
        request.setPassword(faker.internet().password());

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();


    }

    @Test
    void given_contract_information_when_call_user_registration_then_return_created_status() throws Exception {
        //give
        CreateUserRequest request = new CreateUserRequest();
        request.setName(faker.harryPotter().character());
        request.setEmail(faker.bothify("????##@gmail.com"));
        request.setPassword(faker.internet().password());

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setToken("token");
        user.setActive(true);
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());

        CreateUserResponse response = new CreateUserResponse();
        response.setId(user.getId());
        response.setToken(user.getToken());
        response.setCreated(user.getCreated());
        response.setLastLogin(user.getLastLogin());
        response.setActive(user.isActive());

        Mockito.when(mapper.toCreateUser(request)).thenReturn(new CreateUser());
        Mockito.when(userRegisterUseCase.register(Mockito.any())).thenReturn(user);
        Mockito.when(mapper.toCreateUserResponse(user)).thenReturn(response);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.jsonPath("$.id").value(response.getId().toString()),
                        MockMvcResultMatchers.jsonPath("$.token").value(response.getToken()),
                        MockMvcResultMatchers.jsonPath("$.created").value(Is.is(response.getCreated().toString())),
                        MockMvcResultMatchers.jsonPath("$.lastLogin").value(Is.is(response.getLastLogin().toString())),
                        MockMvcResultMatchers.jsonPath("$.active").value(response.isActive()));

        Mockito.verify(mapper, Mockito.times(1)).toCreateUser(request);
        Mockito.verify(userRegisterUseCase, Mockito.times(1)).register(Mockito.any());
        Mockito.verify(mapper, Mockito.times(1)).toCreateUserResponse(user);
    }
}
