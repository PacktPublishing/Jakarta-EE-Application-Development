package com.ensode.jakartaeealltogether.faces.converter;

import com.ensode.jakartaeealltogether.entity.Address;
import com.ensode.jakartaeealltogether.faces.controller.AddressController;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass = Address.class)
public class AddressConverter implements Converter {

  @Override
  public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
    if (string == null || string.length() == 0) {
      return null;
    }
    Integer id = Integer.valueOf(string);
    AddressController controller = (AddressController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "address");
    return controller.getDao().findAddress(id);
  }

  @Override
  public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
    if (object == null) {
      return null;
    }
    if (object instanceof Address) {
      Address o = (Address) object;
      return o.getAddressId() == null ? "" : o.getAddressId().toString();
    } else {
      throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: com.ensode.jakartaeealltogether.entity.Address");
    }
  }

}
