package com.ensode.jakartaeebook.jakartarestintroclient;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;

public class App {

  public static void main(String[] args) {
    App app = new App();
    app.insertCustomer();
  }

  public void insertCustomer() {
    Client client = ClientBuilder.newClient();
    String customerJson = """
                          {
                            "customer": {
                              "id": 234,
                              "firstName": "Tamara",
                              "middleName": "Adeline",
                              "lastName": "Graystone"
                            }
                          }
                          """;

    client.target(
            "http://localhost:8080/jakartarestintro/resources/customer").
            request().put(
                    Entity.entity(customerJson, MediaType.APPLICATION_JSON),
                    String.class);
  }
}
