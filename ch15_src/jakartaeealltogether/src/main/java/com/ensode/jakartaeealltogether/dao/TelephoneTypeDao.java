package com.ensode.jakartaeealltogether.dao;

import com.ensode.jakartaeealltogether.entity.TelephoneType;
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
public class TelephoneTypeDao implements Serializable {

  @PersistenceContext
  private EntityManager em;

  public List<TelephoneType> findTelephoneTypeEntities() {
    return findTelephoneTypeEntities(true, -1, -1);
  }

  private List<TelephoneType> findTelephoneTypeEntities(boolean all, int maxResults, int firstResult) {
    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
    cq.select(cq.from(TelephoneType.class));
    Query q = em.createQuery(cq);
    if (!all) {
      q.setMaxResults(maxResults);
      q.setFirstResult(firstResult);
    }
    return q.getResultList();
  }

  public TelephoneType findTelephoneType(Integer id) {
    return em.find(TelephoneType.class, id);
  }

}
