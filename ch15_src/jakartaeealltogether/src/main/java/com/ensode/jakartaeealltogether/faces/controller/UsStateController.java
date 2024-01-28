package com.ensode.jakartaeealltogether.faces.controller;

import com.ensode.jakartaeealltogether.dao.UsStateDao;
import com.ensode.jakartaeealltogether.faces.util.JsfUtil;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UsStateController implements Serializable {

  @EJB
  private UsStateDao dao;
  
  public SelectItem[] getUsStateItemsAvailableSelectOne() {
    return JsfUtil.getSelectItems(dao.findUsStateEntities(), true);
  }

  public UsStateDao getDao() {
    return dao;
  }

}
