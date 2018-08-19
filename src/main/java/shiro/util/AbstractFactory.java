package main.java.shiro.util;

public abstract class AbstractFactory<T> implements Factory<T> {

    private boolean singleton;
    private T singletonInstance;


    public AbstractFactory(){
        this.singleton = true;
    }

    public boolean isSingleton(){
        return singleton;
    }


    @Override
    public T getInstance() {
        T instance;

        if(isSingleton()){
            if(this.singletonInstance == null) {
                this.singletonInstance = createInstance();
            }
            instance = this.singletonInstance;
        }else{
            instance = createInstance();
        }

        if(instance == null){
            throw  new IllegalStateException("");
        }

        return instance;
    }

    protected abstract T createInstance();
}
