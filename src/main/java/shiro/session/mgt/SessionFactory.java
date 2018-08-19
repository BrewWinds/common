package shiro.session.mgt;

import shiro.session.Session;

public interface SessionFactory {

    Session createSession(SessionContext initData);

}
