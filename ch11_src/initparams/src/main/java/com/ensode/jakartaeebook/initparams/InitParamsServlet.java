package com.ensode.jakartaeebook.initparams;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "InitParamsServlet", urlPatterns = {
  "/InitParamsServlet"}, initParams = {
  @WebInitParam(name = "param1", value = "value1"),
  @WebInitParam(name = "param2", value = "value2")})
public class InitParamsServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request,
          HttpServletResponse response)
          throws ServletException, IOException {
    ServletConfig servletConfig = getServletConfig();
    String param1Val = servletConfig.getInitParameter("param1");
    String param2Val = servletConfig.getInitParameter("param2");
    response.setContentType("text/html");
    PrintWriter printWriter = response.getWriter();

    printWriter.println("<p>");
    printWriter.println("Value of param1 is " + param1Val);
    printWriter.println("</p>");

    printWriter.println("<p>");
    printWriter.println("Value of param2 is " + param2Val);
    printWriter.println("</p>");
  }
}
