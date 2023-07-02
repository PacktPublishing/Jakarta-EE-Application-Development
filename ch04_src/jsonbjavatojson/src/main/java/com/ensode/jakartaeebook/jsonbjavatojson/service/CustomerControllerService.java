package com.ensode.jakartaeebook.jsonbjavatojson.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.ensode.jakartaeebook.jsonbjavatojson.dto.Customer;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/customercontroller")
public class CustomerControllerService {

  @GET
  public String getCustomerAsJson() {
    String jsonString;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    Customer customer = new Customer("Mr", "David", "Raymond", "Heffelfinger", LocalDate.parse("03/03/1997", dateTimeFormatter));

    Jsonb jsonb = JsonbBuilder.create();

    jsonString = jsonb.toJson(customer);

    return jsonString;
  }

}
