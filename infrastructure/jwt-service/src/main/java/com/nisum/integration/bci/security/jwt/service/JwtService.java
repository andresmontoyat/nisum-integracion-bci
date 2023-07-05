package com.nisum.integration.bci.security.jwt.service;

import com.nisum.integration.bci.domain.exception.TokenException;
import com.nisum.integration.bci.domain.port.security.TokenService;
import com.nisum.integration.bci.security.jwt.context.JwtContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtService implements TokenService {
    private final JwtContext jwtContext;

    @Override
    public String generate(String subject) {
        try {
            return jwtContext.create(subject);
        } catch (Exception e) {
            throw new TokenException("An error has occurred trying to generate a JWT", e);
        }
    }
}
