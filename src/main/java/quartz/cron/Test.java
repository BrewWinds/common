package quartz.cron;

import java.util.Calendar;
import java.util.Date;

/**
 * @Date: 2019/2/13 18:07
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        Calendar calendar = java.util.Calendar.getInstance();
        long time = 1231231231321231321L;

        calendar.setTime(new Date());


        DailyCalendar dc = new DailyCalendar("10:00", "18:25");
        dc.setInvertTimeRange(true);
        long current = System.currentTimeMillis();

        System.out.println(dc.isTimeIncluded(current));
    }
}
