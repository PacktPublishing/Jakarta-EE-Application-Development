package com.ensode.jakartaeebook.entityrelationship.namedbean;

import com.ensode.jakartaeebook.entityrelationship.entity.Customer;
import com.ensode.jakartaeebook.entityrelationship.entity.LoginInfo;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;

@Named
@RequestScoped
public class OneToOneRelationshipDemoBean {

  @PersistenceContext
  private EntityManager entityManager;

  @Resource
  private UserTransaction userTransaction;

  public String updateDatabase() {
    String retVal = "confirmation";
    Customer customer;
    LoginInfo loginInfo = new LoginInfo();

    loginInfo.setLoginInfoId(1L);
    loginInfo.setLoginName("charlesj");
    loginInfo.setPassword("iwonttellyou");

    try {
      userTransaction.begin();

      customer = entityManager.find(Customer.class, 4L);
      loginInfo.setCustomer(customer);

      entityManager.persist(loginInfo);

      userTransaction.commit();

    } catch (NotSupportedException
            | SystemException
            | SecurityException
            | IllegalStateException
            | RollbackException
            | HeuristicMixedException
            | HeuristicRollbackException e) {
      retVal = "error";
      e.printStackTrace();
    }

    return retVal;
  }
}
