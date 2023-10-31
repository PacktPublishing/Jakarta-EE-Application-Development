package com.ensode.jakartaeebook;

import jakarta.ejb.EJB;

public class Client
{
  @EJB
  private static SecureCustomerDao secureCustomerDao;

  public static void main(String[] args)
  {
    Long newCustomerId;

    Customer customer = new Customer();
    customer.setFirstName("Mark");
    customer.setLastName("Butcher");
    customer.setEmail("butcher@phony.org");

    System.out.println("Saving New Customer...");
    newCustomerId = secureCustomerDao.saveNewCustomer(customer);

    System.out.println("Retrieving customer...");
    customer = secureCustomerDao.getCustomer(newCustomerId);
    System.out.println(customer);
  }
}
