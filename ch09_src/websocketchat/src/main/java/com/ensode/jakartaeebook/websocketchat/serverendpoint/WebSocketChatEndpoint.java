package com.ensode.jakartaeebook.websocketchat.serverendpoint;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint("/websocketchat")
public class WebSocketChatEndpoint {

  private static final Logger LOG = Logger.getLogger(WebSocketChatEndpoint.class.getName());

  @OnOpen
  public void connectionOpened() {
    LOG.log(Level.INFO, "connection opened");
  }

  @OnMessage
  public synchronized void processMessage(Session session, String message) {
    LOG.log(Level.INFO, "received message: {0}", message);

    session.getOpenSessions().forEach(sess -> {
      if (sess.isOpen()) {
        try {
          sess.getBasicRemote().sendText(message);
        } catch (IOException ex) {
          LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
      }
    });

  }

  @OnClose
  public void connectionClosed() {
    LOG.log(Level.INFO, "connection closed");
  }

}
