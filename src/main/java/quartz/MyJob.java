package quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Date: 2018/11/28 14:23
 * @Description:
 */
public class MyJob implements Job {

    public void execute(JobExecutionContext context) {
        System.out.println("MyJob running");
    }

    public static void main(String[] args) {

        try {
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();

            JobDetail job = JobBuilder.newJob(MyJob.class)
                    .withIdentity("job1", "group1")
                    .storeDurably()
                    .build();

            sched.addJob(job, false);


            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .forJob(JobKey.jobKey("job1", "group1"))
                    .build();

            sched.scheduleJob(trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }


    }
}
