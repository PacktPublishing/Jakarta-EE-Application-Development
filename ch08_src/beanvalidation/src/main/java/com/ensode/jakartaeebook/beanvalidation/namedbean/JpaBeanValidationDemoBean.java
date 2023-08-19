package com.ensode.jakartaeebook.beanvalidation.namedbean;

import com.ensode.jakartaeebook.beanvalidation.entity.Customer;
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
import jakarta.validation.ConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class JpaBeanValidationDemoBean {

  private static final Logger LOG = Logger.getLogger(JpaBeanValidationDemoBean.class.getName());

  @PersistenceContext
  private EntityManager entityManager;

  @Resource
  private UserTransaction userTransaction;

  public String beanValidationDemo() {
    String retVal = "confirmation";

    Customer customer = new Customer();
    Customer customer2 = new Customer();
    Customer customer3;

    customer.setCustomerId(10L);
    customer.setFirstName(null);
    customer.setLastName("McKenzie");
    customer.setEmail("jamesm@notreal.com");

    customer2.setCustomerId(11L);
    customer2.setFirstName("Charles");
    customer2.setLastName("Johnson");
    customer2.setEmail("cjohnson@phony.org");

    try {
      userTransaction.begin();
      entityManager.persist(customer);
      entityManager.persist(customer2);

      customer3 = entityManager.find(Customer.class, 4L);
      customer3.setLastName("Thispersonhasareallylonglastname");
      entityManager.persist(customer3);

      entityManager.remove(customer);

      userTransaction.commit();
    } catch (NotSupportedException
            | SystemException
            | SecurityException
            | IllegalStateException
            | RollbackException
            | HeuristicMixedException
            | HeuristicRollbackException
            | ConstraintViolationException e) {
      retVal = "error";
      e.printStackTrace();

      if (e instanceof ConstraintViolationException) {
        ConstraintViolationException cve = (ConstraintViolationException) e;
        LOG.log(Level.SEVERE, "-- begin constraint violations");
        cve.getConstraintViolations().forEach(cv -> LOG.log(Level.SEVERE,
                String.format("class: %s, property: %s, message: %s",
                        cv.getRootBeanClass().getCanonicalName(),
                        cv.getPropertyPath(), cv.getMessage())));
        LOG.log(Level.SEVERE, "-- end constraint violations");
      }

    }

    return retVal;
  }

}
