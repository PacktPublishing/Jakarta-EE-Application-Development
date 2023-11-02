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
    Timer timer = timerService.createTimer(new Date(), 5000, info);
  }

  @Override
  public void stopTimer(Serializable info) {
    Timer timer;
    Collection timers = timerService.getTimers();

    for (Object object : timers) {
      timer = ((Timer) object);

      if (timer.getInfo().equals(info)) {
        timer.cancel();
        break;
      }
    }
  }

  @Timeout
  @Override
  public void logMessage(Timer timer) {
    LOG.log(Level.INFO, "This message was triggered by :{0} at {1}",
        new Object[]{timer.getInfo(), System.currentTimeMillis()});
  }
}
