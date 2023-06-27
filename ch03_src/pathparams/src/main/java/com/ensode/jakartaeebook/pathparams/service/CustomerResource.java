package com.ensode.jakartaeebook.pathparams.service;

import com.ensodejakartaeebook.pathparams.entity.Customer;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/customer/")
public class CustomerResource {

  private static final Logger LOG = Logger.getLogger(CustomerResource.class.getName());

  private Customer customer;

  public CustomerResource() {
    customer = new Customer(1L, "William",
            "Daniel", "Graystone");
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}/")
  public Customer getCustomer(@PathParam("id") Long id) {
    //in a "real" RESTful service, we would retrieve data from a database
    //using the supplied id.

    LOG.log(Level.INFO, "{0}.getCustomer() invoked, path parameter id = {1}", new Object[]{this.getClass().getCanonicalName(), id});

    return customer;
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
  @Path("{id}/")
  public void deleteCustomer(@PathParam("id") Long id) {
    //in a "real" RESTful service, we would delete the customer from the database

    LOG.log(Level.INFO, "{0}.deleteCustomer() invoked, id = {1}", new Object[]{this.getClass().getCanonicalName(), id});

    LOG.log(Level.INFO, "customer = {0}", customer);
  }
}
