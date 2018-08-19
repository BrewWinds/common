package main.java.shiro.session.mgt;


import main.java.shiro.session.Session;
import main.java.shiro.session.SessionException;

public interface SessionManager {

    Session start(SessionContext context);

    Session getSession(SessionKey key) throws SessionException;

}
