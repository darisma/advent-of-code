package advent2023;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day14 {

    static AdventInputReader ir = new AdventInputReader();
    static final String FILENAME = "2023input/day14.txt";

    static List<String> INPUT_DATA = ir.getStringStream(FILENAME).collect(Collectors.toList());

    public static void main (String[] args) {
        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }

    private static long solveAdvent1a() {

        List<String> transposed = transpose(INPUT_DATA);
        List<String> tilted = tilt(transposed);
        
        return scoreTilted(tilted, transposed.get(0).length());
    }

    private static long solveAdvent1b() {
        List<String> transposed = transpose(INPUT_DATA);
        List<String> cycleResult = cycle(transposed);

        return scoreTilted(cycleResult, transposed.get(0).length());
     }
    
    private static List<String> cycle(List<String> transposed) {
        List<String> tilted = tilt(transposed);
        tilted = transpose(tilted);
        tilted = tilt(tilted);
        tilted = transpose(tilted);
        tilted = tilt(tilted);
        tilted = transpose(tilted);
        tilted = tilt(tilted);
        return tilted;
    }

    private static List<String> transpose(List<String> data) {
        List<String> transposed = new ArrayList<>();
        // transpose
        for(int i = 0; i < INPUT_DATA.get(0).length(); i++) {
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j < INPUT_DATA.size(); j++) {
                sb.append(INPUT_DATA.get(j).charAt(i));
            }
            transposed.add(sb.toString());
        }
        return transposed;
    }

    private static long scoreTilted(List<String> tilted, int length) {
        long score = 0;
        for(String s : tilted) {
            for(int i = 0; i < s.length(); i ++) {
                if(s.charAt(i) == 'O') {
                    score = score + length - i;
                }
            }
        }

        return score;
    }

    private static List<String> tilt(List<String> transposed) {
        int length = transposed.get(0).length();
        List<String> tilted = new ArrayList<>();
        for(String s : transposed) {
            tilted.add(tilted(s, length));
        }
        return tilted;
    }

    private static String tilted(String s, int length) {

        StringBuilder sb = new StringBuilder();
        
        for(int i=0; i < s.length() ; i++) {
            if(s.charAt(i) == '#') {
                int sbLength = sb.length();
                for(int j = 0 + sbLength; j < i ; j++ ) {
                    sb.append('.');
                }
                sb.append('#');
            }else if(s.charAt(i) == 'O') {
                sb.append('O');
            }
        }
        while(sb.length() < length) {
            sb.append('.');
        }
        return sb.toString();
    }
}