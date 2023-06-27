package com.ensode.jakartaeebook.cdievents.controller;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import com.ensode.jakartaeebook.cdievents.event.NavigationInfo;
import com.ensode.jakartaeebook.cdievents.model.Customer;
import jakarta.enterprise.event.Event;

@Named
@RequestScoped
public class CustomerInfoController implements Serializable {

    @Inject
    private Conversation conversation;
    @Inject
    private Customer customer;
    @Inject
    private Event<NavigationInfo> navigationInfoEvent;

    public String customerInfoEntry() {
        conversation.begin();
        NavigationInfo navigationInfo = new NavigationInfo();
        navigationInfo.setPage("1");
        navigationInfo.setCustomer(customer);

        navigationInfoEvent.fire(navigationInfo);
        return "page1";
    }

    public String navigateToPage1() {
        NavigationInfo navigationInfo = new NavigationInfo();
        navigationInfo.setPage("1");
        navigationInfo.setCustomer(customer);

        navigationInfoEvent.fire(navigationInfo);

        return "page1";
    }

    public String navigateToPage2() {
        NavigationInfo navigationInfo = new NavigationInfo();
        navigationInfo.setPage("2");
        navigationInfo.setCustomer(customer);

        navigationInfoEvent.fire(navigationInfo);
        return "page2";
    }

    public String navigateToPage3() {
        NavigationInfo navigationInfo = new NavigationInfo();
        navigationInfo.setPage("3");
        navigationInfo.setCustomer(customer);

        navigationInfoEvent.fire(navigationInfo);
        return "page3";
    }

    public String navigateToConfirmationPage() {
        NavigationInfo navigationInfo = new NavigationInfo();
        navigationInfo.setPage("confirmation");
        navigationInfo.setCustomer(customer);

        navigationInfoEvent.fire(navigationInfo);
        conversation.end();
        return "confirmation";
    }
}
