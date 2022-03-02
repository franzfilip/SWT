package swt6.nonmodular.beans;

import java.util.EventObject;

public class TimerEvent extends EventObject {
    protected int noTicks = 0;
    protected int tickCount = 0;

    public int getNoTicks() {
        return noTicks;
    }

    public void setNoTicks(int noTicks) {
        this.noTicks = noTicks;
    }

    public int getTickCount() {
        return tickCount;
    }

    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;
    }

    public TimerEvent(Object source, int noTicks, int tickCount) {
        super(source);
        this.noTicks = noTicks;
        this.tickCount = tickCount;
    }
}
