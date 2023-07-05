package com.nisum.integration.bci.domain.usecase;

import com.github.javafaker.Faker;
import com.nisum.integration.bci.domain.exception.DomainException;
import com.nisum.integration.bci.domain.model.user.CreateUser;
import com.nisum.integration.bci.domain.model.user.User;
import com.nisum.integration.bci.domain.port.environment.EnvironmentService;
import com.nisum.integration.bci.domain.port.repository.UserRepository;
import com.nisum.integration.bci.domain.port.security.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserRegisterUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private EnvironmentService environmentService;

    @InjectMocks
    private UserRegisterUseCase userRegisterUseCase;

    private Faker faker = new Faker();

    @Test
    void given_an_wrong_email_when_user_business_validation_then_throws_domain_exception() {
        CreateUser createUser = new CreateUser();
        createUser.setName(faker.harryPotter().character());
        createUser.setEmail(faker.bothify("????##@gmail.com"));
        createUser.setPassword(faker.internet().password());

        Mockito.when(environmentService.getPropertyValue("application.contract.user.email")).thenReturn("pattern");

        DomainException exception = assertThrows(DomainException.class,
                () -> userRegisterUseCase.register(createUser));

        assertEquals("Email invalido", exception.getMessage());

        Mockito.verify(environmentService, Mockito.times(1)).getPropertyValue("application.contract.user.email");
    }

    @Test
    void given_a_wrong_password_when_user_business_validation_then_throws_domain_exception() {
        CreateUser createUser = new CreateUser();
        createUser.setName(faker.harryPotter().character());
        createUser.setEmail(faker.bothify("????##@gmail.com"));
        createUser.setPassword(faker.internet().password());

        Mockito.when(environmentService.getPropertyValue("application.contract.user.email")).thenReturn("^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$");
        Mockito.when(environmentService.getPropertyValue("application.contract.user.password")).thenReturn("pattern");

        DomainException exception = assertThrows(DomainException.class,
                () -> userRegisterUseCase.register(createUser));

        assertEquals("Password invalido", exception.getMessage());

        Mockito.verify(environmentService, Mockito.times(1)).getPropertyValue("application.contract.user.email");
        Mockito.verify(environmentService, Mockito.times(1)).getPropertyValue("application.contract.user.password");
    }

    @Test
    void given_a_existent_email_when_user_business_validation_then_throws_domain_exception() {
        CreateUser createUser = new CreateUser();
        createUser.setName(faker.harryPotter().character());
        createUser.setEmail(faker.bothify("????##@gmail.com"));
        createUser.setPassword("a1234569");

        Mockito.when(environmentService.getPropertyValue("application.contract.user.email")).thenReturn("^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$");
        Mockito.when(environmentService.getPropertyValue("application.contract.user.password")).thenReturn("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        Mockito.when(userRepository.findUserByEmail(createUser.getEmail())).thenReturn(Optional.of(new User()));

        DomainException exception = assertThrows(DomainException.class,
                () -> userRegisterUseCase.register(createUser));

        assertEquals("El correo ya registrado", exception.getMessage());

        Mockito.verify(environmentService, Mockito.times(1)).getPropertyValue("application.contract.user.email");
        Mockito.verify(environmentService, Mockito.times(1)).getPropertyValue("application.contract.user.password");
        Mockito.verify(userRepository, Mockito.times(1)).findUserByEmail(createUser.getEmail());
    }

    @Test
    void given_a_non_existent_email_when_user_business_validation_then_insert_new_user() {
        CreateUser createUser = new CreateUser();
        createUser.setName(faker.harryPotter().character());
        createUser.setEmail(faker.bothify("????##@gmail.com"));
        createUser.setPassword("a1234569");

        String token = "token";

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(createUser.getName());
        user.setEmail(createUser.getEmail());
        user.setPassword(createUser.getPassword());
        user.setToken(token);
        user.setActive(true);
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());

        Mockito.when(environmentService.getPropertyValue("application.contract.user.email")).thenReturn("^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$");
        Mockito.when(environmentService.getPropertyValue("application.contract.user.password")).thenReturn("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        Mockito.when(userRepository.findUserByEmail(createUser.getEmail())).thenReturn(Optional.empty());
        Mockito.when(tokenService.generate(createUser.getEmail())).thenReturn("token");
        Mockito.when(userRepository.save(createUser, token)).thenReturn(user);

        User result = userRegisterUseCase.register(createUser);
        assertEquals(user.getId(), result.getId());
        assertEquals(token, result.getToken());

        Mockito.verify(environmentService, Mockito.times(1)).getPropertyValue("application.contract.user.email");
        Mockito.verify(environmentService, Mockito.times(1)).getPropertyValue("application.contract.user.password");
        Mockito.verify(userRepository, Mockito.times(1)).findUserByEmail(createUser.getEmail());
        Mockito.verify(tokenService, Mockito.times(1)).generate(createUser.getEmail());
        Mockito.verify(userRepository, Mockito.times(1)).save(createUser, token);
    }
}
