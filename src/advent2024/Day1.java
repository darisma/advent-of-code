package advent2024;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day1 {

	static AdventInputReader ir = new AdventInputReader();
	static final String FILENAME = "2024input/day1.txt";

	static List<String> locationData = ir.getStringStream(FILENAME).collect(Collectors.toList());
	static List<Integer> leftList = locationData.stream().map(e -> Integer.parseInt(e.substring(0, e.indexOf(" "))))
			.sorted().collect(Collectors.toList());
	static List<Integer> rightList = locationData.stream().map(e -> Integer.parseInt(e.substring(e.indexOf("   ") + 3)))
			.sorted().collect(Collectors.toList());

	static HashMap<String, String> mappings = new HashMap<String, String>();

	public static void main(String[] args) {

		System.out.println("Answer to 1a is: " + solveAdvent1a());
		System.out.println("Answer to 1b is: " + solveAdvent1b());
	}

	private static int solveAdvent1a() {
		int result = 0;

		for (int i = 0; i < leftList.size(); i++) {
			result = result + Math.abs(leftList.get(i) - rightList.get(i));
		}

		return result;
	}

	private static long solveAdvent1b() {
		long result = 0;

		result = leftList.stream().map(left -> left * rightList.stream().filter(e -> e.equals(left)).count())
				.collect(Collectors.summingLong(Long::longValue));

//        for (int i = 0; i < leftList.size(); i ++) {
//        	Integer left = leftList.get(i);
//        	result = result + left * rightList.stream().filter(e -> e.equals(left)).count();
//        }

		return result;
	}
}