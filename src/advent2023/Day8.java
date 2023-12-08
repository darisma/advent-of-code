package advent2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day8 {

	static AdventInputReader ir = new AdventInputReader();
	static final String FILENAME = "2023input/day8.txt";

	static List<String> gameData = ir.getStringStream(FILENAME).collect(Collectors.toList());

	static final char[] instructions = gameData.get(0).toCharArray();

	static HashMap<Integer, List<Long>> hits = new HashMap<>();
	
	static final HashMap<String, MapNode> map = createMap();

	public static void main (String[] args) {
		System.out.println("Answer to 1a is: " + solveAdvent1a());
		// Takes forever with this, need to see the cycles of each starting point
		// and find lcm (spoiled)
		System.out.println("Answer to 1b is: " + solveAdvent1b());
	}

    private static long solveAdvent1a() {
        String key = "AAA";
        int counter = 0;
        while((! key.equalsIgnoreCase("ZZZ"))) {
            String direction = String.valueOf(instructions[counter % instructions.length]);
            if(direction.equalsIgnoreCase("R")) {
                key = map.get(key).right;
            }else {
                key = map.get(key).left;
            }
            counter ++;

        }
        return counter;
	}

    private static long solveAdvent1b() {
        List<String> keys = getAllNodesThatEndWithA();
        int counter = 0; // needs to be long as the answer is too large for int
        while(! allKeysEndWithZ(keys, counter)) {
            String direction = String.valueOf(instructions[counter % instructions.length]);
            keys = moveAllKeys(keys, direction);
            counter ++;
            // if cycles found
            if(hits.size() == keys.size()) {
                break;
            }
        }
        
        List<Long> cycles = new ArrayList<>();
        for(int i = 0; i < hits.size(); i ++) {
            cycles.add(hits.get(Integer.valueOf(i)).get(0));
        }
        
        long lcm = cycles.get(0);
        
        for(int i = 1; i < cycles.size(); i ++) {
            lcm = lcm(lcm, cycles.get(i));
        }
        
        return lcm;
	}

    public static long lcm(long number1, long number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        long absNumber1 = Math.abs(number1);
        long absNumber2 = Math.abs(number2);
        long absHigherNumber = Math.max(absNumber1, absNumber2);
        long absLowerNumber = Math.min(absNumber1, absNumber2);
        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }
    
    private static List<String> moveAllKeys(List<String> keys, String direction) {
        List<String> newKeys = new ArrayList<>();
        for(String key: keys) {
            if(direction.equalsIgnoreCase("R")) {
                newKeys.add(map.get(key).right);
            }else {
                newKeys.add(map.get(key).left);
            }
        }
        return newKeys;
    }

    private static boolean allKeysEndWithZ(List<String> keys, long counter) {
        boolean oneHasFailed = true;
        for(int i = 0; i < keys.size(); i ++) {
            if(!keys.get(i).endsWith("Z")) {
                oneHasFailed = false;
            }else {
                // store hits to get cycle
                if(hits.get(Integer.valueOf(i)) == null) {
                    hits.put(Integer.valueOf(i), List.of(Long.valueOf(counter)));
                }else {
                    List<Long> newList = new ArrayList<>();
                    newList.addAll(hits.get(Integer.valueOf(i)));
                    newList.add(Long.valueOf(counter));
                    hits.put(Integer.valueOf(i), newList );
                }
            }
        }
        return oneHasFailed;
    }

    private static List<String> getAllNodesThatEndWithA() {
        List<String> keysThatEndWithA = new ArrayList<>();
        for(String s: map.keySet()) {
            if(s.endsWith("A")) {
                keysThatEndWithA.add(s);
            }
        }
        return keysThatEndWithA;
    }

    private static HashMap<String, MapNode> createMap(){
        HashMap<String, MapNode> mappi = new HashMap<>();
        for(String s: gameData.subList(2, gameData.size())) {
            mappi.put(s.substring(0,3), new MapNode(s));
        }
        return mappi;
    }
    
    static class MapNode {
        String left;
        String right;
        
        public MapNode(String initString) {
            this.left = initString.substring(7, 10);
            this.right = initString.substring(12, 15);
        }
    }
}