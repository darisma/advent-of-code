package advent2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import utils.AdventInputReader;

public class Day7 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day7.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	static final List<Integer> crabs = Arrays.asList(INPUT_DATA.get(0).split(",")).stream()
			.map(Integer::parseInt).collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day7().solveAdventA());
		System.out.println("Answer to B is: " + new Day7().solveAdventB());

	}

	private long solveAdventA() {

		Integer maxPosition = crabs.stream().mapToInt(e -> e).max().orElse(0);

		List<Long> results = new ArrayList<>();

		for ( int i = 0 ; i < maxPosition ; i ++) {
			results.add(fuelConsumptionAtIndex(crabs, i));
		}

		return results.stream().mapToLong(e -> e).min().orElse(0);
	}

	private long solveAdventB() {

		Integer maxPosition = crabs.stream().mapToInt(e -> e).max().orElse(0);

		List<Long> results = new ArrayList<>();

		for ( int i = 0 ; i < maxPosition ; i ++) {
			results.add(fuelConsumptionAtIndexB(crabs, i));
		}

		return results.stream().mapToLong(e -> e).min().orElse(0);
	}

	private long fuelConsumptionAtIndex(List<Integer> crabs, int index) {
		long consumption = 0L;

		for ( Integer i : crabs ) {
			consumption = consumption + (Math.abs(index - i));
		}

		return consumption;
	}

	private long fuelConsumptionAtIndexB(List<Integer> crabs, int index) {
		long consumption = 0L;

		for ( Integer i : crabs ) {
			int baseConsumptionAtIndex = 1;
			for (int j = 0; j < Math.abs(index - i); j ++) {
				consumption = consumption + baseConsumptionAtIndex++;
			}
		}

		return consumption;
	}
}
