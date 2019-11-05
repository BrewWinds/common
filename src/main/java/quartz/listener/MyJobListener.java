package quartz.listener;

import org.quartz.listeners.JobListenerSupport;

/**
 * @Date: 2018/11/28 14:39
 * @Description:
 */
public class MyJobListener extends JobListenerSupport {

    public String getName() {
        return "myJobListener";
    }
}
