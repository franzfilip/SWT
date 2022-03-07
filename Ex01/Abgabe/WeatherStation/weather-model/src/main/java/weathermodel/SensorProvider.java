package weathermodel;

public interface SensorProvider {
    Sensor initSensor(String unit, int interval);
}
