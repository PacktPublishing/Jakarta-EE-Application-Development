package com.ensode.jakartaeebook.calculatorserviceclient;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.xml.ws.WebServiceRef;
import com.ensode.jakartaeebook.xmlws.Calculator;
import com.ensode.jakartaeebook.xmlws.CalculatorService;

@Named
@RequestScoped
public class CalculatorClientController {

  @WebServiceRef(wsdlLocation = "http://localhost:8080/calculatorservice/CalculatorService?wsdl")
  private CalculatorService calculatorService;

  @Inject
  private CalculatorServiceClientModel calculatorServiceClientModel;

  private Integer sum;
  private Integer difference;

  public void add(ActionEvent actionEvent) {
    Calculator calculator = calculatorService.getCalculatorPort();

    sum = calculator.add(calculatorServiceClientModel.getAddend1(), calculatorServiceClientModel.getAddend2());
  }

  public void subtract(ActionEvent actionEvent) {
    Calculator calculator = calculatorService.getCalculatorPort();

    difference = calculator.subtract(calculatorServiceClientModel.getMinuend(), calculatorServiceClientModel.getSubtrahend());
  }

  public Integer getSum() {
    return sum;
  }

  public void setSum(Integer sum) {
    this.sum = sum;
  }

  public Integer getDifference() {
    return difference;
  }

  public void setDifference(Integer difference) {
    this.difference = difference;
  }

}
