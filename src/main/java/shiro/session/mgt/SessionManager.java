package shiro.session.mgt;


import shiro.session.Session;
import shiro.session.SessionException;

public interface SessionManager {

    Session start(SessionContext context);

    Session getSession(SessionKey key) throws SessionException;

}
