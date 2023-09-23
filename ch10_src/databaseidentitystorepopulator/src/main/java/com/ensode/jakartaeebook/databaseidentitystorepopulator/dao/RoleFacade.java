package com.ensode.jakartaeebook.databaseidentitystorepopulator.dao;

import java.util.List;
import com.ensode.jakartaeebook.databaseidentitystorepopulator.entity.Role;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class RoleFacade extends AbstractFacade<Role> {

    @PersistenceContext(unitName = "authPersistenceUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoleFacade() {
        super(Role.class);
    }

    public List<Role> findByRoleNames(List<String> roleStrList) {
        List<Role> roleList;
        Query query = em.createNamedQuery("Role.findByGroupNames", Role.class);

        query.setParameter("groupNames", roleStrList);
        roleList = query.getResultList();

        return roleList;
    }

}
