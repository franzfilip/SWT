package swt6.modular.beans;

public class TimerFactory {
    public static Timer getTimer(int interval, int noTicks){
        return new SimpleTimer(noTicks, interval);
    }
}
