package com.ensode.jakartaee;

import jakarta.ejb.Remote;
import java.util.List;


@Remote
public interface CustomerDaoBmt {
  public void saveMultipleNewCustomers(List<Customer> customerList);
}
