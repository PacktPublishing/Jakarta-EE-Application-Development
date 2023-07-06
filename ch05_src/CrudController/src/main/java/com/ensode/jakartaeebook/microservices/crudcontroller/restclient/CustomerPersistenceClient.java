package com.ensode.jakartaeebook.microservices.crudcontroller.restclient;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.ensode.jakartaeebook.microservices.crudcontroller.dto.Customer;

public class CustomerPersistenceClient {

  private final WebTarget webTarget;
  private final Client client;
  private static final String BASE_URI = "http://localhost:8080/CrudPersistence/resources";

  public CustomerPersistenceClient() {
    client = ClientBuilder.newClient();
    webTarget = client.target(BASE_URI).path("customerpersistence");
  }

  public Response create(Customer customer) throws ClientErrorException {
    return webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(customer,
            MediaType.APPLICATION_JSON), Response.class);
  }

  public void close() {
    client.close();
  }

}
