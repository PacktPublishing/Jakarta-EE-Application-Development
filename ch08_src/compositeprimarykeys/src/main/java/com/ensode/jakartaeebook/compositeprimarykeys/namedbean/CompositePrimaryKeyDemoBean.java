package com.ensode.jakartaeebook.compositeprimarykeys.namedbean;

import com.ensode.jakartaeebook.compositeprimarykeys.entity.OrderItem;
import com.ensode.jakartaeebook.compositeprimarykeys.entity.OrderItemPK;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Named
@RequestScoped
public class CompositePrimaryKeyDemoBean {

  @PersistenceContext
  private EntityManager entityManager;

  private OrderItem orderItem;

  public String findOrderItem() {
    String retVal = "confirmation";

    try {
      orderItem = entityManager.find(OrderItem.class, new OrderItemPK(1L, 2L));
    } catch (Exception e) {
      retVal = "error";
      e.printStackTrace();
    }

    return retVal;
  }

  public OrderItem getOrderItem() {
    return orderItem;
  }

  public void setOrderItem(OrderItem orderItem) {
    this.orderItem = orderItem;
  }

}
