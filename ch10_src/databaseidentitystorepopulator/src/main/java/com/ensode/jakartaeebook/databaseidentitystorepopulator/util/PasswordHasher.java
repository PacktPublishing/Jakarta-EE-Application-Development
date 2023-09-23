package com.ensode.jakartaeebook.databaseidentitystorepopulator.util;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class PasswordHasher {

    @Inject
    private Pbkdf2PasswordHash pbkdf2PasswordHash;

    @PostConstruct
    public void init() {

        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameterMap.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameterMap.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        pbkdf2PasswordHash.initialize(parameterMap);
    }

    public String hashPassword(String clearTextPassword) {
        return pbkdf2PasswordHash.generate(clearTextPassword.toCharArray());
    }

}
