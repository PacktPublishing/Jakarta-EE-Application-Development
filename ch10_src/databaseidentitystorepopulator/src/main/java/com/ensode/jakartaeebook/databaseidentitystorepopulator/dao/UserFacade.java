package com.ensode.jakartaeebook.databaseidentitystorepopulator.dao;

import com.ensode.jakartaeebook.databaseidentitystorepopulator.entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "authPersistenceUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

}
