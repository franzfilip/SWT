package temperaturesensor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weathermodel.Measurement;
import weathermodel.MeasurementEvent;
import weathermodel.Sensor;
import weathermodel.SensorListener;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class TempSensor implements Sensor {
    private CopyOnWriteArrayList<SensorListener> listeners = new CopyOnWriteArrayList<>();
    private Timer timer;
    private boolean isMeasuring;
    private static Logger logger = LoggerFactory.getLogger(TempSensor.class);

    private String unit;
    private long interval;

    public TempSensor(String unit, long interval) {
        this.unit = unit;
        this.interval = interval;
        this.isMeasuring = false;
        this.timer = new Timer();

        this.startMeasurementProcess();
    }

    @Override
    public void measure() {
        Random random = new Random();
        double measurement = random.nextDouble(-15, 40);
        listeners.forEach(l -> l.notifyAboutNewMeasurement(new MeasurementEvent(this, new Measurement(measurement, unit, LocalDateTime.now()))));
    }

    @Override
    public void startMeasurementProcess() {
        if(isMeasuring){
            logger.error("Cannot start the Measurementprocess again");
            throw new IllegalStateException("Cannot start Temperaturesensor, already running");
        }

        this.isMeasuring = true;
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                measure();
            }
        }, 0, interval);
        logger.info("Measurementprocess started");
    }

    @Override
    public void stopMeasurementProcess() {
        if(isMeasuring){
            timer.cancel();
            logger.info("Measurementprocess stopped");
        }
    }

    @Override
    public void addWeatherStation(SensorListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeWeatherStation(SensorListener listener) {
        listeners.remove(listener);
    }
}
