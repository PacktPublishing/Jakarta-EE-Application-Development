package com.ensode.jakartarestjavajsonclient;

import com.ensode.jakartarestjavajsonclient.entity.Customer;
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
    Customer customer = new Customer(456L, "Daniel", "Robert", "Hanson");
    Client client = ClientBuilder.newClient();

    client.target(
            "http://localhost:8080/jakartarestjavajson/resources/customer").
            request().put(
                    Entity.entity(customer, MediaType.APPLICATION_JSON),
                    Customer.class);
  }
}
