package com.ensode.jakartaeebook.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;

@WebListener()
public class RequestAttributeListener implements ServletRequestListener {

  @Override
  public void requestInitialized(ServletRequestEvent servletRequestEvent) {
    ServletContext servletContext = servletRequestEvent.getServletContext();
    String clientIpAddress = servletRequestEvent.getServletRequest().
            getRemoteAddr();
    servletContext.log("New request initialized for client at IP address: "
            + clientIpAddress);
  }

  @Override
  public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
    ServletContext servletContext = servletRequestEvent.getServletContext();
    String clientIpAddress = servletRequestEvent.getServletRequest().
            getRemoteAddr();
    servletContext.log("Request destroyed for client at IP address: "
            + clientIpAddress);
  }
}
