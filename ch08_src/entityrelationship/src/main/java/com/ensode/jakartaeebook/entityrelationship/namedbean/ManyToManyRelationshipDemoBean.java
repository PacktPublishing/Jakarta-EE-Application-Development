package com.ensode.jakartaeebook.entityrelationship.namedbean;

import java.util.ArrayList;
import java.util.Collection;
import com.ensode.jakartaeebook.entityrelationship.entity.Item;
import com.ensode.jakartaeebook.entityrelationship.entity.Order;
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
public class ManyToManyRelationshipDemoBean {

  @PersistenceContext
  private EntityManager entityManager;

  @Resource
  private UserTransaction userTransaction;

  public String updateDatabase() {
    String retVal = "confirmation";

    Order order;
    Collection<Item> items = new ArrayList<Item>();
    Item item1 = new Item();
    Item item2 = new Item();

    item1.setItemId(1L);
    item1.setItemNumber("BCD1234");
    item1.setItemShortDesc("Notebook Computer");
    item1.setItemLongDesc("64 bit Quad core CPU, 4GB memory");

    item2.setItemId(2L);
    item2.setItemNumber("CDF2345");
    item2.setItemShortDesc("Cordless Mouse");
    item2.setItemLongDesc("Three button, infrared, "
            + "vertical and horizontal scrollwheels");

    items.add(item1);
    items.add(item2);

    try {
      userTransaction.begin();

      entityManager.persist(item1);
      entityManager.persist(item2);

      order = entityManager.find(Order.class, 1L);
      order.setItems(items);

      entityManager.persist(order);

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
