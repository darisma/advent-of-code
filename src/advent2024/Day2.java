package advent2024;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day2 {

    static AdventInputReader ir = new AdventInputReader();
    static final String FILENAME = "2024input/day2.txt";

    static final List<String> safetyData = ir.getStringStream(FILENAME).collect(Collectors.toList());
    static final List<List<Integer>> reportData = getSafetyNumbers(safetyData);

    public static void main(String[] args) {
        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }

    private static long solveAdvent1a() {
        return reportData.stream().filter(e -> isSafe(e)).count();
    }

    private static long solveAdvent1b() {
        return reportData.stream().filter(e -> isSafe(e) || isDampenerSafe(e)).count();
    }

    private static boolean isSafe(List<Integer> numberList) {
        boolean ascending = numberList.get(0) < numberList.get(1);

        for (int j = 0; j < numberList.size() - 1; j++) {
            if(!doSafetyCheck(numberList.get(j), numberList.get(j + 1), ascending)) {
                return false;
            }
        }

        return true;
    }

    private static boolean doSafetyCheck(Integer a, Integer b, boolean ascending) {
        if (ascending) {
            return (a < b && b - a <= 3);
        }

        return( b < a && a - b <= 3);
    }

    private static boolean isDampenerSafe(List<Integer> singleReport) {
        for(int i = 0; i < singleReport.size(); i++) {
            if( removeOneFromListAndTest(singleReport, i)) {
                return true;
            }
        }

        return false;
    }

    private static boolean removeOneFromListAndTest(List<Integer> singleReport, int i) {
        List<Integer> dampenedList = new ArrayList<>();
        dampenedList.addAll(singleReport.subList(0, i));
        dampenedList.addAll(singleReport.subList(i + 1, singleReport.size()));

        return isSafe(dampenedList);
    }

    private static List<List<Integer>> getSafetyNumbers(List<String> safetyData) {
        List<List<Integer>> numberList = new ArrayList<>();

        for (String safetyString : safetyData) {
            List<Integer> reportNumbers = new ArrayList<>();
            String[] reports = safetyString.split(" ");

            for(int i = 0; i < reports.length; i++) {
                reportNumbers.add(Integer.parseInt(reports[i]));
            }
            numberList.add(reportNumbers);
        }

        return numberList;
    }
}