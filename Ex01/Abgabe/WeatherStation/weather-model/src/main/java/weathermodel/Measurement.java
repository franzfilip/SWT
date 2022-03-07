package weathermodel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Measurement {
    private double measurement;
    private String unit;
    private LocalDateTime timestamp;

    public Measurement(double measurement, String unit, LocalDateTime timestamp) {
        this.measurement = measurement;
        this.unit = unit;
        this.timestamp = timestamp;
    }

    public double getMeasurement() {
        return measurement;
    }

    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy: H-mm-ss-SSS");
        var tstamp = formatter.format(timestamp);
        return "Measurement{" +
                "measurement=" + measurement +
                ", unit='" + unit + '\'' +
                ", timestamp=" + tstamp +
                '}';
    }
}
