package com.ensode.jakartaeebook.queryparamsclient;

import com.ensode.jakartaeebook.queryparamsclient.entity.Customer;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

public class App {

  public static void main(String[] args) {
    App app = new App();
    app.getCustomer();
  }

  public void getCustomer() {
    Client client = ClientBuilder.newClient();
    Customer customer = client.target(
            "http://localhost:8080/queryparams/resources/customer").
            queryParam("id", 1L).
            request().get(Customer.class);

    System.out.println(String.format(
            "Received the following customer information:\n%s",
            customer));
  }
}
