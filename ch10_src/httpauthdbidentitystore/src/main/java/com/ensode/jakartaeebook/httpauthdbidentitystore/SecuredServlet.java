package com.ensode.jakartaeebook.httpauthdbidentitystore;

import jakarta.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@FormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login.html",
                errorPage = "/loginerror.html"
        )
)

@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:global/jdbc/userauthdbDatasource",
        callerQuery = "select password from users where USERNAME = ?",
        groupsQuery = "select g.GROUP_NAME from USER_GROUPS ug, users u, GROUPS g where ug.USER_ID = u.user_id and g.GROUP_ID= ug.GROUP_ID and u.USERNAME=?",
        hashAlgorithm = Pbkdf2PasswordHash.class,
        hashAlgorithmParameters = {
            "Pbkdf2PasswordHash.Iterations=3072",
            "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA512",
            "Pbkdf2PasswordHash.SaltSizeBytes=64"
        }
)
@WebServlet("/securedServlet")
@ServletSecurity(
        @HttpConstraint(rolesAllowed = {"admin"}))
public class SecuredServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().write("Congratulations, login successful.");
    }
}
