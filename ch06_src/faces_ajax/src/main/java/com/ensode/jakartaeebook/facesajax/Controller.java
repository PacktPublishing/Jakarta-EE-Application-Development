package com.ensode.jakartaeebook.facesajax;

import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class Controller implements Serializable {

  private String text;
  private int firstOperand;
  private int secondOperand;
  private int total;

  public Controller() {
  }

  public void calculateTotal(ActionEvent actionEvent) {
    total = firstOperand + secondOperand;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getFirstOperand() {
    return firstOperand;
  }

  public void setFirstOperand(int firstOperand) {
    this.firstOperand = firstOperand;
  }

  public int getSecondOperand() {
    return secondOperand;
  }

  public void setSecondOperand(int secondOperand) {
    this.secondOperand = secondOperand;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }
}
