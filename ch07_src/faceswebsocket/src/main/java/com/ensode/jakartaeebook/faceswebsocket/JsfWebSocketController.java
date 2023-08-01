package com.ensode.jakartaeebook.faceswebsocket;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class JsfWebSocketController implements Serializable {

  @Inject
  private JsfWebSocketMessageSender jsfWebSocketMessageSender;

  private String userName;
  private String message;

  public void sendMessage() {
    jsfWebSocketMessageSender.send(String.format("%s: %s", userName, message));
  }

  public String navigateToChatPage() {
    return "chat";
  }

  public JsfWebSocketMessageSender getJsfWebSocketMessageSender() {
    return jsfWebSocketMessageSender;
  }

  public void setJsfWebSocketMessageSender(JsfWebSocketMessageSender jsfWebSocketMessageSender) {
    this.jsfWebSocketMessageSender = jsfWebSocketMessageSender;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
