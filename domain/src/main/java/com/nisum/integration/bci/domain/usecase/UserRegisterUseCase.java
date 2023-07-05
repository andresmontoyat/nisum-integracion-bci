package com.nisum.integration.bci.domain.usecase;

import com.nisum.integration.bci.domain.exception.DomainException;
import com.nisum.integration.bci.domain.model.user.CreateUser;
import com.nisum.integration.bci.domain.model.user.User;
import com.nisum.integration.bci.domain.port.environment.EnvironmentService;
import com.nisum.integration.bci.domain.port.repository.UserRepository;
import com.nisum.integration.bci.domain.port.security.TokenService;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class UserRegisterUseCase {
    private final UserRepository repository;
    private final TokenService tokenService;
    private final EnvironmentService environmentService;

    public User register(CreateUser createUser) {
        valid(createUser);

        Optional<User> result = repository.findUserByEmail(createUser.getEmail());
        if (result.isPresent()) {
            throw new DomainException("El correo ya registrado");
        }

        String token = tokenService.generate(createUser.getEmail());
        return repository.save(createUser, token);
    }

    private void valid(CreateUser createUser) {
        String emailRule = environmentService.getPropertyValue("application.contract.user.email");
        if (!match(emailRule, createUser.getEmail()))
            throw new DomainException("Email invalido");

        String passwordRule = environmentService.getPropertyValue("application.contract.user.password");
        if (!match(passwordRule, createUser.getPassword()))
            throw new DomainException("Password invalido");
    }

    private boolean match(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);

        return matcher.matches();
    }
}
