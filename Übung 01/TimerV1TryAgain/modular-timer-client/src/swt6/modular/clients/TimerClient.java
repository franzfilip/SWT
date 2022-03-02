package swt6.modular.clients;


import swt6.modular.beans.Timer;
import swt6.modular.beans.TimerFactory;
import swt6.modular.beans.TimerProvider;

import java.util.ServiceLoader;
//import swt6.modular.beans.impl.SimpleTimer;

public class TimerClient {
    private static void sleep(int millis){
        try{
            Thread.sleep(millis);
        }
        catch (InterruptedException e){}
    }

    private static void testTimer(){
        //SimpleTimer timer = new SimpleTimer(10, 1000);
        Timer timer = TimerFactory.getTimer(500, 10);
        //timer.addTimerListener(te -> System.out.printf("timer expired: %d/%d%n", te.getTickCount(), te.getNoTicks()));
        timer.addTimerListener(te -> System.out.println("timer expired: " + te.getTickCount() + "/" + te.getNoTicks() + "\n"));

        timer.start();
        sleep(2000);
        timer.stop();
        sleep(2000);
        timer.start();
    }

    private static Timer getBestTimer(int interval, int noTicks){
        ServiceLoader<TimerProvider> serviceLoader = ServiceLoader.load(TimerProvider.class);
        double minResolution = Double.MAX_VALUE;
        TimerProvider minProvider = null;

        for(TimerProvider provider: serviceLoader){
            if(provider.timerResolution() < minResolution){
                minProvider = provider;
                minResolution = minProvider.timerResolution();
            }
        }

        return minProvider == null ? null : minProvider.getTimer(interval, noTicks);
    }

    private static void testReflection(){
        Timer timer = getBestTimer(100, 10);
        if(timer == null){
            return;
        }

        try{
            System.out.printf("Fields of %s object %n", timer.getClass().getName());
            var fields = timer.getClass().getDeclaredFields();
            for(var field : fields){
                field.setAccessible(true);
                Object value = field.get(timer);
                System.out.printf("    %s -> %s%n", field.getName(), value);
            }
        }
        catch (IllegalAccessException ex){
            ex.printStackTrace();
        }
    }

    private static void testTimerProvider(){
        Timer timer = getBestTimer(100, 10);
        if(timer == null){
            return;
        }

        timer.addTimerListener(te -> System.out.println("timer expired: " + te.getTickCount() + "/" + te.getNoTicks() + "\n"));

        timer.start();
        sleep(2000);
        timer.stop();
        sleep(2000);
        timer.start();
    }

    public static void main(String[] args){
        System.out.println("====================== testTimer ======================");
        testTimer();

        System.out.println("====================== testTimerProvider ======================");
        testTimerProvider();

        System.out.println("====================== testTimerReflection ======================");
        testReflection();
    }
}
