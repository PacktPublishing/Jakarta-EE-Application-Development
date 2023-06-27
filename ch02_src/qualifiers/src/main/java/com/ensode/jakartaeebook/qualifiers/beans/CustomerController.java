package com.ensode.jakartaeebook.qualifiers.beans;

import java.util.logging.Logger;
import com.ensode.jakartaeebook.qualifiers.Premium;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.logging.Level;

@Named
@RequestScoped
public class CustomerController {

  private static final Logger logger = Logger.getLogger(
          CustomerController.class.getName());
  @Inject
  @Premium
  private Customer customer;

  public String saveCustomer() {

    PremiumCustomer premiumCustomer = (PremiumCustomer) customer;

    logger.log(Level.INFO, "Saving the following information \n{0} {1}, discount code = {2}",
            new Object[]{premiumCustomer.getFirstName(), premiumCustomer.getLastName(), premiumCustomer.getDiscountCode()});

    return "confirmation";
  }
}
