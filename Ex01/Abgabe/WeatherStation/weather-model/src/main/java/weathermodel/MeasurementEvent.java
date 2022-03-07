package weathermodel;

import java.util.EventObject;

public class MeasurementEvent extends EventObject {
    Measurement measurement;

    public MeasurementEvent(Object source, Measurement measurement) {
        super(source);
        this.measurement = measurement;
    }

    public Measurement getMeasurement() {
        return measurement;
    }
}
