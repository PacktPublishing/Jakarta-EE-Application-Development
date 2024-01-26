package com.ensode.jakartaeealltogether.faces.converter;

import com.ensode.jakartaeealltogether.entity.Telephone;
import com.ensode.jakartaeealltogether.faces.controller.TelephoneController;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass = Telephone.class)
public class TelephoneConverter implements Converter {

  @Override
  public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
    if (string == null || string.length() == 0) {
      return null;
    }
    Integer id = Integer.valueOf(string);
    TelephoneController controller = (TelephoneController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "telephone");
    return controller.getDao().findTelephone(id);
  }

  @Override
  public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
    if (object == null) {
      return null;
    }
    if (object instanceof Telephone) {
      Telephone o = (Telephone) object;
      return o.getTelephoneId() == null ? "" : o.getTelephoneId().toString();
    } else {
      throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: com.ensode.jakartaeealltogether.entity.Telephone");
    }
  }

}
