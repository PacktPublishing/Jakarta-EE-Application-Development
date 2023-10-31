package com.ensode.jakartaeebook;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateful
@LocalBean
public class CustomerDaoBean {

  @PersistenceContext
  private EntityManager entityManager;

  public void saveCustomer(Customer customer) {
    if (customer.getCustomerId() == null) {
      saveNewCustomer(customer);
    } else {
      updateCustomer(customer);
    }
  }

  private void saveNewCustomer(Customer customer) {
    entityManager.persist(customer);
  }

  private void updateCustomer(Customer customer) {
    entityManager.merge(customer);
  }

  public Customer getCustomer(Long customerId) {
    Customer customer;

    customer = entityManager.find(Customer.class, customerId);

    return customer;
  }

  public void deleteCustomer(Customer customer) {
    entityManager.remove(customer);
  }
}
