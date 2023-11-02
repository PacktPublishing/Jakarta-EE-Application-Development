package com.ensode.jakartaeebook;

import jakarta.ejb.Remote;

@Remote
public interface SimpleSession {
    public String getMessage();
}
