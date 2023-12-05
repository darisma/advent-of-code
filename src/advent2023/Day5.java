package advent2023;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day5 {

    static AdventInputReader ir = new AdventInputReader();
    static final String FILENAME = "2023input/day5.txt";

    static List<String> gameData = ir.getStringStream(FILENAME).collect(Collectors.toList());

    static List<MapperRow> seedToSoil = getMapperRowList("seed-to-soil");
    static List<MapperRow> soilToFertilizer = getMapperRowList("soil-to-fertilizer");
    static List<MapperRow> fertilizerToWater = getMapperRowList("fertilizer-to-water");
    static List<MapperRow> waterToLight = getMapperRowList("water-to-light");
    static List<MapperRow> lightToTemperature = getMapperRowList("light-to-temperature");
    static List<MapperRow> temperatureToHumidity = getMapperRowList("temperature-to-humidity");
    static List<MapperRow> humidityToLocation = getMapperRowList("humidity-to-location");   

    public static void main (String[] args) {
        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }

    private static long solveAdvent1a() {
        List<Long> seeds = getSeeds();

        List<Long> mappedValues = seeds.stream().map(e -> getMappedValue(e, seedToSoil))
                .map(e -> getMappedValue(e, soilToFertilizer))
                .map(e -> getMappedValue(e, fertilizerToWater))
                .map(e -> getMappedValue(e, waterToLight))
                .map(e -> getMappedValue(e, lightToTemperature))
                .map(e -> getMappedValue(e, temperatureToHumidity))
                .map(e -> getMappedValue(e, humidityToLocation))
                .collect(Collectors.toList());

        return mappedValues.stream().mapToLong(e -> e).min().getAsLong();
    }


    private static long solveAdvent1b() {
        //return getMoreSeedsToo();
        return goBackwards();
    }

    private static long solveAdvent1bagain() {
        return goBackwards();
    }
    
    private static long goBackwards() {
        Long magicNumber = 0L;

        return getReverseMappedValue(getReverseMappedValue(getReverseMappedValue(getReverseMappedValue(getReverseMappedValue(getReverseMappedValue(
                getReverseMappedValue(magicNumber, humidityToLocation), temperatureToHumidity), lightToTemperature), waterToLight), fertilizerToWater), soilToFertilizer), seedToSoil);
        
    }

    private static List<MapperRow> getMapperRowList(String mapperName) {
        List<MapperRow> mapperList = new ArrayList<>();

        boolean gatherData = false;

        for(String dataRow : gameData) {
            if(dataRow.isBlank()) {
                gatherData = false;
            }
            if(gatherData) {
                mapperList.add(new MapperRow(dataRow));
            }
            if(dataRow.startsWith(mapperName)) {
                gatherData = true;
            }
        }
        return mapperList;
    }

    private static Long getMoreSeedsToo() {

        Long lowest = Long.valueOf(Long.MAX_VALUE);

        StringTokenizer tokenizer = new StringTokenizer(gameData.get(0).substring(gameData.get(0).indexOf(":") + 2));
        while(tokenizer.hasMoreTokens()) {
            long startingSeed = Long.parseLong(tokenizer.nextToken());
            long rangeLength = Long.parseLong(tokenizer.nextToken());
            for(long i = startingSeed ; i < startingSeed + rangeLength; i ++) {
                long value = getLocation(i);
                if( value < lowest) {
                    lowest = value;
                }
            }
        }
        return lowest;
    }

    private static long getLocation(long seed) {
        return getMappedValue(
                getMappedValue(
                        getMappedValue(
                                getMappedValue(
                                        getMappedValue(
                                                getMappedValue(
                                                        getMappedValue(seed, seedToSoil), 
                                                        soilToFertilizer),
                                                fertilizerToWater),
                                        waterToLight), 
                                lightToTemperature), 
                        temperatureToHumidity), 
                humidityToLocation);
    }

    private static List<Long> getSeeds() {
        List<Long> seeds = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(gameData.get(0).substring(gameData.get(0).indexOf(":") + 2));
        while(tokenizer.hasMoreTokens()) {
            seeds.add(Long.parseLong(tokenizer.nextToken()));
        }
        return seeds;
    }

    private static Long getMappedValue(Long source, List<MapperRow> mappings) {
        for (MapperRow mapperRow : mappings) {
            if(mapperRow.mappingHit(source)) {
                return mapperRow.getMappedValue(source);
            }
        }
        return source;
    }

    private static Long getReverseMappedValue(Long destination, List<MapperRow> mappings) {
        for (MapperRow mapperRow : mappings) {
            if(mapperRow.mappingHit(destination)) {
                return mapperRow.getReverseMapping(destination);
            }
        }
        return destination;
    }
    
    static class MapperRow{

        Long destinationRangeStart;
        Long sourceRangeStart;
        Long rangeLength;

        public MapperRow(String s) {
            String[] splitString = s.split(" ");
            this.destinationRangeStart = Long.parseLong(splitString[0]);
            this.sourceRangeStart = Long.parseLong(splitString[1]);
            this.rangeLength = Long.parseLong(splitString[2]);
        }

        public boolean mappingHit(Long sourceValue) {
            return sourceValue >= sourceRangeStart && sourceValue <= (sourceRangeStart + rangeLength);
        }

        public Long getMappedValue(Long sourceValue) {
            if(sourceValue >= sourceRangeStart && sourceValue <= (sourceRangeStart + rangeLength)) {
                return Long.valueOf(sourceValue + (destinationRangeStart - sourceRangeStart));
            }
            return sourceValue;
        }

        public boolean reverseMappingHit(Long destinationValue) {
            return (destinationValue >= destinationRangeStart && destinationValue <= (destinationRangeStart + rangeLength));
            }
        
        public Long getReverseMapping(Long destinationValue) {
            return Long.valueOf(Math.abs(destinationValue - destinationRangeStart)) + sourceRangeStart;
        }
    }

}
