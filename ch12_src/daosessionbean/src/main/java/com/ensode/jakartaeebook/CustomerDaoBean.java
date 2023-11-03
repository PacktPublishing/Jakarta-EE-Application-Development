package com.ensode.jakartaeebook;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateful
@LocalBean
public class CustomerDaoBean implements CustomerDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void saveCustomer(Customer customer) {
    if (customer.getCustomerId() == null) {
      entityManager.persist(customer);
    } else {
      entityManager.merge(customer);
    }
  }

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
