package com.ensode.jakartaeebook.persistenceintro.namedbean;

import com.ensode.jakartaeebook.persistenceintro.entity.Customer;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.RollbackException;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;

@Named
@RequestScoped

public class JakartaPersistence {

  @PersistenceContext
  private EntityManager entityManager;

  @Resource
  private UserTransaction userTransaction;

  public String updateDatabase() {

    String retVal = "confirmation";

    Customer customer = new Customer();
    Customer customer2 = new Customer();
    Customer customer3;

    customer.setCustomerId(3L);
    customer.setFirstName("James");
    customer.setLastName("McKenzie");
    customer.setEmail("jamesm@example.com");

    customer2.setCustomerId(4L);
    customer2.setFirstName("Charles");
    customer2.setLastName("Jonson");
    customer2.setEmail("cjohnson@example.org");

    try {
      userTransaction.begin();
      entityManager.persist(customer);
      entityManager.persist(customer2);
      customer3 = entityManager.find(Customer.class, 4L);
      customer3.setLastName("Johnson");
      entityManager.persist(customer3);
      entityManager.remove(customer);

      userTransaction.commit();
    } catch (HeuristicMixedException
            | HeuristicRollbackException
            | IllegalStateException
            | NotSupportedException
            | RollbackException
            | SecurityException
            | SystemException e) {
      retVal = "error";
      e.printStackTrace();
    }

    return retVal;
  }
}
