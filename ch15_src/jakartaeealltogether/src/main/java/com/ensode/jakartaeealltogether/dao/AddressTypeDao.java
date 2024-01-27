package com.ensode.jakartaeealltogether.dao;

import com.ensode.jakartaeealltogether.entity.AddressType;
import jakarta.annotation.Resource;
import jakarta.ejb.EJBContext;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

@Stateless
public class AddressTypeDao implements Serializable {

  @PersistenceContext
  private EntityManager em;

  public List<AddressType> findAddressTypeEntities() {
    return findAddressTypeEntities(true, -1, -1);
  }

  private List<AddressType> findAddressTypeEntities(boolean all, int maxResults, int firstResult) {
    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
    cq.select(cq.from(AddressType.class));
    Query q = em.createQuery(cq);
    if (!all) {
      q.setMaxResults(maxResults);
      q.setFirstResult(firstResult);
    }
    return q.getResultList();
  }

  public AddressType findAddressType(Integer id) {
    return em.find(AddressType.class, id);
  }

}
