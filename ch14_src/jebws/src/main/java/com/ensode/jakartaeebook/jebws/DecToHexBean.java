package com.ensode.jakartaeebook.jebws;

import jakarta.ejb.Stateless;
import jakarta.jws.WebService;

@Stateless
@WebService
public class DecToHexBean {

  public String convertDecToHex(Integer i) {
    return Integer.toHexString(i);
  }
}
