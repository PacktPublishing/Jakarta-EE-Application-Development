package com.ensode.jakartaeealltogether.faces.controller;

import com.ensode.jakartaeealltogether.dao.TelephoneTypeDao;
import com.ensode.jakartaeealltogether.faces.util.JsfUtil;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("telephoneType")
@SessionScoped
public class TelephoneTypeController implements Serializable {

  @EJB
  private TelephoneTypeDao dao;

  public TelephoneTypeDao getDao() {
    return dao;
  }

  public SelectItem[] getTelephoneTypeItemsAvailableSelectOne() {
    return JsfUtil.getSelectItems(getDao().findTelephoneTypeEntities(), true);
  }

}
