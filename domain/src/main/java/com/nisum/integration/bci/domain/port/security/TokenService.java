package com.nisum.integration.bci.domain.port.security;

public interface TokenService {
    String generate(String subject);
}
