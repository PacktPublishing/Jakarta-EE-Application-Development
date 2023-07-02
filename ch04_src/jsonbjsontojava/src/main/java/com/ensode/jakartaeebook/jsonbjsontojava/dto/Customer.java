package com.ensode.jakartaeebook.jsonbjsontojava.dto;

import java.time.LocalDate;

public class Customer {

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
