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

    System.out.println("Received the following customer information:");
    System.out.println("Id: " + customer.getId());
    System.out.println("First Name: " + customer.getFirstName());
    System.out.println("Middle Name: " + customer.getMiddleName());
    System.out.println("Last Name: " + customer.getLastName());
  }
}
