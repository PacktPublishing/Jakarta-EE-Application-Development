package com.ensode.jakartaeebook.faceswebsocket;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class FacesWebSocketController implements Serializable {

  @Inject
  private FacesWebSocketMessageSender facesWebSocketMessageSender;

  private String userName;
  private String message;

  public void sendMessage() {
    facesWebSocketMessageSender.send(
            String.format("%s: %s", userName, message));
  }

  public String navigateToChatPage() {
    return "chat";
  }

  public FacesWebSocketMessageSender getJsfWebSocketMessageSender() {
    return facesWebSocketMessageSender;
  }

  public void setJsfWebSocketMessageSender(FacesWebSocketMessageSender jsfWebSocketMessageSender) {
    this.facesWebSocketMessageSender = jsfWebSocketMessageSender;
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
