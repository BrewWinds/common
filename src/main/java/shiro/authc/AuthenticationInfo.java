package shiro.authc;

import shiro.subject.PrincipalCollection;

import java.io.Serializable;

public interface AuthenticationInfo extends Serializable {

    PrincipalCollection getPrincipals();

    Object getCredentials();
}
