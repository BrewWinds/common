package shiro.subject;

import shiro.mgt.SecurityManager;
import shiro.util.ThreadContext;

import java.io.Serializable;

public interface Subject {


    public static class Builder{

        private SecurityManager securityManager;

        private SubjectContext subjectContext;

        public static void setSecurityManager(SecurityManager securityManager){
        }


        public Builder(){
        }

        public Builder(SecurityManager securityManager){
            if(securityManager == null){
                throw new NullPointerException();
            }
            this.securityManager = securityManager;
            this.subjectContext = newSubjectContextInstance();
        }

        protected SubjectContext newSubjectContextInstance(){
            return new DefaultSubjectContext();
        }

        public Subject buildSubject(){
            return this.securityManager.createSubject(this.subjectContext);
        }

        public Subject getSubject(){
            Subject subject = ThreadContext.getSubject();
            if(subject == null){
                subject = new Subject.Builder().buildSubject();
                ThreadContext.bind(subject);
            }
            return subject;
        }

        public Builder sessionId(Serializable sessionId){
            if(sessionId!=null){
                this.subjectContext.setSessionId(sessionId);
            }
            return this;
        }
    }

}
