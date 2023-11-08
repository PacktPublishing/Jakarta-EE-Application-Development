package com.ensode.javaee8book.calendarbasedtimer;

import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class CalendarBasedTimerJebExampleBean {

  private static Logger logger = Logger.getLogger(
      CalendarBasedTimerJebExampleBean.class.getName());

  @Schedule(hour = "20", minute = "10")
  public void logMessage() {
    logger.log(Level.INFO, "This message was triggered at:{0}", System.currentTimeMillis());
  }
}
