package com.ensode.jakartaeebook.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = {"/SimpleServlet"})
public class SimpleServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request,
          HttpServletResponse response) {
    try {
      request.setAttribute("attribute1", "value1");
      response.setContentType("text/html");
      PrintWriter printWriter = response.getWriter();
      printWriter.println("<p>");
      printWriter.println(
              "Check the logs to see the listener's output");
      printWriter.println("</p>");
      request.setAttribute("attribute1", "value2");
      request.removeAttribute("attribute1");
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }
}
