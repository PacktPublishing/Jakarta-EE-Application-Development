package com.ensode.jakartaeebook.cdievents.eventlistener;

import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.event.Observes;
import java.io.Serializable;
import com.ensode.jakartaeebook.cdievents.event.NavigationInfo;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
public class NavigationEventListener implements Serializable {

  private static final Logger LOG = Logger.getLogger(NavigationEventListener.class.getName());

    public void handleNavigationEvent(@Observes NavigationInfo navigationInfo) {
        LOG.info("Navigation event fired");
        LOG.log(Level.INFO, "Page: {0}", navigationInfo.getPage());
        LOG.log(Level.INFO, "Customer: {0}", navigationInfo.getCustomer());
    }
}
