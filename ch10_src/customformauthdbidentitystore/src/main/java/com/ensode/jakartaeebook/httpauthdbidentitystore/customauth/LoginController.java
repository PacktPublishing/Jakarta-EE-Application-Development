package com.ensode.jakartaeebook.httpauthdbidentitystore.customauth;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Named
@RequestScoped
public class LoginController {

    @Inject
    private SecurityContext securityContext;

    @Inject
    private User user;

    public void login() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) externalContext.getRequest();
        HttpServletResponse httpServletResponse = (HttpServletResponse) externalContext.getResponse();
        UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredential(user.getUserName(), user.getPassword());

        AuthenticationParameters authenticationParameters = AuthenticationParameters.withParams().credential(usernamePasswordCredential);

        AuthenticationStatus authenticationStatus = securityContext.authenticate(httpServletRequest, httpServletResponse, authenticationParameters);

        if (authenticationStatus.equals(AuthenticationStatus.SEND_CONTINUE)) {
            facesContext.responseComplete();
        } else if (authenticationStatus.equals(AuthenticationStatus.SEND_FAILURE)) {
            FacesMessage facesMessage = new FacesMessage("Login error");
            facesContext.addMessage(null, facesMessage);
        }

    }
}
