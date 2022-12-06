package advent2022;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day6 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2022input/day6.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day6().solveAdventA());
		System.out.println("Answer to B is: " + new Day6().solveAdventB());
	}

	private int solveAdventA() {
		int length = 4;
		for(int i = 0; i < INPUT_DATA.get(0).length() ; i++) {
			if(allCharsDifferent(INPUT_DATA.get(0).substring(i, i + length), length)) {
				return i + length;
			}
		}
		return 0;
	}

	private boolean allCharsDifferent(String substring, int length) {
		List<String> charsInSubString = new ArrayList<>();
		charsInSubString.add(substring.substring(0, 1));
		for(int i = 1; i < length ; i++) {
			if(charsInSubString.contains(substring.substring(i, i + 1))) {
				return false;
			}
			charsInSubString.add(substring.substring(i, i + 1));
		}
		return true;
	}

	private int solveAdventB() {
		int length = 14;
		for(int i = 0; i < INPUT_DATA.get(0).length() ; i++) {
			if(allCharsDifferent(INPUT_DATA.get(0).substring(i, i + length), length)) {
				return i + length;
			}
		}
		return 0;
	}
}
