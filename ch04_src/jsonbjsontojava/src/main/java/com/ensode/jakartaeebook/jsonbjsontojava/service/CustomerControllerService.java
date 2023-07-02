package com.ensode.jakartaeebook.jsonbjsontojava.service;

import com.ensode.jakartaeebook.jsonbjsontojava.dto.Customer;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/customercontroller")
public class CustomerControllerService {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String addCustomer(String customerJson) {
    Jsonb jsonb = JsonbBuilder.create();

    Customer customer = jsonb.fromJson(customerJson, Customer.class);

    return customer.toString();
  }

}
