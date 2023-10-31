package com.ensode.jakartaeebook;

import jakarta.annotation.Resource;
import jakarta.ejb.EJBContext;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

@Stateless
public class CustomerDaoRollbackBean implements CustomerDaoRollback {

  @Resource
  private EJBContext ejbContext;

  @PersistenceContext
  private EntityManager entityManager;

  @Resource(name = "java:app/jdbc/customerdbDatasource")
  private DataSource dataSource;

  @Override
  public void saveNewCustomer(Customer customer) {
    if (customer == null || customer.getCustomerId() != null) {
      ejbContext.setRollbackOnly();
    } else {
      customer.setCustomerId(getNewCustomerId());
      entityManager.persist(customer);
    }
  }

  @Override
  public void updateCustomer(Customer customer) {
    if (customer == null || customer.getCustomerId() == null) {
      ejbContext.setRollbackOnly();
    } else {
      entityManager.merge(customer);
    }
  }

  @Override
  public Customer getCustomer(Long customerId) {
    Customer customer;

    customer = entityManager.find(Customer.class, customerId);

    return customer;
  }

  @Override
  public void deleteCustomer(Customer customer) {
    entityManager.remove(customer);
  }

  private Long getNewCustomerId() {
    Connection connection;
    Long newCustomerId = null;
    try {
      connection = dataSource.getConnection();
      PreparedStatement preparedStatement = connection
          .prepareStatement("select max(customer_id)+1 as new_customer_id from customers");

      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet != null && resultSet.next()) {
        newCustomerId = resultSet.getLong("new_customer_id");
      }
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return newCustomerId;
  }
}
