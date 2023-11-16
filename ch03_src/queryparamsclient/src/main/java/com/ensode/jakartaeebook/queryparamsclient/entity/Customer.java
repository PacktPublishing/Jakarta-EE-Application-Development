package com.ensode.jakartaeebook.queryparamsclient.entity;

public class Customer {

  private Long id;
  private String firstName;
  private String middleName;
  private String lastName;

  public Customer() {
  }

  public Customer(Long id, String firstName,
          String middleInitial, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.middleName = middleInitial;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Customer{");
    sb.append("id=").append(id);
    sb.append(", firstName=").append(firstName);
    sb.append(", middleName=").append(middleName);
    sb.append(", lastName=").append(lastName);
    sb.append('}');
    return sb.toString();
  }

}
