package shiro.authz.permission;


import java.io.Serializable;
import java.util.Collection;

public interface AuthorizationInfo extends Serializable {

    Collection<String> getRoles();

    Collection<String> getStringPermission();

    Collection<Permission> getObjectPermission();


}
