package com.ensode.jakartaeebook.basicauthdbidentitystore;

import com.ensode.jakartaeebook.Customer;
import com.ensode.jakartaeebook.SecureCustomerDaoBean;
import jakarta.annotation.security.DeclareRoles;
import jakarta.ejb.EJB;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
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

@BasicAuthenticationMechanismDefinition(
    realmName = "Book Realm"
)

@DatabaseIdentityStoreDefinition(
    dataSourceLookup = "java:global/jdbc/jebSecurityDatasource",
    callerQuery = "select password from users where USERNAME = ?",
    groupsQuery = "select g.GROUP_NAME from USER_GROUPS ug, users u, "
    + "GROUPS g where ug.USER_ID = u.user_id and "
    + "g.GROUP_ID= ug.GROUP_ID and u.USERNAME=?",
    hashAlgorithm = Pbkdf2PasswordHash.class,
    hashAlgorithmParameters = {
      "Pbkdf2PasswordHash.Iterations=3072",
      "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA512",
      "Pbkdf2PasswordHash.SaltSizeBytes=64"
    }
)
@DeclareRoles({"user", "admin"})
@WebServlet("/securedServlet")
@ServletSecurity(
    @HttpConstraint(rolesAllowed = {"user", "admin"}))
public class SecuredServlet extends HttpServlet {

  @EJB
  private SecureCustomerDaoBean secureCustomerDao;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.getWriter().write("Congratulations, login successful.\n");

    Long newCustomerId;

    Customer customer = new Customer();
    customer.setFirstName("Mark");
    customer.setLastName("Butcher");
    customer.setEmail("butcher@phony.org");

    response.getWriter().write("Saving New Customer...\n");
    newCustomerId = secureCustomerDao.saveNewCustomer(customer);

    response.getWriter().write("Retrieving customer...\n");
    customer = secureCustomerDao.getCustomer(newCustomerId);
    response.getWriter().write(customer.toString());
  }
}
