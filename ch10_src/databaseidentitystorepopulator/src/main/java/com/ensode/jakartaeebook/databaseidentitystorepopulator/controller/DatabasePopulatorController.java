package com.ensode.jakartaeebook.databaseidentitystorepopulator.controller;

import com.ensode.jakartaeebook.databaseidentitystorepopulator.dao.RoleFacade;
import com.ensode.jakartaeebook.databaseidentitystorepopulator.dao.UserFacade;
import java.util.List;
import java.util.Set;
import com.ensode.jakartaeebook.databaseidentitystorepopulator.entity.Role;
import com.ensode.jakartaeebook.databaseidentitystorepopulator.entity.User;
import com.ensode.jakartaeebook.databaseidentitystorepopulator.model.LoginInfo;
import com.ensode.jakartaeebook.databaseidentitystorepopulator.util.PasswordHasher;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class DatabasePopulatorController {

  private static final Logger LOG = Logger.getLogger(DatabasePopulatorController.class.getName());

    @Inject
    private LoginInfo loginInfo;
    @Inject
    private UserFacade userFacade;
    @Inject
    private RoleFacade roleFacade;
    @Inject
    private PasswordHasher passwordHasher;

    private boolean userValid(User user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        if (constraintViolations.isEmpty()) {
            return true;
        } else {
            //userId property has a @NotNull validation, the value will be set by JPA before inserting into the database
            //ignore this constraint since it will be dealt with.
            if (constraintViolations.size() == 1) {
                ConstraintViolation<User> constraintViolation = constraintViolations.iterator().next();
                if (constraintViolation.getPropertyPath().toString().equals("userId")
                        && constraintViolation.getMessage().equals("must not be null")) {
                    return true;
                }
            }
            constraintViolations.forEach((constraintViolation) -> {
                System.out.println(constraintViolation.getPropertyPath().toString() + " : " + constraintViolation.getMessage());
            });
            return false;
        }

    }

    @Transactional
    private boolean saveData() {
        User user;
        List<Role> roleList = roleFacade.findByRoleNames(loginInfo.getRoleList());

        String hashedPassword = passwordHasher.hashPassword(loginInfo.getClearTextPassword());

        LOG.log(Level.INFO, String.format("hashed password: \"%s\"", hashedPassword));

        user = new User();
        user.setUsername(loginInfo.getUserName());
        user.setPassword(hashedPassword);
        user.setRoleCollection(roleList);

        if (userValid(user)) {
            userFacade.create(user);
            return true;
        } else {
            return false;
        }

    }

    public String populateUserInfo() {
        if (saveData()) {
            return "success";
        } else {
            return "error";
        }
    }
}
