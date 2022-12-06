package advent2022;


import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day4 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2022input/day4.txt";
	private static final String PAIR_DELIMITER = ",";
	private static final String MIN_MAX_DELIMITER = "-";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + solveAdventA());
		System.out.println("Answer to B is: " + solveAdventB());
	}

	private static int solveAdventA() {

		int sum = 0;

		for(String fullLine :  INPUT_DATA) {
			sum += fullContains(fullLine);
		}
		
		return sum;
	}

	private static int fullContains(String fullLine) {
		String[] split = fullLine.split(PAIR_DELIMITER);
		int firstMin = Integer.parseInt(split[0].split(MIN_MAX_DELIMITER)[0]);
		int firstMax = Integer.parseInt(split[0].split(MIN_MAX_DELIMITER)[1]);
		int secondMin = Integer.parseInt(split[1].split(MIN_MAX_DELIMITER)[0]);
		int secondMax = Integer.parseInt(split[1].split(MIN_MAX_DELIMITER)[1]);
		
		if(firstMin >= secondMin && firstMax <= secondMax) {
			return 1;
		}
		if(secondMin >= firstMin && secondMax <= firstMax)
		{
			return 1;
		}
		return 0;
	}

	private static int solveAdventB() {

		int sum = 0;

		for(String fullLine :  INPUT_DATA) {
			sum += overlaps(fullLine);
		}
		
		return sum;
	}

	private static int overlaps(String fullLine) {
		String[] split = fullLine.split(PAIR_DELIMITER);
		int firstMin = Integer.parseInt(split[0].split(MIN_MAX_DELIMITER)[0]);
		int firstMax = Integer.parseInt(split[0].split(MIN_MAX_DELIMITER)[1]);
		int secondMin = Integer.parseInt(split[1].split(MIN_MAX_DELIMITER)[0]);
		int secondMax = Integer.parseInt(split[1].split(MIN_MAX_DELIMITER)[1]);

		if(firstMax < secondMin) {
			return 0;
		}
		
		if(firstMin > secondMax) {
			return 0;
		}
		return 1;
	}

}

