package com.ensode.jakartaeealltogether.faces.controller;

import com.ensode.jakartaeealltogether.dao.TelephoneDao;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jakarta.faces.FacesException;
import com.ensode.jakartaeealltogether.faces.util.JsfUtil;
import com.ensode.jakartaeealltogether.dao.exceptions.NonexistentEntityException;
import com.ensode.jakartaeealltogether.entity.Telephone;
import com.ensode.jakartaeealltogether.faces.converter.TelephoneConverter;
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

@Named("telephone")
@SessionScoped
public class TelephoneController implements Serializable {

  public TelephoneController() {
    pagingInfo = new PagingInfo();
    converter = new TelephoneConverter();
  }
  private Telephone telephone = null;
  private List<Telephone> telephoneItems = null;
  @EJB
  private TelephoneDao jpaController;
  private TelephoneConverter converter = null;
  private PagingInfo pagingInfo = null;
  @Resource
  private UserTransaction utx = null;
  @PersistenceUnit(unitName = "customerPersistenceUnit")
  private EntityManagerFactory emf = null;

  public PagingInfo getPagingInfo() {
    if (pagingInfo.getItemCount() == -1) {
      pagingInfo.setItemCount(getJpaController().getTelephoneCount());
    }
    return pagingInfo;
  }

  public TelephoneDao getJpaController() {
    return jpaController;
  }

  public SelectItem[] getTelephoneItemsAvailableSelectMany() {
    return JsfUtil.getSelectItems(getJpaController().findTelephoneEntities(), false);
  }

  public SelectItem[] getTelephoneItemsAvailableSelectOne() {
    return JsfUtil.getSelectItems(getJpaController().findTelephoneEntities(), true);
  }

  public Telephone getTelephone() {
    if (telephone == null) {
      telephone = (Telephone) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentTelephone", converter, null);
    }
    if (telephone == null) {
      telephone = new Telephone();
    }
    return telephone;
  }

  public String listSetup() {
    reset(true);
    return "telephone_list";
  }

  public String createSetup() {
    reset(false);
    telephone = new Telephone();
    return "telephone_create";
  }

  public String create() {
    try {
      getJpaController().create(telephone);
      JsfUtil.addSuccessMessage("Telephone was successfully created.");
    } catch (Exception e) {
      JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
      return null;
    }
    return listSetup();
  }

  public String detailSetup() {
    return scalarSetup("telephone_detail");
  }

  public String editSetup() {
    return scalarSetup("telephone_edit");
  }

  private String scalarSetup(String destination) {
    reset(false);
    telephone = (Telephone) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentTelephone", converter, null);
    if (telephone == null) {
      String requestTelephoneString = JsfUtil.getRequestParameter("jsfcrud.currentTelephone");
      JsfUtil.addErrorMessage("The telephone with id " + requestTelephoneString + " no longer exists.");
      return relatedOrListOutcome();
    }
    return destination;
  }

  public String edit() {
    String telephoneString = converter.getAsString(FacesContext.getCurrentInstance(), null, telephone);
    String currentTelephoneString = JsfUtil.getRequestParameter("jsfcrud.currentTelephone");
    if (telephoneString == null || telephoneString.length() == 0 || !telephoneString.equals(currentTelephoneString)) {
      String outcome = editSetup();
      if ("telephone_edit".equals(outcome)) {
        JsfUtil.addErrorMessage("Could not edit telephone. Try again.");
      }
      return outcome;
    }
    try {
      getJpaController().edit(telephone);
      JsfUtil.addSuccessMessage("Telephone was successfully updated.");
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
    String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentTelephone");
    Integer id = Integer.valueOf(idAsString);
    try {
      getJpaController().destroy(id);
      JsfUtil.addSuccessMessage("Telephone was successfully deleted.");
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

  public List<Telephone> getTelephoneItems() {
    if (telephoneItems == null) {
      getPagingInfo();
      telephoneItems = getJpaController().findTelephoneEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
    }
    return telephoneItems;
  }

  public String next() {
    reset(false);
    getPagingInfo().nextPage();
    return "telephone_list";
  }

  public String prev() {
    reset(false);
    getPagingInfo().previousPage();
    return "telephone_list";
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
    telephone = null;
    telephoneItems = null;
    pagingInfo.setItemCount(-1);
    if (resetFirstItem) {
      pagingInfo.setFirstItem(0);
    }
  }

  public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
    Telephone newTelephone = new Telephone();
    String newTelephoneString = converter.getAsString(FacesContext.getCurrentInstance(), null, newTelephone);
    String telephoneString = converter.getAsString(FacesContext.getCurrentInstance(), null, telephone);
    if (!newTelephoneString.equals(telephoneString)) {
      createSetup();
    }
  }

  public Converter getConverter() {
    return converter;
  }

}
