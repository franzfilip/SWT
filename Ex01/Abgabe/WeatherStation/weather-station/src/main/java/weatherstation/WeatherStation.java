package weatherstation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weathermodel.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;

public class WeatherStation {
    private Sensor sensor;
    private List<Measurement> measurements;
    private static Logger logger = LoggerFactory.getLogger(WeatherStation.class);

    public WeatherStation(){
        measurements = new ArrayList<>();
        this.sensor = initSensor();
        if(this.sensor != null){
            sensor.addWeatherStation(event -> measurements.add(event.getMeasurement()));
        }
    }

    private Sensor initSensor(){
        logger.info("Init Sensor");
        ServiceLoader<SensorProvider> serviceLoader = ServiceLoader.load(SensorProvider.class);
        SensorProvider provider = null;

        for(var curr: serviceLoader){
            provider = curr;
        }

        if(provider != null){
            return provider.initSensor("°C", 500);
        }
        return null;
    }

    public String getLatest(){
        var mToSort = measurements;
        mToSort.sort(Comparator.comparing(Measurement::getTimestamp));
        return mToSort.get(mToSort.size() - 1).toString();
    }

    public List<String> getAllMeasurements(){
        var mToSort = measurements;
        mToSort.sort(Comparator.comparing(Measurement::getTimestamp));
        List<String> data = new ArrayList();
        for(var m : mToSort){
            data.add(m.toString());
        }

        return data;
    }

    public String getAverage(int numberOfMeasurements){
        double sum = 0.0;
        var arr = measurements.toArray();
        for(int i = arr.length - 1; i > arr.length - 1 - numberOfMeasurements; i--){
            sum += ((Measurement)arr[i]).getMeasurement();
        }

        return Math.round(sum / numberOfMeasurements) + measurements.get(0).getUnit();
    }

    public void shutDown(){
        logger.info("Shut down WeatherStation");
        sensor.stopMeasurementProcess();
    }
}
