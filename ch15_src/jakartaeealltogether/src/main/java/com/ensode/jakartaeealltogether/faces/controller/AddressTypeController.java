package com.ensode.jakartaeealltogether.faces.controller;

import com.ensode.jakartaeealltogether.dao.AddressTypeDao;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jakarta.faces.FacesException;
import com.ensode.jakartaeealltogether.faces.util.JsfUtil;
import com.ensode.jakartaeealltogether.dao.exceptions.NonexistentEntityException;
import com.ensode.jakartaeealltogether.entity.AddressType;
import com.ensode.jakartaeealltogether.faces.converter.AddressTypeConverter;
import com.ensode.jakartaeealltogether.faces.util.PagingInfo;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.UserTransaction;
import java.io.Serializable;
import java.util.List;

@Named("addressType")
@SessionScoped
public class AddressTypeController implements Serializable {

  public AddressTypeController() {
    pagingInfo = new PagingInfo();
    converter = new AddressTypeConverter();
  }
  private AddressType addressType = null;
  private List<AddressType> addressTypeItems = null;
  @EJB
  private AddressTypeDao jpaController;
  private AddressTypeConverter converter = null;
  private PagingInfo pagingInfo = null;
  @Resource
  private UserTransaction utx = null;
  @PersistenceUnit(unitName = "customerPersistenceUnit")
  private EntityManagerFactory emf = null;

  public PagingInfo getPagingInfo() {
    if (pagingInfo.getItemCount() == -1) {
      pagingInfo.setItemCount(getJpaController().getAddressTypeCount());
    }
    return pagingInfo;
  }

  public AddressTypeDao getJpaController() {
    return jpaController;
  }

  public SelectItem[] getAddressTypeItemsAvailableSelectMany() {
    return JsfUtil.getSelectItems(getJpaController().findAddressTypeEntities(), false);
  }

  public SelectItem[] getAddressTypeItemsAvailableSelectOne() {
    return JsfUtil.getSelectItems(getJpaController().findAddressTypeEntities(), true);
  }

  public AddressType getAddressType() {
    if (addressType == null) {
      addressType = (AddressType) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentAddressType", converter, null);
    }
    if (addressType == null) {
      addressType = new AddressType();
    }
    return addressType;
  }

  public String listSetup() {
    reset(true);
    return "addressType/List";
  }

  public String createSetup() {
    reset(false);
    addressType = new AddressType();
    return "addressType_create";
  }

  public String create() {
    try {
      getJpaController().create(addressType);
      JsfUtil.addSuccessMessage("AddressType was successfully created.");
    } catch (Exception e) {
      JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
      return null;
    }
    return listSetup();
  }

  public String detailSetup() {
    return scalarSetup("addressType_detail");
  }

  public String editSetup() {
    return scalarSetup("addressType_edit");
  }

  private String scalarSetup(String destination) {
    reset(false);
    addressType = (AddressType) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentAddressType", converter, null);
    if (addressType == null) {
      String requestAddressTypeString = JsfUtil.getRequestParameter("jsfcrud.currentAddressType");
      JsfUtil.addErrorMessage("The addressType with id " + requestAddressTypeString + " no longer exists.");
      return relatedOrListOutcome();
    }
    return destination;
  }

  public String edit() {
    String addressTypeString = converter.getAsString(FacesContext.getCurrentInstance(), null, addressType);
    String currentAddressTypeString = JsfUtil.getRequestParameter("jsfcrud.currentAddressType");
    if (addressTypeString == null || addressTypeString.length() == 0 || !addressTypeString.equals(currentAddressTypeString)) {
      String outcome = editSetup();
      if ("addressType_edit".equals(outcome)) {
        JsfUtil.addErrorMessage("Could not edit addressType. Try again.");
      }
      return outcome;
    }
    try {
      getJpaController().edit(addressType);
      JsfUtil.addSuccessMessage("AddressType was successfully updated.");
    } catch (NonexistentEntityException ne) {
      JsfUtil.addErrorMessage(ne.getLocalizedMessage());
      return listSetup();
    } catch (Exception e) {
      JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
      return null;
    }
    return detailSetup();
  }

  public String destroy() {
    String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentAddressType");
    Integer id = Integer.valueOf(idAsString);
    try {
      getJpaController().destroy(id);
      JsfUtil.addSuccessMessage("AddressType was successfully deleted.");
    } catch (NonexistentEntityException ne) {
      JsfUtil.addErrorMessage(ne.getLocalizedMessage());
      return relatedOrListOutcome();
    } catch (Exception e) {
      JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
      return null;
    }
    return relatedOrListOutcome();
  }

  private String relatedOrListOutcome() {
    String relatedControllerOutcome = relatedControllerOutcome();
    if (relatedControllerOutcome != null) {
      return relatedControllerOutcome;
    }
    return listSetup();
  }

  public List<AddressType> getAddressTypeItems() {
    if (addressTypeItems == null) {
      getPagingInfo();
      addressTypeItems = getJpaController().findAddressTypeEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
    }
    return addressTypeItems;
  }

  public String next() {
    reset(false);
    getPagingInfo().nextPage();
    return "addressType_list";
  }

  public String prev() {
    reset(false);
    getPagingInfo().previousPage();
    return "addressType_list";
  }

  private String relatedControllerOutcome() {
    String relatedControllerString = JsfUtil.getRequestParameter("jsfcrud.relatedController");
    String relatedControllerTypeString = JsfUtil.getRequestParameter("jsfcrud.relatedControllerType");
    if (relatedControllerString != null && relatedControllerTypeString != null) {
      FacesContext context = FacesContext.getCurrentInstance();
      Object relatedController = context.getApplication().getELResolver().getValue(context.getELContext(), null, relatedControllerString);
      try {
        Class<?> relatedControllerType = Class.forName(relatedControllerTypeString);
        Method detailSetupMethod = relatedControllerType.getMethod("detailSetup");
        return (String) detailSetupMethod.invoke(relatedController);
      } catch (ClassNotFoundException e) {
        throw new FacesException(e);
      } catch (NoSuchMethodException e) {
        throw new FacesException(e);
      } catch (IllegalAccessException e) {
        throw new FacesException(e);
      } catch (InvocationTargetException e) {
        throw new FacesException(e);
      }
    }
    return null;
  }

  private void reset(boolean resetFirstItem) {
    addressType = null;
    addressTypeItems = null;
    pagingInfo.setItemCount(-1);
    if (resetFirstItem) {
      pagingInfo.setFirstItem(0);
    }
  }

  public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
    AddressType newAddressType = new AddressType();
    String newAddressTypeString = converter.getAsString(FacesContext.getCurrentInstance(), null, newAddressType);
    String addressTypeString = converter.getAsString(FacesContext.getCurrentInstance(), null, addressType);
    if (!newAddressTypeString.equals(addressTypeString)) {
      createSetup();
    }
  }

  public Converter getConverter() {
    return converter;
  }

}
