package com.ensode.jakartaeebook.databaseidentitystorepopulator.init;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;

@ApplicationScoped

@DataSourceDefinition(name = "java:app/jdbc/userauthdbDatasource",
        className = "org.h2.jdbcx.JdbcDataSource",
        url = "jdbc:h2:tcp://127.0.1.1:9092/mem:userauthdb",
        user = "sa",
        password = "")
public class DbInitializer {

  private void init(@Observes @Initialized(ApplicationScoped.class) Object object) {
    //This method will be invoked when the CDI application scope is initialized, during deployment
    //No logic necessary, class level @DataSourceDefinition will create a data source to be used by the application.
  }

}
