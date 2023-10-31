package com.ensode.jakartaeebook.init;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Startup
@Singleton
@DataSourceDefinition(name = "java:app/jdbc/customerdbDatasource",
        className = "org.h2.jdbcx.JdbcDataSource",
        url = "jdbc:h2:tcp://127.0.1.1:9092/mem:customerdb",
        user = "sa",
        password = "")
public class DbInitializer {


}
