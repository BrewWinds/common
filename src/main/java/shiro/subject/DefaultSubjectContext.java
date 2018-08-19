package shiro.subject;

import shiro.authc.AuthenticationInfo;
import shiro.authc.AuthenticationToken;
import shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class DefaultSubjectContext implements SubjectContext{

    @Override
    public SecurityManager getSecurityMangaer() {
        return null;
    }

    @Override
    public void setSecurityManager(SecurityManager securityManager) {

    }

    @Override
    public SecurityManager resolveSecurityManager() {
        return null;
    }

    @Override
    public Serializable getSessionId() {
        return null;
    }

    @Override
    public void setSessionId(Serializable sessionId) {

    }

    @Override
    public void setSubject(Subject subject) {

    }

    @Override
    public void setPrincipals(PrincipalCollection principals) {

    }

    @Override
    public Session getSession() {
        return null;
    }

    @Override
    public void setSession() {

    }

    @Override
    public Session resolveSession() {
        return null;
    }

    @Override
    public PrincipalCollection getPrincipals() {
        return null;
    }

    @Override
    public PrincipalCollection resolvePrincipals() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean authc) {

    }

    @Override
    public AuthenticationInfo getAuthenticationInfo() {
        return null;
    }

    @Override
    public void setAuthenticationInfo() {

    }

    @Override
    public AuthenticationToken getAuthenticationToken() {
        return null;
    }

    @Override
    public void setAuthenticationToken() {

    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public void setHost() {

    }

    @Override
    public String resolveHost() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return null;
    }

    @Override
    public Object put(String key, Object value) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Object> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return null;
    }
}
