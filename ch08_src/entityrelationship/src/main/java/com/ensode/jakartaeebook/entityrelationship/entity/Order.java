package com.ensode.jakartaeebook.entityrelationship.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "ORDERS")
public class Order {

  @Id
  @Column(name = "ORDER_ID")
  private Long orderId;

  @Column(name = "ORDER_NUMBER")
  private String orderNumber;

  @Column(name = "ORDER_DESCRIPTION")
  private String orderDescription;

  @ManyToOne
  @JoinColumn(name = "CUSTOMER_ID")
  private Customer customer;

  @ManyToMany
  @JoinTable(name = "ORDER_ITEMS",
          joinColumns = @JoinColumn(name = "ORDER_ID",
                  referencedColumnName = "ORDER_ID"),
          inverseJoinColumns = @JoinColumn(name = "ITEM_ID",
                  referencedColumnName = "ITEM_ID"))
  private Collection<Item> items;

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public String getOrderDescription() {
    return orderDescription;
  }

  public void setOrderDescription(String orderDescription) {
    this.orderDescription = orderDescription;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public String getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  public Collection<Item> getItems() {
    return items;
  }

  public void setItems(Collection<Item> items) {
    this.items = items;
  }
}
