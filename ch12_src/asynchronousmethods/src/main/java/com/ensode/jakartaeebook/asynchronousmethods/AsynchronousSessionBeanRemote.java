package com.ensode.jakartaeebook.asynchronousmethods;

import jakarta.ejb.Remote;
import java.util.concurrent.Future;

@Remote
public interface AsynchronousSessionBeanRemote {

  void slowMethod() throws InterruptedException;

  Future<Long> slowMethodWithReturnValue() throws InterruptedException;

}
