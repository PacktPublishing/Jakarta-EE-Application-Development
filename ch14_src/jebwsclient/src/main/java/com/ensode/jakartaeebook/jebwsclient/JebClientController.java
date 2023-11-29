package com.ensode.jakartaeebook.jebwsclient;

import com.ensode.jakartaeebook.jebws.DecToHexBeanService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.xml.ws.WebServiceRef;

@Named
@RequestScoped
public class JebClientController {

    @WebServiceRef(wsdlLocation = "http://localhost:8080/DecToHexBeanService/DecToHexBean?wsdl")
    private DecToHexBeanService decToHexBeanService;

    @Inject
    private JebClientModel jebClientModel;

    private String hexVal;

    public void convertIntToHex() {
        hexVal = decToHexBeanService.getDecToHexBeanPort().
                convertDecToHex(jebClientModel.getIntVal());
    }

    public String getHexVal() {
        return hexVal;
    }

    public void setHexVal(String hexVal) {
        this.hexVal = hexVal;
    }

}
