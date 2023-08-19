package com.ensode.jakartaeebook.criteriadelete.namedbean;

import com.ensode.jakartaeebook.criteriadelete.entity.Address;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;

@Named
@RequestScoped
public class CriteriaDeleteDemoBean {

  @PersistenceContext
  private EntityManager entityManager;

  @Resource
  private UserTransaction userTransaction;

  private int deletedRows;

  public String deleteData() {
    String retVal = "confirmation";

    try {

      userTransaction.begin();

      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      CriteriaDelete<Address> criteriaDelete
              = criteriaBuilder.createCriteriaDelete(Address.class);
      Root<Address> root = criteriaDelete.from(Address.class);
      criteriaDelete.where(criteriaBuilder.or(criteriaBuilder.equal(
              root.get("city"), "New York"),
              criteriaBuilder.equal(root.get("city"), "New York")));

      Query query = entityManager.createQuery(criteriaDelete);

      deletedRows = query.executeUpdate();
      userTransaction.commit();
    } catch (HeuristicMixedException
            | HeuristicRollbackException
            | NotSupportedException
            | RollbackException
            | SystemException
            | IllegalStateException
            | SecurityException e) {
      retVal = "error";
      e.printStackTrace();
    }
    return retVal;
  }

  public int getDeletedRows() {
    return deletedRows;
  }

  public void setDeletedRows(int updatedRows) {
    this.deletedRows = updatedRows;
  }
}
