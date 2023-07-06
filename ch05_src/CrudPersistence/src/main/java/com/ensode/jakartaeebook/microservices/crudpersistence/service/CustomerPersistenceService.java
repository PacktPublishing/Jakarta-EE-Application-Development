package com.ensode.jakartaeebook.microservices.crudpersistence.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import com.ensode.jakartaeebook.microservices.crudpersistence.Customer;
import com.ensode.jakartaeebook.microservices.crudpersistence.dao.CrudDao;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@Path("customerpersistence")
public class CustomerPersistenceService {

  private static final Logger LOG = Logger.getLogger(CustomerPersistenceService.class.getName());

  @Context
  private UriInfo uriInfo;

  @Inject
  private CrudDao customerDao;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response create(Customer customer) {
    LOG.log(Level.INFO, String.format("create() invoked with argument %s", customer));

    try {
      customerDao.create(customer);
    } catch (Exception e) {
      LOG.log(Level.SEVERE, "Exception caught", e);
      return Response.serverError().build();
    }

    return Response.created(uriInfo.getAbsolutePath()).build();
  }

}
