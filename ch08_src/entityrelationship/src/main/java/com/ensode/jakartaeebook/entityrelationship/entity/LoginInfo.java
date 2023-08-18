package com.ensode.jakartaeebook.entityrelationship.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "LOGIN_INFO")
public class LoginInfo {

  @Id
  @Column(name = "LOGIN_INFO_ID")
  private Long loginInfoId;

  @Column(name = "LOGIN_NAME")
  private String loginName;

  private String password;

  @OneToOne
  @JoinColumn(name = "CUSTOMER_ID")
  private Customer customer;

  public Long getLoginInfoId() {
    return loginInfoId;
  }

  public void setLoginInfoId(Long loginInfoId) {
    this.loginInfoId = loginInfoId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String userName) {
    this.loginName = userName;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

}
