package com.nisum.integration.bci.security.jwt.context;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nisum.integration.bci.security.jwt.config.properties.JwtProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Component
@AllArgsConstructor
public class JwtContext {
    private final JwtProperties jwtProperties;


    public String create(String subject) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Algorithm algorithm = Algorithm.RSA256(publicKey(), privateKey());

        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expirationAt = issuedAt.plus(jwtProperties.getExpiration(), ChronoUnit.MINUTES);

        return JWT.create()
                .withIssuer("nisum")
                .withSubject(subject)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expirationAt)
                .sign(algorithm);
    }

    private RSAPublicKey publicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(jwtProperties.getPublicKey()));
        return (RSAPublicKey) kf.generatePublic(keySpecX509);
    }

    private RSAPrivateKey privateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(jwtProperties.getPrivateKey()));
        return (RSAPrivateKey) kf.generatePrivate(keySpecPKCS8);
    }
}
