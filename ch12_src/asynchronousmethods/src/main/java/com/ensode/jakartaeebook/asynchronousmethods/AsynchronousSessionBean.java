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
  public void slowMethod() throws InterruptedException {
    long startTime = System.currentTimeMillis();
    logger.log(Level.INFO, "entering slowMethod()");
    Thread.sleep(10000); //simulate processing for 10 seconds
    logger.log(Level.INFO, "leaving slowMethod()");
    long endTime = System.currentTimeMillis();
    logger.log(Level.INFO, "execution took {0} milliseconds", endTime - startTime);
  }

  @Asynchronous
  @Override
  public Future<Long> slowMethodWithReturnValue() throws InterruptedException {
    Thread.sleep(15000); //simulate processing for 15 seconds
    return new AsyncResult<>(42L);
  }
}
