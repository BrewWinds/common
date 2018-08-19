package main.java.shiro.session.mgt;

import main.java.shiro.session.Session;

public interface SessionFactory {

    Session createSession(SessionContext initData);

}
