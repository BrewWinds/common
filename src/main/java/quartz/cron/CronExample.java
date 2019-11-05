package quartz.cron;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import quartz.listener.MyJobListener;

import java.util.TimeZone;

/**
 * @Date: 2018/11/28 14:20
 * @Description:
 */
public class CronExample {


    public static void main(String[] args) {

    }

    public Trigger cronTrigger(){

        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("trigger3", "group1")
            .withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 8-17 * * ?"))
            .forJob("myJob", "group1")
            .build();

        return trigger;
    }

    public Trigger timeSpecificTrigger(JobDetail myjob){
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger3", "group1")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(10, 42))
                .forJob(myjob)
                .build();
        return trigger;
    }

    public Trigger timeZoneSpecific(JobKey jobKey){
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger3","group1")
                .withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(DateBuilder.WEDNESDAY, 10, 42).inTimeZone(TimeZone.getTimeZone("America/Los_Angeles")))
                .forJob(jobKey)
                .build();
        return trigger;
    }


    public Trigger triggerWithMisfire(){
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger3", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 8-17 * * ?")
                    .withMisfireHandlingInstructionFireAndProceed())
                .forJob("myJob", "group1")
                .build();
        return trigger;
    }
}
