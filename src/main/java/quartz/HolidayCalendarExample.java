package quartz;

import org.apache.commons.lang3.time.DateUtils;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;

import java.text.ParseException;
import java.util.Date;

public class HolidayCalendarExample {
    public static void main(String[] args) throws ParseException {

        try {
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();

            HolidayCalendar cal = new HolidayCalendar();
            cal.addExcludedDate(DateUtils.parseDate("2019-01-01", "yyyy-MM-dd"));
            sched.addCalendar("myHolidays", cal,false ,false);

            Trigger t = TriggerBuilder.newTrigger()

                    .withIdentity("myTrigger")
                    .forJob("myJob")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).repeatForever())
                    .modifiedByCalendar("MyHolidays")
                    .build();


        }catch(Exception e){

        }
    }

    public SimpleTrigger simpleTriggerNoRepeat(){
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("trigger", "group1")
                .startAt(new Date("2018-11-27 23:00:00"))
                .forJob("job1", "group1").build();

        return trigger;
    }

    public Trigger triggerRepat(JobDetail myJob) throws ParseException {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger3", "group1")
                .startAt(DateUtils.parseDate("2018-11-27 00:00:00","yyyy-MM-dd HH:mm:ss"))
                .withSchedule( SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10))
                .forJob(myJob)
                .build();

        return trigger;
    }

    public Trigger futureTrigger(JobKey myjobKey){
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger5", "group1")
                .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.MINUTE))
                .forJob(myjobKey)
                .build();
        return trigger;
    }

    public Trigger tiggerWithEnd(){
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger7", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5)
                .repeatForever()).endAt(DateBuilder.dateOf(22,0,0))
                .build();
        return trigger;
    }

    public Trigger triggerWithDifferntTimeStep(JobDetail myJob){
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger8")
                .startAt(DateBuilder.evenHourDate(null))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(2)
                .repeatForever()).forJob(myJob).build();


        return trigger;
    }
}
