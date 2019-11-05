package quartz.cron;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * @Date: 2018/11/28 16:19
 * @Description:
 */
public class SchedulerExample {
    public static void main(String[] args) {
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler = sf.getScheduler();

            scheduler.start();

        }catch(Exception e){

        }
    }

    public void schedulerFromProperties() throws SchedulerException {
        String fileName = "";
        StdSchedulerFactory sf = new StdSchedulerFactory();
        sf.initialize(fileName);
        Scheduler scheduler = sf.getScheduler();
        scheduler.start();
        scheduler.shutdown();


    }
}
