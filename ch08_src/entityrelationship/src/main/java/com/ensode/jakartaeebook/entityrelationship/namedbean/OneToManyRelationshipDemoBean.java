package com.ensode.jakartaeebook.entityrelationship.namedbean;

import com.ensode.jakartaeebook.entityrelationship.entity.Customer;
import com.ensode.jakartaeebook.entityrelationship.entity.Order;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;

@Named
@RequestScoped
public class OneToManyRelationshipDemoBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    public String updateDatabase() {
        String retVal = "confirmation";

        Customer customer;
        Order order1;
        Order order2;

        order1 = new Order();
        order1.setOrderId(1L);
        order1.setOrderNumber("SFX12345");
        order1.setOrderDescription("Dummy order.");

        order2 = new Order();
        order2.setOrderId(2L);
        order2.setOrderNumber("SFX23456");
        order2.setOrderDescription("Another dummy order.");

        try {
            userTransaction.begin();

            customer = entityManager.find(Customer.class, 4L);

            order1.setCustomer(customer);
            order2.setCustomer(customer);

            entityManager.persist(order1);
            entityManager.persist(order2);

            userTransaction.commit();

        } catch (NotSupportedException |
                SystemException |
                SecurityException |
                IllegalStateException |
                RollbackException |
                HeuristicMixedException |
                HeuristicRollbackException e) {
            retVal = "error";
            e.printStackTrace();
        }

        return retVal;
    }
}
