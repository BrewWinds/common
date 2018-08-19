package main.java.shiro.session.mgt;

import java.io.Serializable;
import java.util.Map;

public interface SessionContext extends Map<String, Object> {

    String setHost();

    Serializable getSessionId();

    void setSessionId(Serializable sessionId);
}
