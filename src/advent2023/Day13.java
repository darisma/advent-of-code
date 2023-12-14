package advent2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day13 {

    static AdventInputReader ir = new AdventInputReader();
    static final String FILENAME = "2023input/day13.txt";

    static List<String> INPUT_DATA = ir.getStringStream(FILENAME).collect(Collectors.toList());

    public static void main (String[] args) {
        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }

    private static long solveAdvent1a() {

        List<List<String>> listOfPatterns = getPatternList();        
        List<Integer> results = new ArrayList<>();
        int i = 0;
        for(List<String> p : listOfPatterns) {
            
            List<Integer> horizontalNumbers = getNumbers(p);
            List<Integer> verticalNumbers = getVerticalNumbers(p);
            
            Optional<Integer> horizontalReflection = reflectionAt(horizontalNumbers, null);
            Optional<Integer> verticalReflection = reflectionAt(verticalNumbers, null);
        
            if(horizontalReflection.isPresent()) {
                results.add(100 * (horizontalReflection.get()));
            }else
            if(verticalReflection.isPresent()) {
                results.add(verticalReflection.get());
            }else {
                results.add(0);
                System.out.println("no reflection found for pattern number " + i);
            }
            i ++;
        }
        return results.stream().mapToInt(e -> e).sum();
    }

    private static List<List<String>> getPatternList() {
        List<List<String>> listOfPatterns = new ArrayList<List<String>>();
        List<String> pattern = new ArrayList<>();     
        for(String s: INPUT_DATA) {
   
            if(s.length()> 0) {
                pattern.add(s);
            }else {
                listOfPatterns.add(pattern);
                pattern = new ArrayList<>();
            }
        }
        listOfPatterns.add(pattern);
        return listOfPatterns;
    }

    private static Optional<Integer> reflectionAt(List<Integer> horizontalNumbers, Optional<Integer> originalReflection) {
        for(int i = 1; i < horizontalNumbers.size(); i ++) {
            
            Stack<Integer> left = new Stack<>();
            for(int j = 0; j < i; j++) {
                left.push(horizontalNumbers.get(j));
            }
            
            Stack<Integer> right = new Stack<>();
            for(int j = horizontalNumbers.size() - 1; j >= i; j--) {
                right.push(horizontalNumbers.get(j));
            }
            
            boolean found = true;
            while(found == true && ! (left.empty() || right.empty()) ) {
                Integer lInt = left.pop();
                Integer rInt = right.pop();
                       
                if(lInt.intValue() != rInt.intValue()) {
                    found = false;
                }
            }
            
            if(found && (originalReflection == null || originalReflection.isEmpty() || i != originalReflection.get())) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    private static List<Integer> getVerticalNumbers(List<String> p) {
        List<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < p.get(0).length(); i ++) {
            StringBuilder sb = new StringBuilder();

            for(int j = 0; j < p.size(); j ++) {
                sb.append(p.get(j).charAt(i));
            }
            String bitString = sb.toString().replace('.', '0').replace('#', '1');
            numbers.add(Integer.parseInt(bitString, 2));
        }
        return numbers;
    }

    private static List<Integer> getNumbers(List<String> p) {
        List<Integer> numbers = new ArrayList<>();
        
        for (String s : p) {
            String bitString = s.replace('.', '0').replace('#', '1');
            numbers.add(Integer.parseInt(bitString, 2));
        }
        return numbers;
    }

    private static long solveAdvent1b() {
        List<List<String>> listOfPatterns = getPatternList();        
        List<Integer> results = new ArrayList<>();

        for(List<String> p : listOfPatterns) {
            // first get original reflection
            List<Integer> originalHorizontalNumbers = getNumbers(p);
            List<Integer> originalverticalNumbers = getVerticalNumbers(p);
            
            Optional<Integer> originalHorizontalReflection = reflectionAt(originalHorizontalNumbers, null);
            Optional<Integer> originalverticalReflection = reflectionAt(originalverticalNumbers, null);
            
            int originalResult = 0;
            
            if(originalHorizontalReflection.isPresent()) {
                originalResult = 100 * originalHorizontalReflection.get();
            }else
            if(originalverticalReflection.isPresent()) {
                originalResult = originalverticalReflection.get();
            }
            // count how many #
            // start changing one by one to find which one successfully returns a different reflection
            
            int amountOfRocks = p.stream().mapToInt(e -> calculateRocks(e)).sum();
            
            for(int r = 0; r < amountOfRocks; r ++) {
                // change r th rock to ash
                
                List<String> lista = rockToAsh(p, r);

                List<Integer> horizontalNumbers = getNumbers(lista);
                List<Integer> verticalNumbers = getVerticalNumbers(lista);
                
                Optional<Integer> horizontalReflection = reflectionAt(horizontalNumbers, originalHorizontalReflection);
                Optional<Integer> verticalReflection = reflectionAt(verticalNumbers, originalverticalReflection);
            
                int result = 0;
                if(horizontalReflection.isPresent()) {
                    result = 100 * horizontalReflection.get();
                }else
                if(verticalReflection.isPresent()) {
                    result = verticalReflection.get();
                }

                if(r == amountOfRocks - 1 && (result == 0 || result == originalResult)) {
                    System.out.println("Problem for pattern " + p);
                }
                
                if(result != originalResult && result > 0) {
                    results.add(result);
                    //System.out.println("Added a result of " + result + " for pattern " + p);
                    break; // next pattern
                }
            }
        }
        return results.stream().mapToInt(e -> e).sum();
    }

    private static List<String> rockToAsh(List<String> p, int r) {
        List<String> pattern = new ArrayList<>();
        int rocksFound = 0;
        boolean rockSmashed = false;
        for(int i = 0; i < p.size(); i ++) {
            String string = p.get(i);
            if(rockSmashed) {
                pattern.add(string);
                continue;
            }
            for(int j = 0; j < p.get(0).length(); j ++) {
                if(string.charAt(j) == '#') {
                    if(rocksFound == r) {
                        String smashedRow = smashRockToAshAt(j, string);
                        pattern.add( smashedRow);
                        rockSmashed = true;
                    }
                    rocksFound ++;
                }
            }
            if(!rockSmashed) {
                pattern.add(string);
            }
        }

        return pattern;
    }

    private static String smashRockToAshAt(int i, String string) {
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < string.length(); j++) {
            if(string.charAt(j) == '.') {
                sb.append('.');
            }else if(i == j){
                sb.append('.');

            }else {
                sb.append('#');
            }
        }
        return sb.toString();
    }

    private static int calculateRocks(String e) {
        int counter = 0;
        for(int i = 0; i < e.length() ; i++) {
            if(e.charAt(i) == '#') {
                counter++;
            }
        }
        return counter;
    }
}