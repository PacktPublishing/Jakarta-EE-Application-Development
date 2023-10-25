package com.ensode.jakartaeebook.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener()
public class HttpRequestListener implements ServletRequestListener {

  private static final Logger LOG
          = Logger.getLogger(HttpRequestListener.class.getName());

  @Override
  public void requestInitialized(ServletRequestEvent servletRequestEvent) {
    LOG.log(Level.INFO, "New request initialized");
  }

  @Override
  public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
    LOG.log(Level.INFO, "Request destroyed");
  }
}
