package swt6.modular.beans.impl;

import swt6.modular.beans.Timer;
import swt6.modular.beans.TimerEvent;
import swt6.modular.beans.TimerListener;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class SimpleTimer implements Timer {
    private int noTicks = 1;
    private int tickInterval = 100;
    private AtomicBoolean stopTimer = new AtomicBoolean(false);
    private AtomicReference<Thread> tickerThread = new AtomicReference(null);
    private CopyOnWriteArrayList<TimerListener> listeners = new CopyOnWriteArrayList<>();

    public SimpleTimer(int noTicks, int tickInterval) {
        this.noTicks = noTicks;
        this.tickInterval = tickInterval;
    }

    @Override
    public void start(){
        if(isRunning()){
            throw new IllegalStateException("Cannot start: Timer is already running");
        }

        int interval = getInterval();
        int noTicks = getNoTicks();

        tickerThread.set(new Thread(() -> {
            int tickCount = 0;
            while(!stopTimer.get() && tickCount < noTicks){
                try{
                    Thread.sleep(interval);
                }
                catch (InterruptedException e){

                }
                if(!stopTimer.get()){
                    tickCount++;
                    fireEvent(new TimerEvent(SimpleTimer.class, noTicks, tickCount));
                }
            }

            stopTimer.set(false);
            tickerThread.set(null);
        }));

        tickerThread.get().start();
    }

    @Override
    public void stop(){
        stopTimer.set(true);
    }

    private void fireEvent(TimerEvent event){
        listeners.forEach(l -> l.expired(event));
    }

    public boolean isRunning(){
        return tickerThread.get() != null;
    }

    public int getInterval(){
        return tickInterval;
    }

    public void setInterval(int tickInterval){
        this.tickInterval = tickInterval;
    }

    public int getNoTicks(){
        return noTicks;
    }

    private void setNoTicks(int noTicks){
        this.noTicks = noTicks;
    }

    @Override
    public void addTimerListener(TimerListener listener){
        this.listeners.add(listener);
    }

    @Override
    public void removeTimerListener(TimerListener listener){
        this.listeners.remove(listener);
    }
}
