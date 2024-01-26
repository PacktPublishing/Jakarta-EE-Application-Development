
package com.ensode.jakartaeealltogether.controller;

import com.ensode.jakartaeealltogether.controller.exceptions.NonexistentEntityException;
import com.ensode.jakartaeealltogether.controller.exceptions.PreexistingEntityException;
import com.ensode.jakartaeealltogether.controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.ensode.jakartaeealltogether.entity.Customer;
import com.ensode.jakartaeealltogether.entity.Telephone;
import com.ensode.jakartaeealltogether.entity.TelephoneType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import java.util.List;


public class TelephoneJpaController implements Serializable {

  public TelephoneJpaController(UserTransaction utx, EntityManagerFactory emf) {
    this.utx = utx;
    this.emf = emf;
  }
  private UserTransaction utx = null;
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Telephone telephone) throws PreexistingEntityException, RollbackFailureException, Exception {
    EntityManager em = null;
    try {
      utx.begin();
      em = getEntityManager();
      Customer customerId = telephone.getCustomerId();
      if (customerId != null) {
        customerId = em.getReference(customerId.getClass(), customerId.getCustomerId());
        telephone.setCustomerId(customerId);
      }
      TelephoneType telephoneTypeId = telephone.getTelephoneTypeId();
      if (telephoneTypeId != null) {
        telephoneTypeId = em.getReference(telephoneTypeId.getClass(), telephoneTypeId.getTelephoneTypeId());
        telephone.setTelephoneTypeId(telephoneTypeId);
      }
      em.persist(telephone);
      if (customerId != null) {
        customerId.getTelephoneList().add(telephone);
        customerId = em.merge(customerId);
      }
      if (telephoneTypeId != null) {
        telephoneTypeId.getTelephoneList().add(telephone);
        telephoneTypeId = em.merge(telephoneTypeId);
      }
      utx.commit();
    } catch (Exception ex) {
      try {
        utx.rollback();
      } catch (Exception re) {
        throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
      }
      if (findTelephone(telephone.getTelephoneId()) != null) {
        throw new PreexistingEntityException("Telephone " + telephone + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Telephone telephone) throws NonexistentEntityException, RollbackFailureException, Exception {
    EntityManager em = null;
    try {
      utx.begin();
      em = getEntityManager();
      Telephone persistentTelephone = em.find(Telephone.class, telephone.getTelephoneId());
      Customer customerIdOld = persistentTelephone.getCustomerId();
      Customer customerIdNew = telephone.getCustomerId();
      TelephoneType telephoneTypeIdOld = persistentTelephone.getTelephoneTypeId();
      TelephoneType telephoneTypeIdNew = telephone.getTelephoneTypeId();
      if (customerIdNew != null) {
        customerIdNew = em.getReference(customerIdNew.getClass(), customerIdNew.getCustomerId());
        telephone.setCustomerId(customerIdNew);
      }
      if (telephoneTypeIdNew != null) {
        telephoneTypeIdNew = em.getReference(telephoneTypeIdNew.getClass(), telephoneTypeIdNew.getTelephoneTypeId());
        telephone.setTelephoneTypeId(telephoneTypeIdNew);
      }
      telephone = em.merge(telephone);
      if (customerIdOld != null && !customerIdOld.equals(customerIdNew)) {
        customerIdOld.getTelephoneList().remove(telephone);
        customerIdOld = em.merge(customerIdOld);
      }
      if (customerIdNew != null && !customerIdNew.equals(customerIdOld)) {
        customerIdNew.getTelephoneList().add(telephone);
        customerIdNew = em.merge(customerIdNew);
      }
      if (telephoneTypeIdOld != null && !telephoneTypeIdOld.equals(telephoneTypeIdNew)) {
        telephoneTypeIdOld.getTelephoneList().remove(telephone);
        telephoneTypeIdOld = em.merge(telephoneTypeIdOld);
      }
      if (telephoneTypeIdNew != null && !telephoneTypeIdNew.equals(telephoneTypeIdOld)) {
        telephoneTypeIdNew.getTelephoneList().add(telephone);
        telephoneTypeIdNew = em.merge(telephoneTypeIdNew);
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
        Integer id = telephone.getTelephoneId();
        if (findTelephone(id) == null) {
          throw new NonexistentEntityException("The telephone with id " + id + " no longer exists.");
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
      Telephone telephone;
      try {
        telephone = em.getReference(Telephone.class, id);
        telephone.getTelephoneId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The telephone with id " + id + " no longer exists.", enfe);
      }
      Customer customerId = telephone.getCustomerId();
      if (customerId != null) {
        customerId.getTelephoneList().remove(telephone);
        customerId = em.merge(customerId);
      }
      TelephoneType telephoneTypeId = telephone.getTelephoneTypeId();
      if (telephoneTypeId != null) {
        telephoneTypeId.getTelephoneList().remove(telephone);
        telephoneTypeId = em.merge(telephoneTypeId);
      }
      em.remove(telephone);
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

  public List<Telephone> findTelephoneEntities() {
    return findTelephoneEntities(true, -1, -1);
  }

  public List<Telephone> findTelephoneEntities(int maxResults, int firstResult) {
    return findTelephoneEntities(false, maxResults, firstResult);
  }

  private List<Telephone> findTelephoneEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Telephone.class));
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

  public Telephone findTelephone(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Telephone.class, id);
    } finally {
      em.close();
    }
  }

  public int getTelephoneCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Telephone> rt = cq.from(Telephone.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }

}
