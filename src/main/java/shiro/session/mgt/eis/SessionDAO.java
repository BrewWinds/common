package main.java.shiro.session.mgt.eis;

import main.java.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;

public interface SessionDAO {

    Serializable create(Session session);

    void removeSession(Session session);

    void update(Session session);

    Session readSession(Serializable sessionId);

    Collection<Session> getActiveSessions();
}
