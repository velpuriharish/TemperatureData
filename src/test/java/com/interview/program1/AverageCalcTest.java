package com.interview.program1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AverageCalcTest {

    @Test
    public void groupNCalc(){
        List<SensorData> sensorDataList = Arrays.asList(
                SensorData.newSensorData().sensorId(1).epochTime(10000).temp(10).build(),
                SensorData.newSensorData().sensorId(1).epochTime(10002).temp(20).build(),
                SensorData.newSensorData().sensorId(1).epochTime(13005).temp(10).build()

        );
        Map<String, Double> stringDoubleMap = new AverageCalc().groupNCalc(1, sensorDataList);

        assertEquals("{10000-10999=15.0, 13000-13999=10.0}",stringDoubleMap.toString());
    }

    @Test
    public void process(){
        Map<String, Double> result = new AverageCalc().process("input1", 1);
        assertEquals("{12000-12999=42.0, 10000-10999=42.333333333333336, 11000-11999=47.5, 13000-13999=42.0}",result.toString());
    }

}