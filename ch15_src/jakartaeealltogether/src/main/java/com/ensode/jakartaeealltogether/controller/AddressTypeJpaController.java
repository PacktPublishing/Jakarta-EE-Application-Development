
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
import com.ensode.jakartaeealltogether.entity.AddressType;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class AddressTypeJpaController implements Serializable {

  public AddressTypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
    this.utx = utx;
    this.emf = emf;
  }
  private UserTransaction utx = null;
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(AddressType addressType) throws PreexistingEntityException, RollbackFailureException, Exception {
    if (addressType.getAddressList() == null) {
      addressType.setAddressList(new ArrayList<Address>());
    }
    EntityManager em = null;
    try {
      utx.begin();
      em = getEntityManager();
      List<Address> attachedAddressList = new ArrayList<Address>();
      for (Address addressListAddressToAttach : addressType.getAddressList()) {
        addressListAddressToAttach = em.getReference(addressListAddressToAttach.getClass(), addressListAddressToAttach.getAddressId());
        attachedAddressList.add(addressListAddressToAttach);
      }
      addressType.setAddressList(attachedAddressList);
      em.persist(addressType);
      for (Address addressListAddress : addressType.getAddressList()) {
        AddressType oldAddressTypeIdOfAddressListAddress = addressListAddress.getAddressTypeId();
        addressListAddress.setAddressTypeId(addressType);
        addressListAddress = em.merge(addressListAddress);
        if (oldAddressTypeIdOfAddressListAddress != null) {
          oldAddressTypeIdOfAddressListAddress.getAddressList().remove(addressListAddress);
          oldAddressTypeIdOfAddressListAddress = em.merge(oldAddressTypeIdOfAddressListAddress);
        }
      }
      utx.commit();
    } catch (Exception ex) {
      try {
        utx.rollback();
      } catch (Exception re) {
        throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
      }
      if (findAddressType(addressType.getAddressTypeId()) != null) {
        throw new PreexistingEntityException("AddressType " + addressType + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(AddressType addressType) throws NonexistentEntityException, RollbackFailureException, Exception {
    EntityManager em = null;
    try {
      utx.begin();
      em = getEntityManager();
      AddressType persistentAddressType = em.find(AddressType.class, addressType.getAddressTypeId());
      List<Address> addressListOld = persistentAddressType.getAddressList();
      List<Address> addressListNew = addressType.getAddressList();
      List<Address> attachedAddressListNew = new ArrayList<Address>();
      for (Address addressListNewAddressToAttach : addressListNew) {
        addressListNewAddressToAttach = em.getReference(addressListNewAddressToAttach.getClass(), addressListNewAddressToAttach.getAddressId());
        attachedAddressListNew.add(addressListNewAddressToAttach);
      }
      addressListNew = attachedAddressListNew;
      addressType.setAddressList(addressListNew);
      addressType = em.merge(addressType);
      for (Address addressListOldAddress : addressListOld) {
        if (!addressListNew.contains(addressListOldAddress)) {
          addressListOldAddress.setAddressTypeId(null);
          addressListOldAddress = em.merge(addressListOldAddress);
        }
      }
      for (Address addressListNewAddress : addressListNew) {
        if (!addressListOld.contains(addressListNewAddress)) {
          AddressType oldAddressTypeIdOfAddressListNewAddress = addressListNewAddress.getAddressTypeId();
          addressListNewAddress.setAddressTypeId(addressType);
          addressListNewAddress = em.merge(addressListNewAddress);
          if (oldAddressTypeIdOfAddressListNewAddress != null && !oldAddressTypeIdOfAddressListNewAddress.equals(addressType)) {
            oldAddressTypeIdOfAddressListNewAddress.getAddressList().remove(addressListNewAddress);
            oldAddressTypeIdOfAddressListNewAddress = em.merge(oldAddressTypeIdOfAddressListNewAddress);
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
        Integer id = addressType.getAddressTypeId();
        if (findAddressType(id) == null) {
          throw new NonexistentEntityException("The addressType with id " + id + " no longer exists.");
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
      AddressType addressType;
      try {
        addressType = em.getReference(AddressType.class, id);
        addressType.getAddressTypeId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The addressType with id " + id + " no longer exists.", enfe);
      }
      List<Address> addressList = addressType.getAddressList();
      for (Address addressListAddress : addressList) {
        addressListAddress.setAddressTypeId(null);
        addressListAddress = em.merge(addressListAddress);
      }
      em.remove(addressType);
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

  public List<AddressType> findAddressTypeEntities() {
    return findAddressTypeEntities(true, -1, -1);
  }

  public List<AddressType> findAddressTypeEntities(int maxResults, int firstResult) {
    return findAddressTypeEntities(false, maxResults, firstResult);
  }

  private List<AddressType> findAddressTypeEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(AddressType.class));
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

  public AddressType findAddressType(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(AddressType.class, id);
    } finally {
      em.close();
    }
  }

  public int getAddressTypeCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<AddressType> rt = cq.from(AddressType.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }

}
