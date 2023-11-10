package com.ensode.jakartaeebook;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
@RolesAllowed("admin")
public class SecureCustomerDaoBean {

  @PersistenceContext
  private EntityManager entityManager;

  public Long saveCustomer(Customer customer) {
    if (customer.getCustomerId() == null) {
      entityManager.persist(customer);
    } else {
      entityManager.merge(customer);
    }
    return customer.getCustomerId();
  }

  @RolesAllowed({"user", "admin"})
  public Customer getCustomer(Long customerId) {
    Customer customer;

    customer = entityManager.find(Customer.class, customerId);
    return customer;
  }

  public void deleteCustomer(Customer customer) {
    entityManager.remove(customer);
  }
}
