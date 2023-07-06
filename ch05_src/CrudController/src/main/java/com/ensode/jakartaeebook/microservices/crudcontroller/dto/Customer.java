package com.ensode.jakartaeebook.microservices.crudcontroller.dto;

public class Customer {

  private String salutation;
  private String firstName;
  private String middleName;
  private String lastName;

  public String getSalutation() {
    return salutation;
  }

  public void setSalutation(String salutation) {
    this.salutation = salutation;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Customer{");
    sb.append("salutation=").append(salutation);
    sb.append(", firstName=").append(firstName);
    sb.append(", middleName=").append(middleName);
    sb.append(", lastName=").append(lastName);
    sb.append('}');
    return sb.toString();
  }

}
