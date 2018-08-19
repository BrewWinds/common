package main.java.shiro.authlz.permission;

import java.io.Serializable;
import java.util.Set;

public class SimpleRole implements Serializable{

    protected String name = null;
    protected Set<Permission> permission;


}
