
package com.ensode.jakartaeealltogether.faces;

import com.ensode.jakartaeealltogether.controller.TelephoneTypeJpaController;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jakarta.faces.FacesException;
import com.ensode.jakartaeealltogether.faces.util.JsfUtil;
import com.ensode.jakartaeealltogether.controller.exceptions.NonexistentEntityException;
import com.ensode.jakartaeealltogether.entity.TelephoneType;
import com.ensode.jakartaeealltogether.faces.util.PagingInfo;
import jakarta.annotation.Resource;
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

@Named("telephoneType")
@SessionScoped
public class TelephoneTypeController implements Serializable{

  public TelephoneTypeController() {
    pagingInfo = new PagingInfo();
    converter = new TelephoneTypeConverter();
  }
  private TelephoneType telephoneType = null;
  private List<TelephoneType> telephoneTypeItems = null;
  private TelephoneTypeJpaController jpaController = null;
  private TelephoneTypeConverter converter = null;
  private PagingInfo pagingInfo = null;
  @Resource
  private UserTransaction utx = null;
  @PersistenceUnit(unitName = "customerPersistenceUnit")
  private EntityManagerFactory emf = null;

  public PagingInfo getPagingInfo() {
    if (pagingInfo.getItemCount() == -1) {
      pagingInfo.setItemCount(getJpaController().getTelephoneTypeCount());
    }
    return pagingInfo;
  }

  public TelephoneTypeJpaController getJpaController() {
    if (jpaController == null) {
      jpaController = new TelephoneTypeJpaController(utx, emf);
    }
    return jpaController;
  }

  public SelectItem[] getTelephoneTypeItemsAvailableSelectMany() {
    return JsfUtil.getSelectItems(getJpaController().findTelephoneTypeEntities(), false);
  }

  public SelectItem[] getTelephoneTypeItemsAvailableSelectOne() {
    return JsfUtil.getSelectItems(getJpaController().findTelephoneTypeEntities(), true);
  }

  public TelephoneType getTelephoneType() {
    if (telephoneType == null) {
      telephoneType = (TelephoneType) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentTelephoneType", converter, null);
    }
    if (telephoneType == null) {
      telephoneType = new TelephoneType();
    }
    return telephoneType;
  }

  public String listSetup() {
    reset(true);
    return "telephoneType_list";
  }

  public String createSetup() {
    reset(false);
    telephoneType = new TelephoneType();
    return "telephoneType_create";
  }

  public String create() {
    try {
      getJpaController().create(telephoneType);
      JsfUtil.addSuccessMessage("TelephoneType was successfully created.");
    } catch (Exception e) {
      JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
      return null;
    }
    return listSetup();
  }

  public String detailSetup() {
    return scalarSetup("telephoneType_detail");
  }

  public String editSetup() {
    return scalarSetup("telephoneType_edit");
  }

  private String scalarSetup(String destination) {
    reset(false);
    telephoneType = (TelephoneType) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentTelephoneType", converter, null);
    if (telephoneType == null) {
      String requestTelephoneTypeString = JsfUtil.getRequestParameter("jsfcrud.currentTelephoneType");
      JsfUtil.addErrorMessage("The telephoneType with id " + requestTelephoneTypeString + " no longer exists.");
      return relatedOrListOutcome();
    }
    return destination;
  }

  public String edit() {
    String telephoneTypeString = converter.getAsString(FacesContext.getCurrentInstance(), null, telephoneType);
    String currentTelephoneTypeString = JsfUtil.getRequestParameter("jsfcrud.currentTelephoneType");
    if (telephoneTypeString == null || telephoneTypeString.length() == 0 || !telephoneTypeString.equals(currentTelephoneTypeString)) {
      String outcome = editSetup();
      if ("telephoneType_edit".equals(outcome)) {
        JsfUtil.addErrorMessage("Could not edit telephoneType. Try again.");
      }
      return outcome;
    }
    try {
      getJpaController().edit(telephoneType);
      JsfUtil.addSuccessMessage("TelephoneType was successfully updated.");
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
    String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentTelephoneType");
    Integer id = new Integer(idAsString);
    try {
      getJpaController().destroy(id);
      JsfUtil.addSuccessMessage("TelephoneType was successfully deleted.");
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

  public List<TelephoneType> getTelephoneTypeItems() {
    if (telephoneTypeItems == null) {
      getPagingInfo();
      telephoneTypeItems = getJpaController().findTelephoneTypeEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
    }
    return telephoneTypeItems;
  }

  public String next() {
    reset(false);
    getPagingInfo().nextPage();
    return "telephoneType_list";
  }

  public String prev() {
    reset(false);
    getPagingInfo().previousPage();
    return "telephoneType_list";
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
    telephoneType = null;
    telephoneTypeItems = null;
    pagingInfo.setItemCount(-1);
    if (resetFirstItem) {
      pagingInfo.setFirstItem(0);
    }
  }

  public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
    TelephoneType newTelephoneType = new TelephoneType();
    String newTelephoneTypeString = converter.getAsString(FacesContext.getCurrentInstance(), null, newTelephoneType);
    String telephoneTypeString = converter.getAsString(FacesContext.getCurrentInstance(), null, telephoneType);
    if (!newTelephoneTypeString.equals(telephoneTypeString)) {
      createSetup();
    }
  }

  public Converter getConverter() {
    return converter;
  }

}
