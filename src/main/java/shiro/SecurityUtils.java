package shiro;


import shiro.subject.Subject;
import shiro.util.ThreadContext;

public abstract class SecurityUtils {

    private static SecurityManager securityManager;


    public static Subject getSubject(){

        Subject subject = ThreadContext.getSubject();

        if(subject == null){
            subject = new Subject.Builder().buildSubject();
            ThreadContext.bind(subject);
        }
        return subject;
    }


    public static void setSecurityManger(SecurityManager securityManager){
        SecurityUtils.securityManager = securityManager;
    }

    public static SecurityManager getSecurityManager(){
        SecurityManager manager = (SecurityManager) ThreadContext.getSecurityManager();
        if(manager == null){
            manager = SecurityUtils.securityManager;
        }
        if(securityManager == null){
            throw new UnavailableSecurityManagerException();
        }

        return securityManager;

    }
}
