package advent2025;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.AdventInputReader;

public class Day2 {

    private static final Logger log = LoggerFactory.getLogger(Day2.class);

    static final String FILENAME = "2025input/day2.txt";
    static final List<String> idRangeData = new AdventInputReader().getStringStream(FILENAME).collect(Collectors.toList());
    static final String[] ranges = idRangeData.get(0).split(",");
    static final List<IdRange> rotationData =
            List.of(ranges).stream().map(Day2::parseIdRange).collect(Collectors.toList());

    public static void main(String[] args) {
        System.out.println("Answer to 1a is: " + solveAdvent1a(rotationData));
        System.out.println("Answer to 1b is: " + solveAdvent1b(rotationData));
    }

    private static class IdRange {
        final long lowLimit;
        final long highLimit;

        IdRange(long low, long high) {
            this.lowLimit = low;
            this.highLimit = high;
        }

        @Override
        public String toString() {
            return lowLimit + "-" + highLimit;
        }

        public List<Long> getRangeValues() {
            return java.util.stream.LongStream.rangeClosed(lowLimit, highLimit)
                    .boxed()
                    .collect(Collectors.toList());
        }

        public List<Pair<String, String>> getRangeTuplesForStrings() {
            return java.util.stream.LongStream.rangeClosed(lowLimit, highLimit)
                    .mapToObj(Long::toString)
                    .map(s -> new Pair<String, String>(s.substring(0, s.length()/2), s.substring(s.length()/2)))
                    .collect(Collectors.toList());
        }
    }

    private static IdRange parseIdRange(String idRange) {
        return new IdRange(Long.parseLong(idRange.substring(0, idRange.indexOf("-"))),
                Long.parseLong(idRange.substring(idRange.indexOf("-") + 1)));
    }

    private static long solveAdvent1a(List<IdRange> data) {
        log.info("Processing ID ranges");

        long count = 0;

        for (IdRange range : data) {

            for(Pair<String, String> pair : range.getRangeTuplesForStrings()) {
                String firstHalf = pair.getValue0();
                String secondHalf = pair.getValue1();

                if (firstHalf.equals(secondHalf)) {
                    log.info("Found matching pair: " + pair);
                    count += Long.parseLong(firstHalf + secondHalf);
                }
            }

        }

        return count;
    }

    private static long solveAdvent1b(List<IdRange> data) {

        long count = 0;

        for(IdRange range : data) {
            for(Long value : range.getRangeValues()) {
                if (findInvalidId(value)) {
                    log.info("Found invalid ID: " + value);
                    count += value;
                }
            }
        }

        return count;
    }

    private static boolean findInvalidId(Long value) {
        // check if all digits are the same
//        if(value.toString().chars().distinct().count() == 1) {
//            log.info("Found invalid ID: " + value);
//            return true;
//        }

        // check if value contains sequential numbers of any length
        String valueStr = value.toString();
        for(int i = 1; i <= value.toString().length() / 2; i++) {
            if(checkRepeatingValue(i ,valueStr)) {
                return true;
            }
       }

        return false;
    }

    private static boolean checkRepeatingValue(int partLength, String valueStr) {

        if(valueStr.length() % partLength != 0) {
            return false;
        }

        // create a list of parts
        List<String> parts = IntStream.range(0, valueStr.length() / partLength).mapToObj(i ->
                valueStr.substring(i * partLength, (i + 1) * partLength)
        ).collect(Collectors.toList());

        // check if all parts are the same
        return parts.stream().distinct().count() == 1;

    }


}