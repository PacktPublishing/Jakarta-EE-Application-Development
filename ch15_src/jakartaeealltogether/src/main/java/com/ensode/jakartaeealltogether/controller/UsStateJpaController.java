
package com.ensode.jakartaeealltogether.controller;

import com.ensode.jakartaeealltogether.controller.exceptions.NonexistentEntityException;
import com.ensode.jakartaeealltogether.controller.exceptions.PreexistingEntityException;
import com.ensode.jakartaeealltogether.controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.ensode.jakartaeealltogether.entity.Address;
import com.ensode.jakartaeealltogether.entity.UsState;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class UsStateJpaController implements Serializable {

  public UsStateJpaController(UserTransaction utx, EntityManagerFactory emf) {
    this.utx = utx;
    this.emf = emf;
  }
  private UserTransaction utx = null;
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(UsState usState) throws PreexistingEntityException, RollbackFailureException, Exception {
    if (usState.getAddressList() == null) {
      usState.setAddressList(new ArrayList<Address>());
    }
    EntityManager em = null;
    try {
      utx.begin();
      em = getEntityManager();
      List<Address> attachedAddressList = new ArrayList<Address>();
      for (Address addressListAddressToAttach : usState.getAddressList()) {
        addressListAddressToAttach = em.getReference(addressListAddressToAttach.getClass(), addressListAddressToAttach.getAddressId());
        attachedAddressList.add(addressListAddressToAttach);
      }
      usState.setAddressList(attachedAddressList);
      em.persist(usState);
      for (Address addressListAddress : usState.getAddressList()) {
        UsState oldUsStateIdOfAddressListAddress = addressListAddress.getUsStateId();
        addressListAddress.setUsStateId(usState);
        addressListAddress = em.merge(addressListAddress);
        if (oldUsStateIdOfAddressListAddress != null) {
          oldUsStateIdOfAddressListAddress.getAddressList().remove(addressListAddress);
          oldUsStateIdOfAddressListAddress = em.merge(oldUsStateIdOfAddressListAddress);
        }
      }
      utx.commit();
    } catch (Exception ex) {
      try {
        utx.rollback();
      } catch (Exception re) {
        throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
      }
      if (findUsState(usState.getUsStateId()) != null) {
        throw new PreexistingEntityException("UsState " + usState + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(UsState usState) throws NonexistentEntityException, RollbackFailureException, Exception {
    EntityManager em = null;
    try {
      utx.begin();
      em = getEntityManager();
      UsState persistentUsState = em.find(UsState.class, usState.getUsStateId());
      List<Address> addressListOld = persistentUsState.getAddressList();
      List<Address> addressListNew = usState.getAddressList();
      List<Address> attachedAddressListNew = new ArrayList<Address>();
      for (Address addressListNewAddressToAttach : addressListNew) {
        addressListNewAddressToAttach = em.getReference(addressListNewAddressToAttach.getClass(), addressListNewAddressToAttach.getAddressId());
        attachedAddressListNew.add(addressListNewAddressToAttach);
      }
      addressListNew = attachedAddressListNew;
      usState.setAddressList(addressListNew);
      usState = em.merge(usState);
      for (Address addressListOldAddress : addressListOld) {
        if (!addressListNew.contains(addressListOldAddress)) {
          addressListOldAddress.setUsStateId(null);
          addressListOldAddress = em.merge(addressListOldAddress);
        }
      }
      for (Address addressListNewAddress : addressListNew) {
        if (!addressListOld.contains(addressListNewAddress)) {
          UsState oldUsStateIdOfAddressListNewAddress = addressListNewAddress.getUsStateId();
          addressListNewAddress.setUsStateId(usState);
          addressListNewAddress = em.merge(addressListNewAddress);
          if (oldUsStateIdOfAddressListNewAddress != null && !oldUsStateIdOfAddressListNewAddress.equals(usState)) {
            oldUsStateIdOfAddressListNewAddress.getAddressList().remove(addressListNewAddress);
            oldUsStateIdOfAddressListNewAddress = em.merge(oldUsStateIdOfAddressListNewAddress);
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
        Integer id = usState.getUsStateId();
        if (findUsState(id) == null) {
          throw new NonexistentEntityException("The usState with id " + id + " no longer exists.");
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
      UsState usState;
      try {
        usState = em.getReference(UsState.class, id);
        usState.getUsStateId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The usState with id " + id + " no longer exists.", enfe);
      }
      List<Address> addressList = usState.getAddressList();
      for (Address addressListAddress : addressList) {
        addressListAddress.setUsStateId(null);
        addressListAddress = em.merge(addressListAddress);
      }
      em.remove(usState);
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

  public List<UsState> findUsStateEntities() {
    return findUsStateEntities(true, -1, -1);
  }

  public List<UsState> findUsStateEntities(int maxResults, int firstResult) {
    return findUsStateEntities(false, maxResults, firstResult);
  }

  private List<UsState> findUsStateEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(UsState.class));
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

  public UsState findUsState(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(UsState.class, id);
    } finally {
      em.close();
    }
  }

  public int getUsStateCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<UsState> rt = cq.from(UsState.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }

}
