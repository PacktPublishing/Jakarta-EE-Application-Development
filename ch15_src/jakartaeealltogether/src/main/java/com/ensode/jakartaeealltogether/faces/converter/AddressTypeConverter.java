package com.ensode.jakartaeealltogether.faces.converter;

import com.ensode.jakartaeealltogether.entity.AddressType;
import com.ensode.jakartaeealltogether.faces.controller.AddressTypeController;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass = AddressType.class)
public class AddressTypeConverter implements Converter {

  @Override
  public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
    if (string == null || string.length() == 0) {
      return null;
    }
    Integer id = Integer.valueOf(string);
    AddressTypeController controller = (AddressTypeController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "addressType");
    return controller.getDao().findAddressType(id);
  }

  @Override
  public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
    if (object == null) {
      return null;
    }
    if (object instanceof AddressType) {
      AddressType o = (AddressType) object;
      return o.getAddressTypeId() == null ? "" : o.getAddressTypeId().toString();
    } else {
      throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: com.ensode.jakartaeealltogether.entity.AddressType");
    }
  }

}
