package com.ensode.jakartaeebook.customerdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.h2.tools.Server;

public class H2DatabaseWrapper {

  private static Server server;
  Connection connection;

  private void startH2Database() {

    try {

      String inMemoryJdbcUrl = "jdbc:h2:mem:customerdb;DB_CLOSE_DELAY=-1";
      String tcpJdbcUrl;

      server = Server.createTcpServer("-tcpAllowOthers").start();

      tcpJdbcUrl = String.format("jdbc:h2:%s/mem:customerdb", server.getURL());
      Class.forName("org.h2.Driver");

      connection = DriverManager.getConnection(String.format(
              inMemoryJdbcUrl), "sa", "");
      System.out.println(String.format("Connection Established: %s/%s", connection.getMetaData().getDatabaseProductName(), connection.getCatalog()));

      System.out.println(String.format("JDBC URL: %s", tcpJdbcUrl));

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  private void populateDatabase() throws SQLException, IOException {
    System.out.println("Populating database");

    List<String> scriptLines = readScript();

    Statement statement = connection.createStatement();

    scriptLines.forEach(line -> {
      try {
        statement.executeUpdate(line);
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    });

    System.out.println("Database populated successfully");
  }

  private List<String> readScript() throws IOException {

    List<String> scriptLines = new ArrayList<>();

    try (InputStream inputStream = this.getClass().getClassLoader()
            .getResourceAsStream("create_populate_tables.sql"); InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8); BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

      String scriptLine;
      while ((scriptLine = bufferedReader.readLine()) != null) {
        scriptLines.add(scriptLine);
      }
    }

    //remove empty lines and comments
    scriptLines = scriptLines.stream().filter(line -> !line.isBlank()).filter(line -> !line.startsWith("--")).collect(Collectors.toList());

    return scriptLines;
  }

  public static void main(String[] args) throws IOException, URISyntaxException, SQLException {
    H2DatabaseWrapper h2DatabaseWrapper = new H2DatabaseWrapper();

    h2DatabaseWrapper.startH2Database();
    h2DatabaseWrapper.populateDatabase();

    System.out.println("Login as user \"sa\" with no password");

    System.out.println("Hit ctrl+C to shut down the database");

    //clean up when ctrl+c is hit
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("Shutting down the database");
      server.shutdown();
    }));

    try {
      //keep the program running until interrupted (ctrl+c)
      Thread.currentThread().join();
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }
}
