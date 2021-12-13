package advent2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day12 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day12test.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	private static final String START = "start";
	private static final String END = "end";
	
	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day12().solveAdventA());
		System.out.println("Answer to B is: " + new Day12().solveAdventB());
	}

	private long solveAdventA() {
		
		HashMap<String, List<String>> connections = createConnections(); 
		
		return countPaths(connections, false);
	}

	private long solveAdventB() {
		
		HashMap<String, List<String>> connections = createConnections();
		
		return countPaths(connections, true);
	}

	private HashMap<String, List<String>> createConnections() {
		
		HashMap<String, List<String>> connections = new HashMap<>();
		
		for (String s : INPUT_DATA) {
			String[] cave = s.split("-");
			
			if (!connections.containsKey(cave[0])) {
				connections.put(cave[0], new ArrayList<>());
			}
			if (!connections.containsKey(cave[1])) {
				connections.put(cave[1], new ArrayList<>());
			}
			
			connections.get(cave[0]).add(cave[1]);
			connections.get(cave[1]).add(cave[0]);
		}
		
		return connections;
	}
	
	private int countPaths(HashMap<String, List<String>> connections, boolean allowTwice) {
		return countPaths(new ArrayList<>(Arrays.asList(START)), connections, allowTwice);
	}

	private int countPaths(List<String> path, HashMap<String, List<String>> connections, boolean allowTwice) {

		if (path.get(path.size() - 1).equals(END)) {
			return 1;
		}

		int count = 0;
		
		for (String neighbour : connections.get(path.get(path.size() - 1))) {
			
			
			if (isUpperCase(neighbour) || !path.contains(neighbour)) {
				path.add(neighbour);
				count += countPaths(path, connections, allowTwice);
				path.remove(path.size() - 1);
			} else if (allowTwice && !neighbour.equals(START) && !neighbour.equals(END)) {
				path.add(neighbour);
				count += countPaths(path, connections, false);
				path.remove(path.size() - 1);
			}
		}
		return count;
	}
	
    private boolean isUpperCase(String str){
        char[] charArray = str.toCharArray();
        
        for(int i=0; i < charArray.length; i++){
            if( !Character.isUpperCase( charArray[i] ))
                return false;
        }
        
        return true;
    }
}
