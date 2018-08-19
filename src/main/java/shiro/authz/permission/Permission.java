package shiro.authz.permission;

public interface Permission {

    boolean implies(Permission p);

}
