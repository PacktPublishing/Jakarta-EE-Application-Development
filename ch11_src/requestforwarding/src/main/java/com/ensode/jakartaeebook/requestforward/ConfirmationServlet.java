package com.ensode.jakartaeebook.requestforward;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/confirmationservlet"})
public class ConfirmationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter printWriter;
            List<String> checkedLabels = (List<String>) request
                    .getAttribute("checkedLabels");

            response.setContentType("text/html");
            printWriter = response.getWriter();
            printWriter.println("<p>");
            printWriter.print("The following options were selected:");
            printWriter.println("<br/>");

            if (checkedLabels != null) {
                for (String optionLabel : checkedLabels) {
                    printWriter.print(optionLabel);
                    printWriter.println("<br/>");
                }
            } else {
                printWriter.println("None");
            }
            printWriter.println("</p>");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
