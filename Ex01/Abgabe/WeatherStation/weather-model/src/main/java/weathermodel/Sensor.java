package weathermodel;

public interface Sensor {
    void measure();

    void startMeasurementProcess();

    void stopMeasurementProcess();

    void addWeatherStation(SensorListener listener);

    void removeWeatherStation(SensorListener listener);
}
