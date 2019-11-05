package quartz.listener;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

/**
 * @Auther: 01378178
 * @Date: 2018/11/28 14:51
 * @Description:
 */
public class SchedulerListenerExample {
    public void addScheduleListener(Scheduler scheduler) throws SchedulerException {
        scheduler.getListenerManager().addSchedulerListener(new MySchedulerListener());
    }
}
