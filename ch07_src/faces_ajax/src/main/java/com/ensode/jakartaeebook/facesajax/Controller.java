package com.ensode.jakartaeebook.facesajax;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class Controller implements Serializable {

  private String text;
  private int firstOperand;
  private int secondOperand;
  private int total;

  private static final Logger LOG = Logger.getLogger(Controller.class.getName());

  @Inject
  private FacesContext facesContext;

  public Controller() {
  }

  @PostConstruct
  public void init() {

    String projectStage = facesContext.getApplication().getProjectStage().toString();
    LOG.log(Level.INFO, String.format("--- Project stage is: %s", projectStage));

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
