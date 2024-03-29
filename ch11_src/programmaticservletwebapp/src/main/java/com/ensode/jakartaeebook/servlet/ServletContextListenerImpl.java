package com.ensode.jakartaeebook.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.annotation.WebListener;

@WebListener()
public class ServletContextListenerImpl implements
        ServletContextListener {

  @Override
  public void contextInitialized(
          ServletContextEvent servletContextEvent) {
    ServletContext servletContext = servletContextEvent.
            getServletContext();
    try {
      ProgrammaticallyConfiguredServlet servlet = servletContext.
              createServlet(ProgrammaticallyConfiguredServlet.class);
      servletContext.addServlet("ProgrammaticallyConfiguredServlet",
              servlet);
      ServletRegistration servletRegistration = servletContext.
              getServletRegistration(
                      "ProgrammaticallyConfiguredServlet");
      servletRegistration.addMapping(
              "/ProgrammaticallyConfiguredServlet");
    } catch (ServletException servletException) {
      servletContext.log(servletException.getMessage());
    }
  }

  @Override
  public void contextDestroyed(
          ServletContextEvent servletContextEvent) {
  }
}
