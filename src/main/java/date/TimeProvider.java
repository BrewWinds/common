package date;

@FunctionalInterface
public interface TimeProvider {

    TimeProvider now = System::currentTimeMillis;

    long getTime();
}
