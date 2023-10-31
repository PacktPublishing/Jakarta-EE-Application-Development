package com.ensode.jakartaeebook;

import jakarta.ejb.Remote;

@Remote
public interface CustomerDaoRollback {

  public abstract void saveNewCustomer(Customer customer);

  public abstract void updateCustomer(Customer customer);

  public abstract Customer getCustomer(Long customerId);

  public abstract void deleteCustomer(Customer customer);
}
