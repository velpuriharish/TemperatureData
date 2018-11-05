package com.interview.program1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AverageCalc {

    public List<SensorData> readData(String fileName)  {

        List<SensorData> sensorDataList;
        Path path;
        try {
            path = Paths.get(getClass().getClassLoader()
                    .getResource(fileName).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid path "+fileName,e);
        }

        try (Stream<String> stream = Files.lines(path)) {
            sensorDataList = stream.map(x -> mapToSensorData(x)).collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Issue reading file " + fileName, e);
        }
        return sensorDataList;
    }

    private SensorData mapToSensorData(String str) {
        String[] tokens = str.split(",");
        int sensorId = new Integer(tokens[0]);
        long epochTime = new Long(tokens[1]);
        double temperature = new Double(tokens[2]);

        SensorData sensorData = SensorData.newSensorData().sensorId(sensorId).epochTime(epochTime).temp(temperature).build();
        return sensorData;

    }

    public Map<String, Double>  process(String path, int interval) {
        List<SensorData> sensorDataList = readData(path);
        Map<String, Double> averages = groupNCalc(interval, sensorDataList);
        return averages;

    }

    protected Map<String, Double> groupNCalc(int interval, List<SensorData> sensorDataList) {
        Map<String, List<Double>> tempByBucket = groupTemparatues(sensorDataList, interval);
        return calcAverages(tempByBucket);
    }

    public static void print(Map<String, Double> averages) {
        averages.keySet().stream().sorted().forEach(key -> System.out.println(key + " = " + averages.get(key)));
    }

    private Map<String, Double> calcAverages(Map<String, List<Double>> tempByBucket) {
        Map<String, Double> result = new HashMap<>();
        tempByBucket.forEach((key, value) -> {
            result.put(key, average(value));
        });
        return result;
    }

    private Double average(List<Double> value) {
        int size = value.size();
        double sum = value.stream().mapToDouble(Double::doubleValue).sum();
        return sum / size;
    }


    public Map<String, List<Double>> groupTemparatues(List<SensorData> sensorDataList, int interval) {

        Set<Long> processedEpochs = new HashSet<>();
        Map<String, List<Double>> tempByBucket = new HashMap<>();
        long startingEpoch = 10000;
        int intervalInMs = interval * 1000;

        sensorDataList.stream()
                .filter(sensorData -> checkFirstConstraint(processedEpochs, sensorData))
                .forEach(sensorData -> {
                    String bucket = findBucket(sensorData.getEpochTime(), startingEpoch, intervalInMs);
                    List<Double> doubles = tempByBucket.get(bucket);
                    if (doubles == null) {
                        doubles = new ArrayList<>();
                        tempByBucket.put(bucket, doubles);
                    }
                    doubles.add(sensorData.getTemp());

                });

        return tempByBucket;

    }

    protected String findBucket(long epochTime, long startingEpoch, int interval) {
        long diff = epochTime - startingEpoch;
        int value = (int) (diff / interval);
        long bucketLong = startingEpoch + value * interval;
        StringBuilder bucket = new StringBuilder().append(bucketLong).append("-").append((bucketLong + interval - 1) + "");
        return bucket.toString();
    }

    private boolean checkFirstConstraint(Set<Long> processedEpochs, SensorData sensorData) {

        if (processedEpochs.contains(sensorData.getEpochTime())) {
            return false;
        }
        processedEpochs.add(sensorData.getEpochTime());
        return true;
    }

    public static void main(String[] args) throws IOException {
        Map<String, Double> result = new AverageCalc().process("input1", 1);
        AverageCalc.print(result);

    }


}