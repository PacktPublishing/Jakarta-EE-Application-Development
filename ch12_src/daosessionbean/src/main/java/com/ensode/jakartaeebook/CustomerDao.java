package com.ensode.jakartaeebook;

import jakarta.ejb.Remote;

@Remote
public interface CustomerDao {

  public void saveCustomer(Customer customer);

  public Customer getCustomer(Long customerId);

  public void deleteCustomer(Customer customer);
}
