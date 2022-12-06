package advent2022;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day3 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2022input/day3.txt";
	
	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());
	
	static HashMap<String, Integer> PRIORITY_MAP = createPriorityMap();
	
	public static void main (String[] args) {
		System.out.println("Answer to A is: " + solveAdventA());
		System.out.println("Answer to B is: " + solveAdventB());
	}
	
	private static HashMap<String, Integer> createPriorityMap() {
		HashMap<String, Integer> map = new HashMap<>();
		for(int i = 97; i < 123; i++) {
			map.put(String.valueOf((char)i), i - 96);
		}
		for(int i = 65; i < 91; i++) {
			map.put(String.valueOf((char)i), i - 38);
		}
		
		return map;
	}

	private static int solveAdventA() {

		int sum = 0;
		
		for(String bothRucksacs : INPUT_DATA) {
			String itemInBoth = findItemInBoth(bothRucksacs);
			sum += PRIORITY_MAP.get(itemInBoth);
		}

		return sum;
	}

	private static String findItemInBoth(String bothRucksacs) {
		String firstHalf = bothRucksacs.substring(0, bothRucksacs.length() / 2);
		String secondHalf = bothRucksacs.substring(bothRucksacs.length() / 2, bothRucksacs.length());
		
		for(int i = 0 ; i < firstHalf.length() ; i++) {
			if(secondHalf.contains(firstHalf.substring(i, i + 1))) {
				return firstHalf.substring(i, i + 1);
			}
		}
		
		return "";
	}

	private static int solveAdventB()  {
		
		int sum = 0;
		
		for(int i = 0; i < INPUT_DATA.size() ; i = i + 3) {
			sum += getPriorityB(INPUT_DATA.get(i), INPUT_DATA.get(i + 1), INPUT_DATA.get(i + 2));
		}

		return sum;

	}

	private static int getPriorityB(String s1, String s2, String s3) {

		
		for(int i = 0 ; i < s1.length() ; i++) {
			if(s2.contains(s1.substring(i, i + 1))) {
				if(s3.contains(s1.substring(i, i + 1))) {
					return PRIORITY_MAP.get(s1.substring(i,  i + 1));
				}
			}
		}
		return 0;
	}


}
