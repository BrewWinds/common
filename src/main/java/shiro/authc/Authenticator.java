package shiro.authc;

public interface Authenticator {

    AuthenticationInfo authenticate(AuthenticationToken authenticationToken) throws AuthenticationException;
}
