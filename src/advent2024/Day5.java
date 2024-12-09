package advent2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day5 {

	static AdventInputReader ir = new AdventInputReader();
	static final String FILENAME = "2024input/day5.txt";

	static final List<String> inputData = ir.getStringStream(FILENAME).collect(Collectors.toList());

	public static void main(String[] args) {
		solveAdvent1a();

	}

	private static void solveAdvent1a() {
		int resulta = 0;
		int resultb = 0;

		List<String> rules = inputData.stream().filter(e -> e.contains("|")).collect(Collectors.toList());

		List<String> booklets = inputData.stream().filter(e -> e.contains(",")).collect(Collectors.toList());

		for (String s : booklets) {
			boolean compliant = true;
			String[] numbers = s.split(",");

			for (int i = 0; i < numbers.length; i++) {
				if (i != 0) {
					if (rules.contains(numbers[i] + "|" + numbers[i - 1])) {
						// ei kelpaa
						compliant = false;
						System.out.println("Not compliant because of " + numbers[i] + "|" + numbers[i - 1]);
					}
				}
			}
			if (compliant) {
				resulta = resulta + Integer.parseInt(numbers[numbers.length / 2]);
			} else {
				resultb = resultb + orderAndReturnMiddle(numbers, rules);
			}
		}

		System.out.println("Answer to a : " + resulta);
		System.out.println("Answer to b : " + resultb);
	}

	private static int orderAndReturnMiddle(String[] numbers, List<String> rules) {

		List<Page> pages = new ArrayList<>();
		for (int i = 0; i < numbers.length; i++) {
			pages.add(new Page(numbers[i], rules));
		}

		Page[] pageArray = pages.toArray(new Page[pages.size()]);

		Arrays.sort(pageArray);

		return pageArray[pageArray.length / 2].getNumberValueAsInt();
	}

	static class Page implements Comparable<Page> {
		List<String> preceding = new ArrayList<>();
		List<String> following = new ArrayList<>();

		String numberValue;

		public Page(String number, List<String> rules) {
			numberValue = number;
			this.preceding = rules.stream().filter(e -> e.endsWith(number)).map(e -> e.split("\\|")[0])
					.collect(Collectors.toList());
			this.following = rules.stream().filter(e -> e.startsWith(number)).map(e -> e.split("\\|")[1])
					.collect(Collectors.toList());
		}

		public int getNumberValueAsInt() {
			return Integer.valueOf(numberValue);
		}

		@Override
		public int compareTo(Page o) {
			if (this.preceding.contains(o.numberValue)) {
				return 1;
			} else {
				return -1;
			}
		}
	}
}