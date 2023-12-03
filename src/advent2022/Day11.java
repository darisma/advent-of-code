package advent2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import utils.AdventInputReader;

public class Day11 {


	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2022input/day11.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME).collect(Collectors.toList());


	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day11().solveAdventA());
		System.out.println("Answer to B is: " + new Day11().solveAdventB());
	}

	private long solveAdventA() {

		return 0;
	}

	private long solveAdventB() {


		
		return 0;
	}

	
	class Monkey {
		List<Long> items = new ArrayList<>(); 
	}


}
