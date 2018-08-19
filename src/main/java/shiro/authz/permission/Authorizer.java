package shiro.authz.permission;

import shiro.authz.AuthorizationException;
import shiro.subject.PrincipalCollection;

import java.util.Collection;
import java.util.List;

public interface Authorizer {

    boolean isPermitted(PrincipalCollection principals, String permission);

    boolean isPermitted(PrincipalCollection subjectPrincipal, Permission permission);

    boolean[] isPermitted(PrincipalCollection subjectPrincipal, String... permissionss);

    void checkPermission(PrincipalCollection subjectPrincipal, Collection<Permission> permission);

    boolean hasRoles(PrincipalCollection subjectPrincipal, List<String> roleIdentifiers);

    void checkRole(PrincipalCollection subjectPrincipal, Collection<String> roleIdentifiers) throws AuthorizationException;


}
