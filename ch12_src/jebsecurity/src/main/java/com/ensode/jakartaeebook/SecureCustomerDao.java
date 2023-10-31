package com.ensode.jakartaeebook;

import jakarta.ejb.Remote;

@Remote
public interface SecureCustomerDao {

  public void saveCustomer(Customer customer);

  public Long saveNewCustomer(Customer customer);

  public void updateCustomer(Customer customer);

  public Customer getCustomer(Long customerId);

  public void deleteCustomer(Customer customer);

}
