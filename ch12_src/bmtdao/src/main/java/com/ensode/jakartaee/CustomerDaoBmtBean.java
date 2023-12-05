package com.ensode.jakartaee;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.UserTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CustomerDaoBmtBean implements CustomerDaoBmt {

  @Resource
  private UserTransaction userTransaction;

  @PersistenceContext
  private EntityManager entityManager;

  @Resource(name = "java:app/jdbc/customerdbDatasource")
  private DataSource dataSource;

  @Override
  public void saveMultipleNewCustomers(List<Customer> customerList)
      throws Exception {
    for (Customer customer : customerList) {
      userTransaction.begin();
      customer.setCustomerId(getNewCustomerId());
      entityManager.persist(customer);
      userTransaction.commit();
    }
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
