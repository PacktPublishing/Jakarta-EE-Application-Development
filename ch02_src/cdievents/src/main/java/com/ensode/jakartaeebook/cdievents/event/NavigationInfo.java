package com.ensode.jakartaeebook.cdievents.event;

import java.io.Serializable;
import com.ensode.jakartaeebook.cdievents.model.Customer;

public class NavigationInfo implements Serializable {

    private String page;

    private Customer customer;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
