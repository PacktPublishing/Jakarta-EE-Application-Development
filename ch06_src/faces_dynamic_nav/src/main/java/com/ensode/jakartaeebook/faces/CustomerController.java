package com.ensode.jakartaeebook.faces;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class CustomerController {

  private static final Logger LOG = Logger.getLogger(CustomerController.class.getName());

  @Inject
  private Customer customer;

  @Inject
  private FacesContext facesContext;

  public String saveCustomer() throws IOException {
    String landingPage = "confirmation";

    String tmpDir = System.getProperty("java.io.tmpdir");
    Path customerFile;

    try {
      customerFile = Path.of(tmpDir, "customer-file.txt");

      if (!Files.exists(customerFile)) {
        customerFile = Files.createFile(Path.of(tmpDir, "customer-file.txt"));
      }

      //alternate between making the file read-only or writable
      //when the file is read only, we get an IO exception and navigate back to the input page
      //otherwise we navigate to the confirmation page
      customerFile.toFile().setWritable(!customerFile.toFile().canWrite());

      LOG.log(Level.INFO, String.format("Saving customer data to %s", customerFile.toString()));
      Files.writeString(customerFile, customer.toString(), StandardOpenOption.APPEND);
    } catch (IOException ex) {
      LOG.log(Level.SEVERE, "IO exception caught", ex);
      landingPage = "index";
      FacesMessage facesMessage = new FacesMessage("Error saving customer");
      facesContext.addMessage(null, facesMessage);

    }

    return landingPage;
  }
}
