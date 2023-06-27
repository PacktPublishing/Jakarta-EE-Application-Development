package com.ensode.jakartaeebook.queryparams.service;

import com.ensode.jakartaeebook.queryparams.entity.Customer;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("customer")
public class CustomerResource {

  private static final Logger LOG = Logger.getLogger(CustomerResource.class.getName());

  private final Customer customer;

  public CustomerResource() {
    customer = new Customer(1L, "Samuel",
            "Joseph", "Willow");
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Customer getCustomer(@QueryParam("id") Long id) {
    //in a "real" RESTful service, we would retrieve data from a database
    //using the supplied id.

    LOG.log(Level.INFO, "{0}.getCustomer() invoked, id = {1}", new Object[]{this.getClass().getCanonicalName(), id});
    return new Customer(id, "Dummy", null, "Customer");
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public void createCustomer(Customer customer) {
    //in a "real" RESTful service, we would insert
    //a new row into the database.

    LOG.log(Level.INFO, "{0}.createCustomer() invoked", this.getClass().getCanonicalName());

    LOG.log(Level.INFO, "customer = {0}", customer);

  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void updateCustomer(Customer customer) {
    //in a "real" RESTful service, we would update the database

    LOG.log(Level.INFO, "{0}.updateCustomer() invoked", this.getClass().getCanonicalName());

    LOG.log(Level.INFO, "customer= {0}", customer);
  }

  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
  public void deleteCustomer(@QueryParam("id") Long id) {
    //in a "real" RESTful service, we would delete a row from the database

    LOG.log(Level.INFO, "{0}.deleteCustomer() invoked, id = {1}", new Object[]{this.getClass().getCanonicalName(), id});

  }
}
