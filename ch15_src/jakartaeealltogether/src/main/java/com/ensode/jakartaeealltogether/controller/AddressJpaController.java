
package com.ensode.jakartaeealltogether.controller;

import com.ensode.jakartaeealltogether.controller.exceptions.NonexistentEntityException;
import com.ensode.jakartaeealltogether.controller.exceptions.PreexistingEntityException;
import com.ensode.jakartaeealltogether.controller.exceptions.RollbackFailureException;
import com.ensode.jakartaeealltogether.entity.Address;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.ensode.jakartaeealltogether.entity.AddressType;
import com.ensode.jakartaeealltogether.entity.Customer;
import com.ensode.jakartaeealltogether.entity.UsState;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import java.util.List;

@Named
@SessionScoped
public class AddressJpaController implements Serializable {

  public AddressJpaController(UserTransaction utx, EntityManagerFactory emf) {
    this.utx = utx;
    this.emf = emf;
  }
  private UserTransaction utx = null;
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Address address) throws PreexistingEntityException, RollbackFailureException, Exception {
    EntityManager em = null;
    try {
      utx.begin();
      em = getEntityManager();
      AddressType addressTypeId = address.getAddressTypeId();
      if (addressTypeId != null) {
        addressTypeId = em.getReference(addressTypeId.getClass(), addressTypeId.getAddressTypeId());
        address.setAddressTypeId(addressTypeId);
      }
      Customer customerId = address.getCustomerId();
      if (customerId != null) {
        customerId = em.getReference(customerId.getClass(), customerId.getCustomerId());
        address.setCustomerId(customerId);
      }
      UsState usStateId = address.getUsStateId();
      if (usStateId != null) {
        usStateId = em.getReference(usStateId.getClass(), usStateId.getUsStateId());
        address.setUsStateId(usStateId);
      }
      em.persist(address);
      if (addressTypeId != null) {
        addressTypeId.getAddressList().add(address);
        addressTypeId = em.merge(addressTypeId);
      }
      if (customerId != null) {
        customerId.getAddressList().add(address);
        customerId = em.merge(customerId);
      }
      if (usStateId != null) {
        usStateId.getAddressList().add(address);
        usStateId = em.merge(usStateId);
      }
      utx.commit();
    } catch (Exception ex) {
      try {
        utx.rollback();
      } catch (Exception re) {
        throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
      }
      if (findAddress(address.getAddressId()) != null) {
        throw new PreexistingEntityException("Address " + address + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Address address) throws NonexistentEntityException, RollbackFailureException, Exception {
    EntityManager em = null;
    try {
      utx.begin();
      em = getEntityManager();
      Address persistentAddress = em.find(Address.class, address.getAddressId());
      AddressType addressTypeIdOld = persistentAddress.getAddressTypeId();
      AddressType addressTypeIdNew = address.getAddressTypeId();
      Customer customerIdOld = persistentAddress.getCustomerId();
      Customer customerIdNew = address.getCustomerId();
      UsState usStateIdOld = persistentAddress.getUsStateId();
      UsState usStateIdNew = address.getUsStateId();
      if (addressTypeIdNew != null) {
        addressTypeIdNew = em.getReference(addressTypeIdNew.getClass(), addressTypeIdNew.getAddressTypeId());
        address.setAddressTypeId(addressTypeIdNew);
      }
      if (customerIdNew != null) {
        customerIdNew = em.getReference(customerIdNew.getClass(), customerIdNew.getCustomerId());
        address.setCustomerId(customerIdNew);
      }
      if (usStateIdNew != null) {
        usStateIdNew = em.getReference(usStateIdNew.getClass(), usStateIdNew.getUsStateId());
        address.setUsStateId(usStateIdNew);
      }
      address = em.merge(address);
      if (addressTypeIdOld != null && !addressTypeIdOld.equals(addressTypeIdNew)) {
        addressTypeIdOld.getAddressList().remove(address);
        addressTypeIdOld = em.merge(addressTypeIdOld);
      }
      if (addressTypeIdNew != null && !addressTypeIdNew.equals(addressTypeIdOld)) {
        addressTypeIdNew.getAddressList().add(address);
        addressTypeIdNew = em.merge(addressTypeIdNew);
      }
      if (customerIdOld != null && !customerIdOld.equals(customerIdNew)) {
        customerIdOld.getAddressList().remove(address);
        customerIdOld = em.merge(customerIdOld);
      }
      if (customerIdNew != null && !customerIdNew.equals(customerIdOld)) {
        customerIdNew.getAddressList().add(address);
        customerIdNew = em.merge(customerIdNew);
      }
      if (usStateIdOld != null && !usStateIdOld.equals(usStateIdNew)) {
        usStateIdOld.getAddressList().remove(address);
        usStateIdOld = em.merge(usStateIdOld);
      }
      if (usStateIdNew != null && !usStateIdNew.equals(usStateIdOld)) {
        usStateIdNew.getAddressList().add(address);
        usStateIdNew = em.merge(usStateIdNew);
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
        Integer id = address.getAddressId();
        if (findAddress(id) == null) {
          throw new NonexistentEntityException("The address with id " + id + " no longer exists.");
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
      Address address;
      try {
        address = em.getReference(Address.class, id);
        address.getAddressId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The address with id " + id + " no longer exists.", enfe);
      }
      AddressType addressTypeId = address.getAddressTypeId();
      if (addressTypeId != null) {
        addressTypeId.getAddressList().remove(address);
        addressTypeId = em.merge(addressTypeId);
      }
      Customer customerId = address.getCustomerId();
      if (customerId != null) {
        customerId.getAddressList().remove(address);
        customerId = em.merge(customerId);
      }
      UsState usStateId = address.getUsStateId();
      if (usStateId != null) {
        usStateId.getAddressList().remove(address);
        usStateId = em.merge(usStateId);
      }
      em.remove(address);
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

  public List<Address> findAddressEntities() {
    return findAddressEntities(true, -1, -1);
  }

  public List<Address> findAddressEntities(int maxResults, int firstResult) {
    return findAddressEntities(false, maxResults, firstResult);
  }

  private List<Address> findAddressEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Address.class));
      Query q = em.createQuery(cq);
      if (!all) {
        q.setMaxResults(maxResults);
        q.setFirstResult(firstResult);
      }
      return q.getResultList();
    } finally {
      em.close();
    }
  }

  public Address findAddress(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Address.class, id);
    } finally {
      em.close();
    }
  }

  public int getAddressCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Address> rt = cq.from(Address.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }

}