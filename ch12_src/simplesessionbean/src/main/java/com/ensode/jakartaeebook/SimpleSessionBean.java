package com.ensode.jakartaeebook;

import jakarta.ejb.Stateless;

@Stateless
public class SimpleSessionBean implements SimpleSession{

    private final String message = "If you don't see this, it didn't work!";

    @Override
    public String getMessage() {
        return message;
    }
}
