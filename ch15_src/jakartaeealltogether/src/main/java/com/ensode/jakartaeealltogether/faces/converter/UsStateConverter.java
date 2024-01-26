package com.ensode.jakartaeealltogether.faces.converter;

import com.ensode.jakartaeealltogether.entity.UsState;
import com.ensode.jakartaeealltogether.faces.controller.UsStateController;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass = UsState.class)
public class UsStateConverter implements Converter {

  public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
    if (string == null || string.length() == 0) {
      return null;
    }
    Integer id = Integer.valueOf(string);
    UsStateController controller = (UsStateController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "usState");
    return controller.getJpaController().findUsState(id);
  }

  public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
    if (object == null) {
      return null;
    }
    if (object instanceof UsState) {
      UsState o = (UsState) object;
      return o.getUsStateId() == null ? "" : o.getUsStateId().toString();
    } else {
      throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: com.ensode.jakartaeealltogether.entity.UsState");
    }
  }

}
