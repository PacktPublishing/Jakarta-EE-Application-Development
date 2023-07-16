package com.ensode.jakartaeebook.facescustommess;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import org.apache.commons.lang3.StringUtils;

@FacesValidator(value = "emailValidator")
public class EmailValidator implements Validator {

  @Override
  public void validate(FacesContext facesContext,
          UIComponent uiComponent,
          Object value) throws ValidatorException {
    org.apache.commons.validator.routines.EmailValidator emailValidator
            = org.apache.commons.validator.routines.EmailValidator.getInstance();
    HtmlInputText htmlInputText = (HtmlInputText) uiComponent;

    String email = (String) value;

    if (!StringUtils.isEmpty(email)) {
      if (!emailValidator.isValid(email)) {
        FacesMessage facesMessage = new FacesMessage(htmlInputText.
                getLabel()
                + ": email format is not valid");
        throw new ValidatorException(facesMessage);
      }
    }
  }
}
