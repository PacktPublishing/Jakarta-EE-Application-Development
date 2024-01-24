package com.ensode.jakartaeealltogether.faces;

import com.ensode.jakartaeealltogether.controller.UsStateJpaController;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jakarta.faces.FacesException;
import com.ensode.jakartaeealltogether.faces.util.JsfUtil;
import com.ensode.jakartaeealltogether.controller.exceptions.NonexistentEntityException;
import com.ensode.jakartaeealltogether.entity.UsState;
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

@Named("usState")
@SessionScoped
public class UsStateController implements Serializable {

  public UsStateController() {
    pagingInfo = new PagingInfo();
    converter = new UsStateConverter();
  }
  private UsState usState = null;
  private List<UsState> usStateItems = null;
  private UsStateJpaController jpaController = null;
  private UsStateConverter converter = null;
  private PagingInfo pagingInfo = null;
  @Resource
  private UserTransaction utx = null;
  @PersistenceUnit(unitName = "customerPersistenceUnit")
  private EntityManagerFactory emf = null;

  public PagingInfo getPagingInfo() {
    if (pagingInfo.getItemCount() == -1) {
      pagingInfo.setItemCount(getJpaController().getUsStateCount());
    }
    return pagingInfo;
  }

  public UsStateJpaController getJpaController() {
    if (jpaController == null) {
      jpaController = new UsStateJpaController(utx, emf);
    }
    return jpaController;
  }

  public SelectItem[] getUsStateItemsAvailableSelectMany() {
    return JsfUtil.getSelectItems(getJpaController().findUsStateEntities(), false);
  }

  public SelectItem[] getUsStateItemsAvailableSelectOne() {
    return JsfUtil.getSelectItems(getJpaController().findUsStateEntities(), true);
  }

  public UsState getUsState() {
    if (usState == null) {
      usState = (UsState) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentUsState", converter, null);
    }
    if (usState == null) {
      usState = new UsState();
    }
    return usState;
  }

  public String listSetup() {
    reset(true);
    return "usState_list";
  }

  public String createSetup() {
    reset(false);
    usState = new UsState();
    return "usState_create";
  }

  public String create() {
    try {
      getJpaController().create(usState);
      JsfUtil.addSuccessMessage("UsState was successfully created.");
    } catch (Exception e) {
      JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
      return null;
    }
    return listSetup();
  }

  public String detailSetup() {
    return scalarSetup("usState_detail");
  }

  public String editSetup() {
    return scalarSetup("usState_edit");
  }

  private String scalarSetup(String destination) {
    reset(false);
    usState = (UsState) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentUsState", converter, null);
    if (usState == null) {
      String requestUsStateString = JsfUtil.getRequestParameter("jsfcrud.currentUsState");
      JsfUtil.addErrorMessage("The usState with id " + requestUsStateString + " no longer exists.");
      return relatedOrListOutcome();
    }
    return destination;
  }

  public String edit() {
    String usStateString = converter.getAsString(FacesContext.getCurrentInstance(), null, usState);
    String currentUsStateString = JsfUtil.getRequestParameter("jsfcrud.currentUsState");
    if (usStateString == null || usStateString.length() == 0 || !usStateString.equals(currentUsStateString)) {
      String outcome = editSetup();
      if ("usState_edit".equals(outcome)) {
        JsfUtil.addErrorMessage("Could not edit usState. Try again.");
      }
      return outcome;
    }
    try {
      getJpaController().edit(usState);
      JsfUtil.addSuccessMessage("UsState was successfully updated.");
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
    String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentUsState");
    Integer id = new Integer(idAsString);
    try {
      getJpaController().destroy(id);
      JsfUtil.addSuccessMessage("UsState was successfully deleted.");
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

  public List<UsState> getUsStateItems() {
    if (usStateItems == null) {
      getPagingInfo();
      usStateItems = getJpaController().findUsStateEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
    }
    return usStateItems;
  }

  public String next() {
    reset(false);
    getPagingInfo().nextPage();
    return "usState_list";
  }

  public String prev() {
    reset(false);
    getPagingInfo().previousPage();
    return "usState_list";
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
    usState = null;
    usStateItems = null;
    pagingInfo.setItemCount(-1);
    if (resetFirstItem) {
      pagingInfo.setFirstItem(0);
    }
  }

  public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
    UsState newUsState = new UsState();
    String newUsStateString = converter.getAsString(FacesContext.getCurrentInstance(), null, newUsState);
    String usStateString = converter.getAsString(FacesContext.getCurrentInstance(), null, usState);
    if (!newUsStateString.equals(usStateString)) {
      createSetup();
    }
  }

  public Converter getConverter() {
    return converter;
  }

}
