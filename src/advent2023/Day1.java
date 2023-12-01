package advent2023;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day1 {

    static AdventInputReader ir = new AdventInputReader();
    static final String FILENAME = "2023input/day1.txt";
    
    static List<String> calibrationData = ir.getStringStream(FILENAME).collect(Collectors.toList());
    
    static HashMap<String, String> mappings = new HashMap<String, String>();
    
    public static void main (String[] args) {
        
        mappings.put("one", "1");
        mappings.put("two", "2");
        mappings.put("three", "3");
        mappings.put("four", "4");
        mappings.put("five", "5");
        mappings.put("six", "6");
        mappings.put("seven", "7");
        mappings.put("eight", "8");
        mappings.put("nine", "9");

        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }
    
    private static int solveAdvent1a() {
        int result = 0;
        
        for(String s: calibrationData) {
            int calibrationValue = Integer.parseInt(getFirstDigit(s) + getLastDigit(s));
            result = result + calibrationValue;
        }
        
        return result;
    }
    
    private static String getLastDigit(String s) {
        for(int i = s.length() - 1; i > -1; i --) {
            if(Character.isDigit(s.charAt(i))){
                return s.substring(i, i + 1);
            }
        }
        throw new RuntimeException("Could not find a digit");
    }

    private static String getFirstDigit(String s) {
        for(int i = 0; i < s.length(); i ++) {
            if(Character.isDigit(s.charAt(i))){
                return s.substring(i, i + 1);
            }
        }
        throw new RuntimeException("Could not find a digit");
    }

    private static int solveAdvent1b()  {
        int result = 0;
        
        for(String s: calibrationData) {
            int calibrationValue = Integer.parseInt(getFirstDigitB(s) + getLastDigitB(s));
            result = result + calibrationValue;
        }
        
        return result;
    }

    private static String getLastDigitB(String s) {
        for(int i = s.length() - 1; i > -1; i --) {
            if(Character.isDigit(s.charAt(i))){
                return s.substring(i, i + 1);
            }else if(startsWithNumberString(s.substring(i)).isPresent()) {
                return startsWithNumberString(s.substring(i)).get();
            }
        }
        throw new RuntimeException("Could not find a digit");
    }

    private static String getFirstDigitB(String s) {
        for(int i = 0; i < s.length(); i ++) {
            if(Character.isDigit(s.charAt(i))){
                return s.substring(i, i + 1);
            }else if(startsWithNumberString(s.substring(i)).isPresent()) {
                return startsWithNumberString(s.substring(i)).get();
            }
        }
        throw new RuntimeException("Could not find a digit");
    }

    private static Optional<String> startsWithNumberString(String s) {
        for(String key : mappings.keySet()) {
            if(s.startsWith(key)) {
                return Optional.of(mappings.get(key));
            }
        }
        return Optional.empty();
    }
}
