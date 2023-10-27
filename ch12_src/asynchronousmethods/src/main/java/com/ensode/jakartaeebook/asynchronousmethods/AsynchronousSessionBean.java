package com.ensode.jakartaeebook.asynchronousmethods;

import jakarta.ejb.AsyncResult;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class AsynchronousSessionBean implements
    AsynchronousSessionBeanRemote {

  private static Logger logger = Logger.getLogger(
      AsynchronousSessionBean.class.getName());

  @Asynchronous
  @Override
  public void slowMethod() {
    long startTime = System.currentTimeMillis();
    logger.log(Level.INFO, "entering {0}.slowMethod()", this.getClass().getCanonicalName());
    try {
      Thread.sleep(10000); //simulate processing for 10 seconds
    } catch (InterruptedException ex) {
      Logger.getLogger(AsynchronousSessionBean.class.getName()).
          log(Level.SEVERE, null, ex);
    }
    logger.log(Level.INFO, "leaving {0}.slowMethod()", this.getClass().getCanonicalName());
    long endTime = System.currentTimeMillis();
    logger.log(Level.INFO, "execution took {0} milliseconds", endTime - startTime);
  }

  @Asynchronous
  @Override
  public Future<Long> slowMethodWithReturnValue() {

    try {
      Thread.sleep(15000); //simulate processing for 15 seconds
    } catch (InterruptedException ex) {
      Logger.getLogger(AsynchronousSessionBean.class.getName()).
          log(Level.SEVERE, null, ex);
    }

    return new AsyncResult<>(42L);
  }
}
