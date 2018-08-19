package shiro.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public interface Session {

    Serializable getId();

    Date getStartTimestamp();

    Date getLastAccessTime();

    long getTimeout() throws InvalidSessionException;

    void setTimeout(long maxIdleTimeInMillis) throws InvalidSessionException;

    String getHost();

    void touch() throws InvalidSessionException;

    void stop() throws InvalidSessionException;

    Collection<Object> getAttributeKeys() throws InvalidSessionException;

    Object getAttribute(Object key) throws InvalidSessionException;

    void setAttribute(Object key, Object value) throws InvalidSessionException;

    Object removeAttribute(Object key) throws InvalidSessionException;

}
