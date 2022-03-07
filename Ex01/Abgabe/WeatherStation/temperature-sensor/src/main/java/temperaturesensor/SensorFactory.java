package temperaturesensor;

import weathermodel.Sensor;
import weathermodel.SensorProvider;

public class SensorFactory implements SensorProvider {

    @Override
    public Sensor initSensor(String unit, int interval) {
        return new TempSensor(unit, interval);
    }
}
