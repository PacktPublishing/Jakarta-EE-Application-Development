package com.ensode.jakartaeebook.security.basicauthexample;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class DummyIdentityStore implements IdentityStore {

  Set<String> adminRoleSet;
  Set userRoleSet;
  Set userAdminRoleSet;

  @PostConstruct
  public void init() {
    adminRoleSet = new HashSet<>(Arrays.asList("admin"));
    userRoleSet = new HashSet<>(Arrays.asList("user"));
    userAdminRoleSet = new HashSet<>(Arrays.asList("user", "admin"));
  }

  @Override
  public CredentialValidationResult validate(Credential credential) {
    UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;

    CredentialValidationResult credentialValidationResult;

    if (usernamePasswordCredential.compareTo(
            "david", "secret")) {
      credentialValidationResult = new CredentialValidationResult("david", adminRoleSet);
    }
    else if (usernamePasswordCredential.compareTo("alan", "iforgot")) {
      credentialValidationResult = new CredentialValidationResult("alan", userAdminRoleSet);
    }
    else {
      credentialValidationResult = CredentialValidationResult.INVALID_RESULT;
    }

    return credentialValidationResult;
  }

}
