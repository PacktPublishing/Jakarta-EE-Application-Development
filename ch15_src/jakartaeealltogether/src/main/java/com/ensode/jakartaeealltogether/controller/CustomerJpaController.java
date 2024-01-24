package com.ensode.jakartaeealltogether.controller;

import com.ensode.jakartaeealltogether.controller.exceptions.NonexistentEntityException;
import com.ensode.jakartaeealltogether.controller.exceptions.PreexistingEntityException;
import com.ensode.jakartaeealltogether.controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import com.ensode.jakartaeealltogether.entity.Address;
import com.ensode.jakartaeealltogether.entity.Customer;
import com.ensode.jakartaeealltogether.entity.Telephone;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;

@Named
@SessionScoped
public class CustomerJpaController implements Serializable {

  public CustomerJpaController(UserTransaction utx, EntityManagerFactory emf) {
    this.utx = utx;
    this.emf = emf;
  }
  private UserTransaction utx = null;
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Customer customer) throws PreexistingEntityException, RollbackFailureException, Exception {

    if (customer.getAddressList() == null) {
      customer.setAddressList(new ArrayList<>());
    }
    if (customer.getTelephoneList() == null) {
      customer.setTelephoneList(new ArrayList<>());
    }
    EntityManager em = null;
    try {
      utx.begin();
      em = getEntityManager();
      em.persist(customer.getAddressList().get(0));
      em.persist(customer.getTelephoneList().get(0));
      em.persist(customer);

      for (Address addressListAddress : customer.getAddressList()) {
        Customer oldCustomerIdOfAddressListAddress = addressListAddress.getCustomerId();
        addressListAddress.setCustomerId(customer);
        addressListAddress = em.merge(addressListAddress);
        if (oldCustomerIdOfAddressListAddress != null) {
          oldCustomerIdOfAddressListAddress.getAddressList().remove(addressListAddress);
          oldCustomerIdOfAddressListAddress = em.merge(oldCustomerIdOfAddressListAddress);
        }
      }
      for (Telephone telephoneListTelephone : customer.getTelephoneList()) {
        Customer oldCustomerIdOfTelephoneListTelephone = telephoneListTelephone.getCustomerId();
        telephoneListTelephone.setCustomerId(customer);
        telephoneListTelephone = em.merge(telephoneListTelephone);
        if (oldCustomerIdOfTelephoneListTelephone != null) {
          oldCustomerIdOfTelephoneListTelephone.getTelephoneList().remove(telephoneListTelephone);
          oldCustomerIdOfTelephoneListTelephone = em.merge(oldCustomerIdOfTelephoneListTelephone);
        }
      }
      utx.commit();
    } catch (Exception ex) {
      try {
        utx.rollback();
      } catch (Exception re) {
        throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
      }
      if (findCustomer(customer.getCustomerId()) != null) {
        throw new PreexistingEntityException("Customer " + customer + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Customer customer) throws NonexistentEntityException, RollbackFailureException, Exception {
    EntityManager em = null;
    try {
      utx.begin();
      em = getEntityManager();
      Customer persistentCustomer = em.find(Customer.class, customer.getCustomerId());
      List<Address> addressListOld = persistentCustomer.getAddressList();
      List<Address> addressListNew = customer.getAddressList();
      List<Telephone> telephoneListOld = persistentCustomer.getTelephoneList();
      List<Telephone> telephoneListNew = customer.getTelephoneList();

      List<Address> attachedAddressListNew = new ArrayList<Address>();
      for (Address addressListNewAddressToAttach : addressListNew) {
        addressListNewAddressToAttach = em.getReference(addressListNewAddressToAttach.getClass(), addressListNewAddressToAttach.getAddressId());
        attachedAddressListNew.add(addressListNewAddressToAttach);
      }
      addressListNew = attachedAddressListNew;
      customer.setAddressList(addressListNew);
      List<Telephone> attachedTelephoneListNew = new ArrayList<Telephone>();
      for (Telephone telephoneListNewTelephoneToAttach : telephoneListNew) {
        telephoneListNewTelephoneToAttach = em.getReference(telephoneListNewTelephoneToAttach.getClass(), telephoneListNewTelephoneToAttach.getTelephoneId());
        attachedTelephoneListNew.add(telephoneListNewTelephoneToAttach);
      }
      telephoneListNew = attachedTelephoneListNew;
      customer.setTelephoneList(telephoneListNew);
      customer = em.merge(customer);
      for (Address addressListOldAddress : addressListOld) {
        if (!addressListNew.contains(addressListOldAddress)) {
          addressListOldAddress.setCustomerId(null);
          addressListOldAddress = em.merge(addressListOldAddress);
        }
      }
      for (Address addressListNewAddress : addressListNew) {
        if (!addressListOld.contains(addressListNewAddress)) {
          Customer oldCustomerIdOfAddressListNewAddress = addressListNewAddress.getCustomerId();
          addressListNewAddress.setCustomerId(customer);
          addressListNewAddress = em.merge(addressListNewAddress);
          if (oldCustomerIdOfAddressListNewAddress != null && !oldCustomerIdOfAddressListNewAddress.equals(customer)) {
            oldCustomerIdOfAddressListNewAddress.getAddressList().remove(addressListNewAddress);
            oldCustomerIdOfAddressListNewAddress = em.merge(oldCustomerIdOfAddressListNewAddress);
          }
        }
      }
      for (Telephone telephoneListOldTelephone : telephoneListOld) {
        if (!telephoneListNew.contains(telephoneListOldTelephone)) {
          telephoneListOldTelephone.setCustomerId(null);
          telephoneListOldTelephone = em.merge(telephoneListOldTelephone);
        }
      }
      for (Telephone telephoneListNewTelephone : telephoneListNew) {
        if (!telephoneListOld.contains(telephoneListNewTelephone)) {
          Customer oldCustomerIdOfTelephoneListNewTelephone = telephoneListNewTelephone.getCustomerId();
          telephoneListNewTelephone.setCustomerId(customer);
          telephoneListNewTelephone = em.merge(telephoneListNewTelephone);
          if (oldCustomerIdOfTelephoneListNewTelephone != null && !oldCustomerIdOfTelephoneListNewTelephone.equals(customer)) {
            oldCustomerIdOfTelephoneListNewTelephone.getTelephoneList().remove(telephoneListNewTelephone);
            oldCustomerIdOfTelephoneListNewTelephone = em.merge(oldCustomerIdOfTelephoneListNewTelephone);
          }
        }
      }
      utx.commit();
    } catch (Exception ex) {
      try {
        utx.rollback();
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
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
    EntityManager em = null;
    try {
      utx.begin();
      em = getEntityManager();
      Customer customer;
      try {
        customer = em.getReference(Customer.class, id);
        customer.getCustomerId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The customer with id " + id + " no longer exists.", enfe);
      }
      List<Address> addressList = customer.getAddressList();
      for (Address addressListAddress : addressList) {
        addressListAddress.setCustomerId(null);
        addressListAddress = em.merge(addressListAddress);
      }
      List<Telephone> telephoneList = customer.getTelephoneList();
      for (Telephone telephoneListTelephone : telephoneList) {
        telephoneListTelephone.setCustomerId(null);
        telephoneListTelephone = em.merge(telephoneListTelephone);
      }
      em.remove(customer);
      utx.commit();
    } catch (Exception ex) {
      try {
        utx.rollback();
      } catch (Exception re) {
        throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
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
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Customer.class));
      Query q = em.createQuery(cq);
      if (!all) {
        q.setMaxResults(maxResults);
        q.setFirstResult(firstResult);
      }
      customerEntities = q.getResultList();
      return customerEntities;
    } finally {
      em.close();
    }
  }

  public Customer findCustomer(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Customer.class, id);
    } finally {
      em.close();
    }
  }

  public int getCustomerCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Customer> rt = cq.from(Customer.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }

}