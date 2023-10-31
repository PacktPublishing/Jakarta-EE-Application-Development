package com.ensode.jakartaeebook;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Stateless
@RolesAllowed("appadmin")
public class SecureCustomerDaoBean implements SecureCustomerDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void saveCustomer(Customer customer) {
    if (customer.getCustomerId() == null) {
      saveNewCustomer(customer);
    } else {
      updateCustomer(customer);
    }
  }

  @Override
  public Long saveNewCustomer(Customer customer) {
    entityManager.persist(customer);

    return customer.getCustomerId();
  }

  @Override
  public void updateCustomer(Customer customer) {
    entityManager.merge(customer);
  }

  @RolesAllowed(
      {"appuser", "appadmin"})
  @Override
  public Customer getCustomer(Long customerId) {
    Customer customer;

    customer = entityManager.find(Customer.class, customerId);

    return customer;
  }

  @Override
  public void deleteCustomer(Customer customer) {
    entityManager.remove(customer);
  }
}
