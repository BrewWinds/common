package quartz.listener;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;
import org.quartz.impl.matchers.OrMatcher;


/**
 * @Date: 2018/11/28 14:42
 * @Description: JobListener & TriggerLister
 */
public class JobListenerExample {


    public static void main(String[] args) {

    }

    public void addJobListenerForJobKey(Scheduler scheduler) throws SchedulerException {
        scheduler.getListenerManager().addJobListener(
                new MyJobListener(), KeyMatcher.keyEquals(JobKey.jobKey("myJobName", "myJobGroup"))
        );
    }

    public void addJobListenerForGroup(Scheduler scheduler) throws SchedulerException {
        scheduler.getListenerManager().addJobListener(
                new MyJobListener(), GroupMatcher.groupEquals("myJobGroup")
        );
    }

    public void addJobListenerFor2Groups(Scheduler scheduler) throws SchedulerException {
        scheduler.getListenerManager().addJobListener(
                new MyJobListener(), OrMatcher.or(GroupMatcher.jobGroupEquals("myJobGroup"), GroupMatcher.jobGroupEquals("yourGroup"))
        );
    }

    public void addJobListenerForAlljob(Scheduler scheduler) throws SchedulerException {
        scheduler.getListenerManager().addJobListener(
                new MyJobListener(), EverythingMatcher.allJobs()
        );
    }
}
