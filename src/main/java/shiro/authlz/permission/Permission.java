package main.java.shiro.authlz.permission;

public interface Permission {

    boolean implies(Permission p);

}
