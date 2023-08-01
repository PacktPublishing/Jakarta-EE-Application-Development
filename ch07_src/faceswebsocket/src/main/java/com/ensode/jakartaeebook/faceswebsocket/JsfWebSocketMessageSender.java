package com.ensode.jakartaeebook.faceswebsocket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@ApplicationScoped
public class JsfWebSocketMessageSender implements Serializable {

  @Inject
  @Push
  private PushContext pushContext;

  public void send(String message) {
    System.out.println("Sending message: " + message);
    pushContext.send(message);
  }
}
