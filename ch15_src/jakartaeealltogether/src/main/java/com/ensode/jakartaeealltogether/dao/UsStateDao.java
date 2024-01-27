package com.ensode.jakartaeealltogether.dao;

import com.ensode.jakartaeealltogether.entity.UsState;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

@Stateless
public class UsStateDao implements Serializable {

  @PersistenceContext
  private EntityManager em;

  public List<UsState> findUsStateEntities() {
    return findUsStateEntities(true, -1, -1);
  }

  private List<UsState> findUsStateEntities(boolean all, int maxResults, int firstResult) {
    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
    cq.select(cq.from(UsState.class));
    Query q = em.createQuery(cq);
    if (!all) {
      q.setMaxResults(maxResults);
      q.setFirstResult(firstResult);
    }
    return q.getResultList();
  }

  public UsState findUsState(Integer id) {
    return em.find(UsState.class, id);
  }

}
