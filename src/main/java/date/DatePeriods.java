package date;

import com.google.common.base.Preconditions;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;


/**
 * @Date: 2019/2/14 15:26
 * @Description:
 */
public enum DatePeriods {

    HOURLY(){

        @Override
        Interval next0(LocalDateTime original, LocalDateTime target, int step, int next) {
            Period period = new Period(original, target, PeriodType.hours());
            LocalDateTime begin = original.plusHours(calc(period.getHours(), step, next));
            return new Interval(begin, begin.plusHours(step));
        }
    },

    DAILY(){
        @Override
        Interval next0(LocalDateTime original, LocalDateTime target, int step, int next) {
            Period period = new Period(original, target, PeriodType.days());
            LocalDateTime begin = original.plusDays(calc(period.getDays(), step, next));
            return new Interval(begin, begin.plusDays(step));
        }
    },

    WEEKLY(){
        @Override
        Interval next0(LocalDateTime original, LocalDateTime target, int step, int next) {
            Period period = new Period(original, target, PeriodType.weeks());
            LocalDateTime begin = original.plusDays(calc(period.getWeeks(), step, next));
            return new Interval(begin, begin.plusWeeks(step));
        }
    },

    MONTHLY(){
        @Override
        Interval next0(LocalDateTime original, LocalDateTime target, int step, int next) {
            Period period = new Period(original, target, PeriodType.months());
            LocalDateTime begin = original.plusDays(calc(period.getMonths(), step, next));
            return new Interval(begin, begin.plusMonths(step));
        }
    },

    QUARTERLY(){
        @Override
        Interval next0(LocalDateTime original, LocalDateTime target, int step, int next) {
            return MONTHLY.next(original, target, step * 3, next);
        }
    },

    SEMIANNUAL(){
        @Override
        Interval next0(LocalDateTime original, LocalDateTime target, int step, int next) {
            return MONTHLY.next(original, target, step * 6, next);
        }
    },

    ANNUAL(){
        @Override
        Interval next0(LocalDateTime original, LocalDateTime target, int step, int next) {
            Period period = new Period(original, target, PeriodType.years());
            LocalDateTime begin = original.plusDays(calc(period.getYears(), step, next));
            return new Interval(begin, begin.plusYears(step));
        }
    };

    private static int calc(int qty, int step, int next){
        return (qty / step + next) * step;
    }

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss SSS";

    private static final LocalDateTime ORIGINAL = DateTimeFormat.forPattern(PATTERN)
            .parseLocalDateTime("2018-01-01 00:00:00 000");


    abstract Interval next0(LocalDateTime original, LocalDateTime target,
                            int step, int next);

    public Interval next(LocalDateTime original, LocalDateTime target, int step, int next){

        Preconditions.checkArgument(step > 0, "step must greater 0");
        Preconditions.checkArgument(!original.isAfter(target), "original must prior to target");

        return this.next0(original, target, step, next);
    }

    public static final class Interval{

        private final String begin;
        private final String end;
        private final Date beginDate;
        private final Date endDate;

        private Interval(LocalDateTime begin, LocalDateTime end){
            this.begin = begin.toString(PATTERN);
            this.beginDate = begin.toDate();

            end = end.minusMillis(1);
            this.end = end.toString(PATTERN);
            this.endDate = end.toDate();
        }

        public String getBegin() {
            return begin;
        }

        public String getEnd() {
            return end;
        }

        public Date getBeginDate() {
            return beginDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        @Override
        public String toString() {
            return begin+"-"+end;
        }
    }

    public static void main(String[] args) {

        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime d1 = LocalDateTime.parse("2018-10-10 10:00:30", format);
        LocalDateTime d2 = LocalDateTime.parse("2018-10-11 10:59:50", format);

        Period period = new Period(d1, d2, PeriodType.hours());
        System.out.println(period.getHours());
    }
}
