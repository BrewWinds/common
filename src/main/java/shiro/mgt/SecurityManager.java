package shiro.mgt;

import shiro.authc.AuthenticationException;
import shiro.authc.AuthenticationToken;
import shiro.authc.Authenticator;
import shiro.authz.permission.Authorizer;
import shiro.session.mgt.SessionManager;
import shiro.subject.Subject;
import shiro.subject.SubjectContext;

public interface SecurityManager extends Authenticator, Authorizer, SessionManager{

    Subject login(Subject subject, AuthenticationToken token) throws AuthenticationException;

    void logout(Subject subject);

    Subject createSubject(SubjectContext subjectContext);
;
}
