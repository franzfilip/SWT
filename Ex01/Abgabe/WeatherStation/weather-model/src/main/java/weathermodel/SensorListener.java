package weathermodel;

public interface SensorListener {
    void notifyAboutNewMeasurement(MeasurementEvent event);
}
