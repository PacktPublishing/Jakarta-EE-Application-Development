package com.ensode.jakartaeebook;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.ejb.Timeout;
import jakarta.ejb.Timer;
import jakarta.ejb.TimerService;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class JebTimerExampleBean implements JebTimerExample {

  private static final Logger LOG = Logger.getLogger(JebTimerExampleBean.class.getName());

  @Resource
  TimerService timerService;

  @Override
  public void startTimer(Serializable info) {
    timerService.createTimer(new Date(), 5000, info);
  }

  @Override
  public void stopTimer(Serializable info) {
    Collection<Timer> timers = timerService.getTimers();

    timers.stream().filter(
        t -> t.getInfo().equals(info)).
        forEach(t -> t.cancel());
  }

  @Timeout
  @Override
  public void logMessage(Timer timer) {
    LOG.log(Level.INFO, "This message was triggered by :{0} at {1}",
        new Object[]{timer.getInfo(), System.currentTimeMillis()});
  }
}
