package com.ensode.jakartaeebook.security.basicauthexample;

import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@BasicAuthenticationMechanismDefinition
@WebServlet(name = "SecuredServlet", urlPatterns = {"/securedServlet"})
@ServletSecurity(
        @HttpConstraint(rolesAllowed = "admin"))
public class SecuredServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.getOutputStream().print("Congratulations, login successful.");
  }
}
