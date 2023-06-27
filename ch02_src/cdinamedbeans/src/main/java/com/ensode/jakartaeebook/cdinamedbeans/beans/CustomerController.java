package com.ensode.jakartaeebook.cdinamedbeans.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class CustomerController {

  private static final Logger logger = Logger.getLogger(
          CustomerController.class.getName());
  @Inject
  private Customer customer;

  public String saveCustomer() {

    logger.log(Level.INFO, "Saving the following information \n{0}", customer.
            toString());

    return "confirmation";
  }
}
