package com.ensode.jakartaeealltogether.faces;

import com.ensode.jakartaeealltogether.controller.CustomerJpaController;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jakarta.faces.FacesException;
import com.ensode.jakartaeealltogether.faces.util.JsfUtil;
import com.ensode.jakartaeealltogether.controller.exceptions.NonexistentEntityException;
import com.ensode.jakartaeealltogether.entity.Address;
import com.ensode.jakartaeealltogether.entity.Customer;
import com.ensode.jakartaeealltogether.entity.Telephone;
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
import java.util.ArrayList;
import java.util.List;

@Named("customer")
@SessionScoped
public class CustomerController implements Serializable {

  public CustomerController() {
    pagingInfo = new PagingInfo();
    converter = new CustomerConverter();
  }
  private Customer customer = null;
  private List<Customer> customerItems = null;
  private CustomerJpaController jpaController = null;
  private CustomerConverter converter = null;
  private PagingInfo pagingInfo = null;
  @Resource
  private UserTransaction utx = null;
  @PersistenceUnit(unitName = "customerPersistenceUnit")
  private EntityManagerFactory emf = null;

  public PagingInfo getPagingInfo() {
    if (pagingInfo.getItemCount() == -1) {
      pagingInfo.setItemCount(getJpaController().getCustomerCount());
    }
    return pagingInfo;
  }

  public CustomerJpaController getJpaController() {
    if (jpaController == null) {
      jpaController = new CustomerJpaController(utx, emf);
    }
    return jpaController;
  }

  public SelectItem[] getCustomerItemsAvailableSelectMany() {
    return JsfUtil.getSelectItems(getJpaController().findCustomerEntities(), false);
  }

  public SelectItem[] getCustomerItemsAvailableSelectOne() {
    return JsfUtil.getSelectItems(getJpaController().findCustomerEntities(), true);
  }

  public Customer getCustomer() {
    if (customer == null) {
      customer = (Customer) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentCustomer", converter, null);
    }
    if (customer == null) {
      customer = new Customer();
    }
    return customer;
  }

  public String listSetup() {
    reset(true);
    return "/customer/List";
  }

  public String createSetup() {
    reset(false);
    customer = new Customer();
    List<Address> addressList = new ArrayList<>(1);
    addressList.add(new Address());
    List<Telephone> telephoneList = new ArrayList<>(1);
    telephoneList.add(new Telephone());
    customer.setAddressList(addressList);
    customer.setTelephoneList(telephoneList);
    return "/customer/New";
  }

  public String create() {
    try {
      getJpaController().create(customer);
      JsfUtil.addSuccessMessage("Customer was successfully created.");
    } catch (Exception e) {
      JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
      return null;
    }
    return listSetup();
  }

  public String detailSetup() {
    return scalarSetup("/customer/Detail");
  }

  public String editSetup() {
    return scalarSetup("/customer/Edit");
  }

  private String scalarSetup(String destination) {
    reset(false);
    customer = (Customer) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentCustomer", converter, null);
    if (customer == null) {
      String requestCustomerString = JsfUtil.getRequestParameter("jsfcrud.currentCustomer");
      JsfUtil.addErrorMessage("The customer with id " + requestCustomerString + " no longer exists.");
      return relatedOrListOutcome();
    }
    return destination;
  }

  public String edit() {
    String customerString = converter.getAsString(FacesContext.getCurrentInstance(), null, customer);
    String currentCustomerString = JsfUtil.getRequestParameter("jsfcrud.currentCustomer");
    if (customerString == null || customerString.length() == 0 || !customerString.equals(currentCustomerString)) {
      String outcome = editSetup();
      if ("customer_edit".equals(outcome)) {
        JsfUtil.addErrorMessage("Could not edit customer. Try again.");
      }
      return outcome;
    }
    try {
      getJpaController().edit(customer);
      JsfUtil.addSuccessMessage("Customer was successfully updated.");
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
    String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentCustomer");
    Integer id = new Integer(idAsString);
    try {
      getJpaController().destroy(id);
      JsfUtil.addSuccessMessage("Customer was successfully deleted.");
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

  public List<Customer> getCustomerItems() {
    if (customerItems == null) {
      getPagingInfo();
      customerItems = getJpaController().findCustomerEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
    }
    return customerItems;
  }

  public String next() {
    reset(false);
    getPagingInfo().nextPage();
    return "customer_list";
  }

  public String prev() {
    reset(false);
    getPagingInfo().previousPage();
    return "customer_list";
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
    customer = null;
    customerItems = null;
    pagingInfo.setItemCount(-1);
    if (resetFirstItem) {
      pagingInfo.setFirstItem(0);
    }
  }

  public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
    Customer newCustomer = new Customer();
    String newCustomerString = converter.getAsString(FacesContext.getCurrentInstance(), null, newCustomer);
    String customerString = converter.getAsString(FacesContext.getCurrentInstance(), null, customer);
    if (!newCustomerString.equals(customerString)) {
      createSetup();
    }
  }

  public Converter getConverter() {
    return converter;
  }

}
