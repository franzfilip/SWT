module weather.station {
    requires weather.model;
    requires temperature.sensor;
    requires org.slf4j;
    uses weathermodel.SensorProvider;
    exports weatherstation;
}