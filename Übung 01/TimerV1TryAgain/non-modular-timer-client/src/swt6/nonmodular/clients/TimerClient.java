package swt6.nonmodular.clients;

import swt6.nonmodular.beans.SimpleTimer;

public class TimerClient {
    private static void sleep(int millis){
        try{
            Thread.sleep(millis);
        }
        catch (InterruptedException e){}
    }

    public static void main(String[] args){
        SimpleTimer timer = new SimpleTimer(10, 1000);
        //timer.addTimerListener(te -> System.out.printf("timer expired: %d/%d%n", te.getTickCount(), te.getNoTicks()));
        timer.addTimerListener(te -> System.out.println("timer expired: " + te.getTickCount() + "/" + te.getNoTicks() + "\n"));

        timer.start();
        sleep(2000);
        timer.stop();
        sleep(2000);
        timer.start();
    }
}
