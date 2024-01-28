package com.ensode.jakartaeealltogether.dao;

import com.ensode.jakartaeealltogether.dao.exceptions.NonexistentEntityException;
import com.ensode.jakartaeealltogether.dao.exceptions.PreexistingEntityException;
import com.ensode.jakartaeealltogether.dao.exceptions.RollbackFailureException;
import com.ensode.jakartaeealltogether.entity.Address;
import com.ensode.jakartaeealltogether.entity.Customer;
import com.ensode.jakartaeealltogether.entity.Telephone;
import jakarta.annotation.Resource;
import jakarta.ejb.EJBContext;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CustomerDao implements Serializable {

  @Resource
  private EJBContext ejbContext;

  @PersistenceContext
  private EntityManager em;

  public void create(Customer customer) throws PreexistingEntityException, RollbackFailureException, Exception {

    if (customer.getAddressList() == null) {
      customer.setAddressList(new ArrayList<>());
    }
    if (customer.getTelephoneList() == null) {
      customer.setTelephoneList(new ArrayList<>());
    }
    try {
      em.persist(customer.getAddressList().get(0));
      em.persist(customer.getTelephoneList().get(0));
      em.persist(customer);
    } catch (Exception ex) {
      try {
        ejbContext.setRollbackOnly();
      } catch (Exception re) {
        throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
      }
      if (findCustomer(customer.getCustomerId()) != null) {
        throw new PreexistingEntityException("Customer " + customer + " already exists.", ex);
      }
      throw ex;
    }
  }

  public void edit(Customer customer) throws NonexistentEntityException, RollbackFailureException, Exception {
    try {
      customer = em.merge(customer);
    } catch (Exception ex) {
      try {
        ejbContext.setRollbackOnly();
      } catch (Exception re) {
        throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
      }
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Integer id = customer.getCustomerId();
        if (findCustomer(id) == null) {
          throw new NonexistentEntityException("The customer with id " + id + " no longer exists.");
        }
      }
      throw ex;
    }
  }

  public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
    try {
      Customer customer;
      try {
        customer = em.getReference(Customer.class, id);
        customer.getCustomerId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The customer with id " + id + " no longer exists.", enfe);
      }
      List<Address> addressList = customer.getAddressList();
      for (Address addressListAddress : addressList) {
        addressListAddress.setCustomer(null);
        addressListAddress = em.merge(addressListAddress);
      }
      List<Telephone> telephoneList = customer.getTelephoneList();
      for (Telephone telephoneListTelephone : telephoneList) {
        telephoneListTelephone.setCustomer(null);
        telephoneListTelephone = em.merge(telephoneListTelephone);
      }
      em.remove(customer);
    } catch (Exception ex) {
      try {
        ejbContext.setRollbackOnly();
      } catch (Exception re) {
        throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
      }
      throw ex;
    }
  }

  public List<Customer> findCustomerEntities() {
    return findCustomerEntities(true, -1, -1);
  }

  public List<Customer> findCustomerEntities(int maxResults, int firstResult) {
    return findCustomerEntities(false, maxResults, firstResult);
  }

  private List<Customer> findCustomerEntities(boolean all, int maxResults, int firstResult) {
    List<Customer> customerEntities;
    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
    cq.select(cq.from(Customer.class));
    Query q = em.createQuery(cq);
    if (!all) {
      q.setMaxResults(maxResults);
      q.setFirstResult(firstResult);
    }
    customerEntities = q.getResultList();
    return customerEntities;

  }

  public Customer findCustomer(Integer id) {
    return em.find(Customer.class, id);
  }

  public int getCustomerCount() {
    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
    Root<Customer> rt = cq.from(Customer.class);
    cq.select(em.getCriteriaBuilder().count(rt));
    Query q = em.createQuery(cq);
    return ((Long) q.getSingleResult()).intValue();
  }

}
