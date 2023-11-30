package com.ensode.jakartaeebook.xmlws;

import jakarta.activation.DataHandler;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.io.FileOutputStream;
import java.io.IOException;

@WebService
public class FileAttachment {

  @WebMethod
  public void attachFile(DataHandler dataHandler) {
    FileOutputStream fileOutputStream;
    try {
      fileOutputStream = new FileOutputStream("/tmp/logo.png");

      dataHandler.writeTo(fileOutputStream);

      fileOutputStream.flush();
      fileOutputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
