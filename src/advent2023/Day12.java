package advent2023;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day12 {

    static AdventInputReader ir = new AdventInputReader();
    static final String FILENAME = "2023input/day12.txt";

    static List<String> INPUT_DATA = ir.getStringStream(FILENAME).collect(Collectors.toList());

    public static void main (String[] args) {
        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }

    private static long solveAdvent1a() {

        // create all possible combinations
        // check if they are valid

        int result = 0;

        for(String s : INPUT_DATA) {
            String springs = s.split(" ")[0];
            String[] groups = s.split(" ")[1].split(",");
            List<Integer> groupSizes = new ArrayList<>();
            for(String groupSize : groups) {
                groupSizes.add(Integer.valueOf(groupSize));
            }

            List<String> possibleStrings = generatePossibleStrings(springs);
            List<String> compliantStrings = getCompliantStrings(possibleStrings, groupSizes);
            result = result + compliantStrings.size();
        }

        return result;
    }

    private static List<String> getCompliantStrings(List<String> possibleStrings, List<Integer> groupSizes) {
        List<String> compliantStrings = new ArrayList<>();
        for(String s: possibleStrings) {
            List<String> springs = getSprings(s);
            if(springs.size() != groupSizes.size()) {
                continue;
            }
            
            boolean compliant = true;
            for(int i = 0; i < groupSizes.size(); i ++) {
                if(groupSizes.get(i) != springs.get(i).length()) {
                   compliant = false;
                }
            }
            if(compliant) {
                compliantStrings.add(s);
            }
        }
        
        return compliantStrings;
    }

    private static List<String> getSprings(String s) {
        List<String> springs = new ArrayList<>();
        boolean spring = false;
        int springLength = 0;
        for(int i = 0; i < s.length(); i ++) {
            if(s.charAt(i) == '#' && !spring ) {
                spring = true;
                springLength = 1;
            }else if (s.charAt(i) == '#' && spring ) {
                springLength++;
            }else if(s.charAt(i) == '.' && spring) {
                springs.add(s.substring(i - springLength, i));
                spring = false;
                springLength = 0;
            }
        }
        if(spring) {
            springs.add((s.substring(s.length() - springLength)));
        }
        return springs;
    }

    public static int getBit(int n, int k) {
        return (n >> k) & 1;
    }

    private static List<String> generatePossibleStrings(String springs) {
        List<String> possibleStrings = new ArrayList<>(); 

        List<Integer> indexesOfQuestionMarks = new ArrayList<>();
        for(int i = 0; i < springs.length();i++) {
            if(springs.charAt(i) == '?') {
                indexesOfQuestionMarks.add(i);
            }
        }

        double possibleCombinations = Math.pow(2, indexesOfQuestionMarks.size());

        for(int j = 0; j < possibleCombinations; j++) {
            String modifiedString = modifyString(springs, j, indexesOfQuestionMarks);
            possibleStrings.add(modifiedString);
        }

        return possibleStrings;
    }

    private static String modifyString(String springs, int j, List<Integer> indexesOfQuestionMarks) {
        StringBuilder stringBuilder = new StringBuilder();
        int indexNumber = 0;
        for(int i = 0; i < springs.length(); i ++) {
            if(springs.charAt(i) == '?') {
                if(getBit(j, indexNumber) == 1) {
                    stringBuilder.append("#");
                }else {
                    stringBuilder.append(".");
                }
                indexNumber ++;
            }else {
                stringBuilder.append(springs.charAt(i));
            }
        }
        return stringBuilder.toString();
    }

    private static long solveAdvent1b() {
        return 1;
    }
}