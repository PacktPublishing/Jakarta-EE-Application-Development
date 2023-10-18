package com.ensode.jakartaeebook.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProgrammaticallyConfiguredServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request,
          HttpServletResponse response)
          throws ServletException, IOException {
    ServletOutputStream outputStream = response.getOutputStream();

    outputStream.println(
            "This message was generated from a servlet that was "
            + "configured programmatically.");
  }
}
