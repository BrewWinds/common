package main.java.shiro.session.mgt.eis;

import main.java.shiro.session.Session;

import java.io.Serializable;

public interface SessionGenerator {

    Serializable generateId(Session session);

}
