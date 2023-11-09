package com.ensode.jakartaeebook.basicauthdbidentitystore.init;

import jakarta.annotation.Resource;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

@DataSourceDefinition(name = "java:global/jdbc/jebSecurityDatasource",
        className = "org.h2.jdbcx.JdbcDataSource",
        url = "jdbc:h2:tcp://127.0.1.1:9093/mem:userauthdb",
        user = "sa",
        password = "")
@ApplicationScoped
public class SecurityConfig {

  @Resource(lookup = "java:global/jdbc/jebSecurityDatasource")
  private DataSource dataSource;

  @Inject
  private Pbkdf2PasswordHash passwordHash;

  public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
    try {
      Map<String, String> parameters = new HashMap<>();
      parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
      parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
      parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
      passwordHash.initialize(parameters);

      executeUpdate(dataSource, "INSERT INTO USERS (USER_ID,USERNAME,FIRST_NAME, LAST_NAME,PASSWORD) VALUES (1,'alice','Alice','Jones','"
              + passwordHash.generate("aaa".toCharArray()) + "')");
      executeUpdate(dataSource, "INSERT INTO USERS (USER_ID,USERNAME,FIRST_NAME, LAST_NAME,PASSWORD) VALUES (2,'bob','Robert','Smith','" +
              passwordHash.generate("bbb".toCharArray()) + "')");

      executeUpdate(dataSource, "INSERT INTO USER_GROUPS (USER_ID,GROUP_ID) VALUES (1,1)");
      executeUpdate(dataSource, "INSERT INTO USER_GROUPS (USER_ID,GROUP_ID) VALUES (2,2)");
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  private void executeUpdate(DataSource dataSource, String query) {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.executeUpdate();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
