package com.ensode.jakartaeebook.xmlws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public class Calculator {

    @WebMethod
    public int add(int first, int second) {
        return first + second;
    }

    @WebMethod
    public int subtract(int first, int second) {
        return first - second;
    }
}
