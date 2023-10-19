package com.ensode.jakartaeebook.webfragment;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebFragmentServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.getOutputStream().print("<p>\n");
    response.getOutputStream().print("Using a web framework has never being this easy!");
    response.getOutputStream().print("</p>\n");
  }
}
