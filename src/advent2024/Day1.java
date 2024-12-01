package advent2024;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day1 {

	static AdventInputReader ir = new AdventInputReader();
	static final String FILENAME = "2024input/day1.txt";

	static final List<String> locationData = ir.getStringStream(FILENAME).collect(Collectors.toList());
	static final List<Integer> leftList = locationData.stream()
			.map(e -> Integer.parseInt(e.substring(0, e.indexOf(" ")))).sorted().collect(Collectors.toList());
	static final List<Integer> rightList = locationData.stream()
			.map(e -> Integer.parseInt(e.substring(e.indexOf("   ") + 3))).sorted().collect(Collectors.toList());

	static HashMap<String, String> mappings = new HashMap<String, String>();

	public static void main(String[] args) {
		System.out.println("Answer to 1a is: " + solveAdvent1a());
		System.out.println("Answer to 1b is: " + solveAdvent1b());
	}

	private static int solveAdvent1a() {
		int result = 0;

		for (int i = 0; i < leftList.size(); i++) {
			result += Math.abs(leftList.get(i) - rightList.get(i));
		}

		return result;
	}

	private static long solveAdvent1b() {
		long result = 0;

		result = leftList.stream().map(left -> left * Collections.frequency(rightList, left))
				.collect(Collectors.summingInt(Integer::intValue));

		return result;
	}
}