package swt6.nonmodular.beans;

import java.util.ArrayList;
import java.util.List;

    public class SimpleTimer {
        private int noTicks = 1;
        private int tickInterval = 100;
        private boolean stopTimer = false;
        private Thread tickerThread = null;
        private List<TimerListener> listeners = new ArrayList();

        public void start(){
            if(isRunning()){
                throw new IllegalStateException("Cannot start: Timer is already running");
            }

            int interval = getInterval();
            int noTicks = getNoTicks();

            tickerThread = new Thread(() -> {
                int tickCount = 0;
                while(!stopTimer && tickCount < noTicks){
                    try{
                        Thread.sleep(interval);
                    }
                    catch (InterruptedException e){
                        if(!stopTimer){
                            tickCount++;
                            fireEvent(new TimerEvent(SimpleTimer.class, noTicks, tickCount));
                        }
                    }
                }

                stopTimer = false;
                tickerThread = null;
            });

            tickerThread.start();
        }

        public void stop(){
            stopTimer = true;
        }

        private void fireEvent(TimerEvent event){
            listeners.forEach(l -> l.expired(event));
        }

        public boolean isRunning(){
            return tickerThread != null;
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

        public void addTimerListener(TimerListener listener){
            this.listeners.add(listener);
        }

        public void removeTimerListener(TimerListener listener){
            this.listeners.remove(listener);
        }
    }
