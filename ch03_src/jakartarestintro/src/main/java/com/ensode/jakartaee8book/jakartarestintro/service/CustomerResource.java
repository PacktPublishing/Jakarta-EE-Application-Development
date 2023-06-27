package com.ensode.jakartaee8book.jakartarestintro.service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("customer")
public class CustomerResource {

  private static final Logger LOG = Logger.getLogger(CustomerResource.class.getName());

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getCustomer() {
    //in a "real" RESTful service, we would retrieve data from a database
    //then return a JSON representation of the data.

    LOG.log(Level.INFO, "{0}.getCustomer() invoked", this.getClass().getCanonicalName());

    //Using a text block for readability
    //requires Java 15 or newer
    return """
           {
             "customer": {
               "id": 123,
               "firstName": "Joseph",
               "middleName": "William",
               "lastName": "Graystone"
             }
           }
           """;
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("customername")
  public String getCustomerName() {
    return "Joseph Graystone";
  }

  /**
   * Create a new customer
   *
   * @param customerJson representation of the customer to create
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public void createCustomer(String customerJson) {
    //in a "real" RESTful service, we would parse the JSON
    //received in the customer JSON parameter, then insert
    //a new row into the database.

    LOG.log(Level.INFO, "{0}.createCustomer() invoked", this.getClass().getCanonicalName());

    LOG.log(Level.INFO, "customerJson = {0}", customerJson);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void updateCustomer(String customerJson) {
    //in a "real" RESTful service, we would parse the JSON
    //received in the customer JSON parameter, then update
    //a row in the database.

    LOG.log(Level.INFO, "{0}.updateCustomer() invoked", this.getClass().getCanonicalName());

    LOG.log(Level.INFO, "customerJson = {0}", customerJson);
  }

}
