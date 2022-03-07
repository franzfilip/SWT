module temperature.sensor {
    requires weather.model;
    requires org.slf4j;
    provides weathermodel.SensorProvider
            with temperaturesensor.SensorFactory;
}