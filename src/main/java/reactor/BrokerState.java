package reactor;

/**
 * @Auther: 01378178
 * @Date: 2019/6/28 17:52
 * @Description:
 */
public class BrokerState {

    volatile Byte currentState = 0;

    public void newState (BrokerStates newState){
        this.newState(newState);
    }

    public void newState(Byte newState){
        currentState = newState;
    }
}
