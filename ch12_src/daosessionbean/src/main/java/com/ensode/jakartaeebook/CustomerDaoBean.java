package com.ensode.jakartaeebook;

import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateful
public class CustomerDaoBean {

  @PersistenceContext
  private EntityManager entityManager;

  public void saveCustomer(Customer customer) {
    if (customer.getCustomerId() == null) {
      entityManager.persist(customer);
    } else {
      entityManager.merge(customer);
    }
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
