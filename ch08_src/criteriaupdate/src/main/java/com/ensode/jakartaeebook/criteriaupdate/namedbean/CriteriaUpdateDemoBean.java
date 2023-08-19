package com.ensode.jakartaeebook.criteriaupdate.namedbean;

import com.ensode.jakartaeebook.criteriaupdate.entity.Address;
import com.ensode.jakartaeebook.criteriaupdate.entity.AddressType;
import com.ensode.jakartaeebook.criteriaupdate.entity.Customer;
import com.ensode.jakartaeebook.criteriaupdate.entity.UsState;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.RollbackException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import jakarta.ws.rs.NotSupportedException;

@Named
@RequestScoped
public class CriteriaUpdateDemoBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    private int updatedRows;

    public String updateData() {
        String retVal = "confirmation";

        try {

            userTransaction.begin();
            insertTempData();

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaUpdate<Address> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Address.class);
            Root<Address> root = criteriaUpdate.from(Address.class);
            criteriaUpdate.set("city", "New York");
            criteriaUpdate.where(criteriaBuilder.equal(root.get("city"), "New Yorc"));

            Query query = entityManager.createQuery(criteriaUpdate);

            updatedRows = query.executeUpdate();
            userTransaction.commit();
        } catch (Exception e) {
            retVal = "error";
            e.printStackTrace();
        }
        return retVal;
    }

    public int getUpdatedRows() {
        return updatedRows;
    }

    public void setUpdatedRows(int updatedRows) {
        this.updatedRows = updatedRows;
    }

    private void insertTempData() throws NotSupportedException,
            SystemException, RollbackException, HeuristicMixedException,
            HeuristicRollbackException {
        Customer customer = entityManager.find(Customer.class, 4);

        AddressType homeAddressType = entityManager.find(AddressType.class, 1);
        AddressType mailingAddressType = entityManager.find(AddressType.class, 2);
        AddressType shippingAddressType = entityManager.find(AddressType.class, 3);

        UsState nyUsState = entityManager.find(UsState.class, 31);

        Address homeAddress = new Address(1, "Line 1 Home", "Line 2 Home", "New Yorc", "10453", nyUsState, customer, homeAddressType);
        Address mailingAddress = new Address(2, "Line 1 Mailing", "Line 2 Mailing", "New Yorc", "10453", nyUsState, customer, mailingAddressType);
        Address shippingAddress = new Address(3, "Line 1 Shipping", "Line 2 Shipping", "New Yorc", "10453", nyUsState, customer, shippingAddressType);

        entityManager.persist(homeAddress);
        entityManager.persist(mailingAddress);
        entityManager.persist(shippingAddress);

    }
}
