package swt6.modular.beans;

public class SimpleTimerProvider implements TimerProvider {
    @Override
    public double timerResolution() {
        return 1/1000.0;
    }

    @Override
    public Timer getTimer(int interval, int noTicks) {
        return new SimpleTimer(noTicks, interval);
    }
}
