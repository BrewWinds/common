package shiro.session.mgt.eis;


import shiro.session.Session;

import java.io.Serializable;

public interface SessionGenerator {

    Serializable generateId(Session session);

}
