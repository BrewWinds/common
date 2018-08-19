package shiro.subject;

import shiro.authc.AuthenticationInfo;
import shiro.authc.AuthenticationToken;
import shiro.session.Session;

import java.io.Serializable;
import java.util.Map;

public interface SubjectContext extends Map<String, Object> {

    SecurityManager getSecurityMangaer();

    void setSecurityManager(SecurityManager securityManager);

    SecurityManager resolveSecurityManager();

    Serializable getSessionId();

    void setSessionId(Serializable sessionId);

    void setSubject(Subject subject);

    void setPrincipals(PrincipalCollection principals);

    Session getSession();

    void setSession();

    Session resolveSession();

    PrincipalCollection getPrincipals();

    PrincipalCollection resolvePrincipals();

    boolean isAuthenticated();

    void setAuthenticated(boolean authc);

    AuthenticationInfo getAuthenticationInfo();

    void setAuthenticationInfo();

    AuthenticationToken getAuthenticationToken();

    void setAuthenticationToken();

    String getHost();

    void setHost();

    String resolveHost();
}
