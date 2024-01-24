
package com.ensode.jakartaeealltogether.controller;

import com.ensode.jakartaeealltogether.controller.exceptions.NonexistentEntityException;
import com.ensode.jakartaeealltogether.controller.exceptions.PreexistingEntityException;
import com.ensode.jakartaeealltogether.controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.ensode.jakartaeealltogether.entity.Telephone;
import com.ensode.jakartaeealltogether.entity.TelephoneType;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class TelephoneTypeJpaController implements Serializable {

  public TelephoneTypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
    this.utx = utx;
    this.emf = emf;
  }
  private UserTransaction utx = null;
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(TelephoneType telephoneType) throws PreexistingEntityException, RollbackFailureException, Exception {
    if (telephoneType.getTelephoneList() == null) {
      telephoneType.setTelephoneList(new ArrayList<Telephone>());
    }
    EntityManager em = null;
    try {
      utx.begin();
      em = getEntityManager();
      List<Telephone> attachedTelephoneList = new ArrayList<Telephone>();
      for (Telephone telephoneListTelephoneToAttach : telephoneType.getTelephoneList()) {
        telephoneListTelephoneToAttach = em.getReference(telephoneListTelephoneToAttach.getClass(), telephoneListTelephoneToAttach.getTelephoneId());
        attachedTelephoneList.add(telephoneListTelephoneToAttach);
      }
      telephoneType.setTelephoneList(attachedTelephoneList);
      em.persist(telephoneType);
      for (Telephone telephoneListTelephone : telephoneType.getTelephoneList()) {
        TelephoneType oldTelephoneTypeIdOfTelephoneListTelephone = telephoneListTelephone.getTelephoneTypeId();
        telephoneListTelephone.setTelephoneTypeId(telephoneType);
        telephoneListTelephone = em.merge(telephoneListTelephone);
        if (oldTelephoneTypeIdOfTelephoneListTelephone != null) {
          oldTelephoneTypeIdOfTelephoneListTelephone.getTelephoneList().remove(telephoneListTelephone);
          oldTelephoneTypeIdOfTelephoneListTelephone = em.merge(oldTelephoneTypeIdOfTelephoneListTelephone);
        }
      }
      utx.commit();
    } catch (Exception ex) {
      try {
        utx.rollback();
      } catch (Exception re) {
        throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
      }
      if (findTelephoneType(telephoneType.getTelephoneTypeId()) != null) {
        throw new PreexistingEntityException("TelephoneType " + telephoneType + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(TelephoneType telephoneType) throws NonexistentEntityException, RollbackFailureException, Exception {
    EntityManager em = null;
    try {
      utx.begin();
      em = getEntityManager();
      TelephoneType persistentTelephoneType = em.find(TelephoneType.class, telephoneType.getTelephoneTypeId());
      List<Telephone> telephoneListOld = persistentTelephoneType.getTelephoneList();
      List<Telephone> telephoneListNew = telephoneType.getTelephoneList();
      List<Telephone> attachedTelephoneListNew = new ArrayList<Telephone>();
      for (Telephone telephoneListNewTelephoneToAttach : telephoneListNew) {
        telephoneListNewTelephoneToAttach = em.getReference(telephoneListNewTelephoneToAttach.getClass(), telephoneListNewTelephoneToAttach.getTelephoneId());
        attachedTelephoneListNew.add(telephoneListNewTelephoneToAttach);
      }
      telephoneListNew = attachedTelephoneListNew;
      telephoneType.setTelephoneList(telephoneListNew);
      telephoneType = em.merge(telephoneType);
      for (Telephone telephoneListOldTelephone : telephoneListOld) {
        if (!telephoneListNew.contains(telephoneListOldTelephone)) {
          telephoneListOldTelephone.setTelephoneTypeId(null);
          telephoneListOldTelephone = em.merge(telephoneListOldTelephone);
        }
      }
      for (Telephone telephoneListNewTelephone : telephoneListNew) {
        if (!telephoneListOld.contains(telephoneListNewTelephone)) {
          TelephoneType oldTelephoneTypeIdOfTelephoneListNewTelephone = telephoneListNewTelephone.getTelephoneTypeId();
          telephoneListNewTelephone.setTelephoneTypeId(telephoneType);
          telephoneListNewTelephone = em.merge(telephoneListNewTelephone);
          if (oldTelephoneTypeIdOfTelephoneListNewTelephone != null && !oldTelephoneTypeIdOfTelephoneListNewTelephone.equals(telephoneType)) {
            oldTelephoneTypeIdOfTelephoneListNewTelephone.getTelephoneList().remove(telephoneListNewTelephone);
            oldTelephoneTypeIdOfTelephoneListNewTelephone = em.merge(oldTelephoneTypeIdOfTelephoneListNewTelephone);
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
        Integer id = telephoneType.getTelephoneTypeId();
        if (findTelephoneType(id) == null) {
          throw new NonexistentEntityException("The telephoneType with id " + id + " no longer exists.");
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
      TelephoneType telephoneType;
      try {
        telephoneType = em.getReference(TelephoneType.class, id);
        telephoneType.getTelephoneTypeId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The telephoneType with id " + id + " no longer exists.", enfe);
      }
      List<Telephone> telephoneList = telephoneType.getTelephoneList();
      for (Telephone telephoneListTelephone : telephoneList) {
        telephoneListTelephone.setTelephoneTypeId(null);
        telephoneListTelephone = em.merge(telephoneListTelephone);
      }
      em.remove(telephoneType);
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

  public List<TelephoneType> findTelephoneTypeEntities() {
    return findTelephoneTypeEntities(true, -1, -1);
  }

  public List<TelephoneType> findTelephoneTypeEntities(int maxResults, int firstResult) {
    return findTelephoneTypeEntities(false, maxResults, firstResult);
  }

  private List<TelephoneType> findTelephoneTypeEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(TelephoneType.class));
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

  public TelephoneType findTelephoneType(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(TelephoneType.class, id);
    } finally {
      em.close();
    }
  }

  public int getTelephoneTypeCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<TelephoneType> rt = cq.from(TelephoneType.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }

}
