package com.ensode.jakartaeebook.microservices.crudpersistence.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.ensode.jakartaeebook.microservices.crudpersistence.Customer;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.transaction.Transactional;

@ApplicationScoped
@DataSourceDefinition(name = "java:app/jdbc/microservicesCrudDatasource",
        className = "org.h2.jdbcx.JdbcDataSource",
        url = "jdbc:h2:tcp://127.0.1.1:9092/mem:microservicescrud",
        user = "sa",
        password = "")
public class CrudDao {

  @PersistenceContext(unitName = "CustomerPersistenceUnit")
  private EntityManager em;

  @Transactional
  public void create(Customer customer) {
    em.persist(customer);
  }
}
