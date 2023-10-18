package com.ensode.jakartaeebook.formhandling;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/formhandlerservlet"})
public class FormHandlerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String enteredValue;

        enteredValue = request.getParameter("enteredValue");

        response.setContentType("text/html");

        PrintWriter printWriter;
        try {
            printWriter = response.getWriter();

            printWriter.println("<p>");
            printWriter.print("You entered: ");
            printWriter.print(enteredValue);
            printWriter.print("</p>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
