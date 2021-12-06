package advent2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day6 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day6.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day6().solveAdventA());
		System.out.println("Answer to B is: " + new Day6().solveAdventB());

	}

	private int solveAdventA() {

		List<Integer> fishes = Arrays.asList(INPUT_DATA.get(0).split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
		
		for (int i = 0 ; i < 80 ; i++) {
			fishes = advanceDay(fishes, i);
		}
		
		return fishes.size();
	}
	
	private long solveAdventB() {
		
		HashMap<Integer, Long> fishMap = new HashMap<>();
		
		List<Integer> fishes = Arrays.asList(INPUT_DATA.get(0).split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());

		for(int i = 0; i < 9 ; i++) {
			int k = i;
			fishMap.put(i, fishes.stream().filter(e -> e.intValue() == k).count());
		}

		for (int i = 0 ; i < 256 ; i++) {
			advanceDayTwo(fishMap);
		}
		
		return countFish(fishMap);
	}

	private List<Integer> advanceDay(List<Integer> fishes, int day) {
		fishes = fishes.stream().flatMap(e -> processFish(e).stream()).collect(Collectors.toList());
		return fishes;
	}

	private List<Integer> processFish(Integer i) {
		if(i == 0) {
			return Arrays.asList(Integer.valueOf(8), Integer.valueOf(6));
		}else return Arrays.asList(Integer.valueOf(i-1));
	}
	
	private HashMap<Integer, Long> advanceDayTwo(HashMap<Integer, Long> fishMap) {

		Long zeroes = fishMap.get(0);
		
		for(int i = 0; i < 8 ; i++) {
			fishMap.put(i, fishMap.get(i + 1));
		}
		
		fishMap.put(8, zeroes);
		fishMap.put(6, zeroes + fishMap.get(6));
		
		return fishMap;
	}

	private long countFish(HashMap<Integer, Long> fishMap) {
		return fishMap.entrySet().stream().mapToLong(e -> e.getValue()).sum();
	}

}
