package com.ensode.jakartaeebook.microservices.cruddatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.h2.tools.Server;

public class H2DatabaseWrapper {

  private static Server server;

  private void startH2Database() {

    try {

      String inMemoryJdbcUrl = "jdbc:h2:mem:microservicescrud;DB_CLOSE_DELAY=-1";
      String tcpJdbcUrl;

      server = Server.createTcpServer("-tcpAllowOthers").start();

      tcpJdbcUrl = String.format("jdbc:h2:%s/mem:microservicescrud", server.getURL());
      Class.forName("org.h2.Driver");

      Connection conn = DriverManager.getConnection(String.format(
              inMemoryJdbcUrl), "sa", "");
      System.out.println(String.format("Connection Established: %s/%s", conn.getMetaData().getDatabaseProductName(), conn.getCatalog()));

      System.out.println(String.format("JDBC URL: %s", tcpJdbcUrl));

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new H2DatabaseWrapper().startH2Database();

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
