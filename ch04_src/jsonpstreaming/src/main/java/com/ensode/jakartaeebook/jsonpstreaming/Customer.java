package com.ensode.jakartaeebook.jsonpstreaming;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class Customer implements Serializable {

  private String firstName;
  private String lastName;
  private String email;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
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
    sb.append("firstName=").append(firstName);
    sb.append(", lastName=").append(lastName);
    sb.append(", email=").append(email);
    sb.append('}');
    return sb.toString();
  }

}
