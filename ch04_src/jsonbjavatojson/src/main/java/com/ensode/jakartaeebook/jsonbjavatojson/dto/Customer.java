package com.ensode.jakartaeebook.jsonbjavatojson.dto;

import java.time.LocalDate;

public class Customer {

  public Customer() {
  }

  public Customer(String salutation, String firstName, String middleName, String lastName, LocalDate dateOfBirth) {
    this.salutation = salutation;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
  }

  private String salutation;
  private String firstName;
  private String middleName;
  private String lastName;
  private LocalDate dateOfBirth;

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

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Customer{");
    sb.append("salutation=").append(salutation);
    sb.append(", firstName=").append(firstName);
    sb.append(", middleName=").append(middleName);
    sb.append(", lastName=").append(lastName);
    sb.append(", dateOfBirth=").append(dateOfBirth);
    sb.append('}');
    return sb.toString();
  }

}
