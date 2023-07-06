package com.ensode.jakartaeebook.microservices.crudcontroller.service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.ensode.jakartaeebook.microservices.crudcontroller.dto.Customer;
import com.ensode.jakartaeebook.microservices.crudcontroller.restclient.CustomerPersistenceClient;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/customercontroller")
public class CustomerControllerService {

  private static final Logger LOG = Logger.getLogger(CustomerControllerService.class.getName());

  public CustomerControllerService() {
  }

  @OPTIONS
  public Response options() {
    LOG.log(Level.INFO, "CustomerControllerService.options() invoked");
    return Response.ok("")
            .header("Access-Control-Allow-Origin", "http://localhost:8080")
            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
            .header("Access-Control-Allow-Credentials", "true")
            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
            .header("Access-Control-Max-Age", "1209600")
            .build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addCustomer(Customer customer) {
    LOG.log(Level.INFO, String.format("addCustomer() invoked with argument %s", customer));

    Response response;
    Response persistenceServiceResponse;

    CustomerPersistenceClient client = new CustomerPersistenceClient();

    persistenceServiceResponse = client.create(customer);
    client.close();

    if (persistenceServiceResponse.getStatus() == 201) {
      response = Response.ok("{}").
              header("Access-Control-Allow-Origin", "http://localhost:8080").build();

    } else {
      response = Response.serverError().
              header("Access-Control-Allow-Origin", "http://localhost:8080").build();
    }

    return response;
  }

}
