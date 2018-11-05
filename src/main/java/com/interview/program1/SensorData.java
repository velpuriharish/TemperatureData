package com.interview.program1;

public class SensorData {

    private final int sensorId;
    private final long epochTime;
    private final double temp;

    private SensorData(Builder builder) {
        this.sensorId = builder.sensorId;
        this.epochTime = builder.epochTime;
        this.temp = builder.temp;
    }

    public static Builder newSensorData() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "sensorId=" + sensorId +
                ", epochTime=" + epochTime +
                ", temp=" + temp +
                '}';
    }

    public int getSensorId() {
        return sensorId;
    }

    public long getEpochTime() {
        return epochTime;
    }

    public double getTemp() {
        return temp;
    }

    public static final class Builder {
        private int sensorId;
        private long epochTime;
        private double temp;

        private Builder() {
        }

        public SensorData build() {
            return new SensorData(this);
        }

        public Builder sensorId(int sensorId) {
            this.sensorId = sensorId;
            return this;
        }

        public Builder epochTime(long epochTime) {
            this.epochTime = epochTime;
            return this;
        }

        public Builder temp(double temp) {
            this.temp = temp;
            return this;
        }
    }


}
