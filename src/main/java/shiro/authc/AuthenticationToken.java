package shiro.authc;

import java.io.Serializable;

public interface AuthenticationToken extends Serializable{

    Object getPrincipal();

    Object getCredentialss();

}
