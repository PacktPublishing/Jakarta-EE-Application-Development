package com.ensode.jakartaeebook.multiplevaluefields;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/multiplevaluefieldhandlerservlet"})
public class MultipleValueFieldHandlerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String[] selectedOptions = request.getParameterValues("options");

        response.setContentType("text/html");

        try {
            PrintWriter printWriter = response.getWriter();

            printWriter.println("<p>");
            printWriter.print("The following options were selected:");
            printWriter.println("<br/>");

            if (selectedOptions != null) {
                for (String option : selectedOptions) {
                    printWriter.print(option);
                    printWriter.println("<br/>");
                }
            } else {
                printWriter.println("None");
            }
            printWriter.println("</p>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
