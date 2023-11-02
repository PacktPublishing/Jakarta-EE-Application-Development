package com.ensode.jakartaeebook;

import jakarta.ejb.Remote;
import jakarta.ejb.Timer;
import java.io.Serializable;

@Remote
public interface JebTimerExample {

  public void startTimer(Serializable info);

  public void stopTimer(Serializable info);

  public void logMessage(Timer timer);
}
