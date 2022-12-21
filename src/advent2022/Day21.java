package advent2022;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import utils.AdventInputReader;

public class Day21 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2022input/day21.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day21().solveAdventA());
		System.out.println("Answer to B is: " + new Day21().solveAdventB());
	}

	private BigInteger solveAdventA() {
		Map<String, Monkey> monkeys = new HashMap<>();
		INPUT_DATA.stream().forEach(e -> monkeys.put(e.split(":")[0], new Monkey(e)));
		return monkeys.get("root").getValue(monkeys);
	}

	private BigInteger solveAdventB() {
		Map<String, Monkey> monkeys = new HashMap<>();
		INPUT_DATA.stream().forEach(e -> monkeys.put(e.split(":")[0], new Monkey(e)));
		monkeys.get("root").calc = "=";

		// Root monkey 2 does not change with humn yell
		// Human should yell so that monkey 1 would scream the same as monkey 2
		// monkey 2 screams 122624242469304
		
		BigInteger minRange = BigInteger.ZERO;
		BigInteger maxRange = BigInteger.valueOf(10000000000000L);
		BigInteger result = BigInteger.valueOf(122624242469304L);
		BigInteger humnYells = BigInteger.ZERO;
		
		while (true) {
			BigInteger midRange = minRange.add(maxRange).divide(BigInteger.valueOf(2L));
			
			monkeys.get("humn").yelling = midRange;
			BigInteger testResult = monkeys.get("root").getValue(monkeys);
			if(testResult.equals(result)){
				humnYells = midRange;
				break;
			}
			if(testResult.compareTo(result) == 1) {
				minRange = midRange;
			}else {
				maxRange = midRange;
			}
		}
		
		return humnYells;
	}

	public class Monkey{
		
		String name;
		BigInteger yelling;
		String monkey1;
		String calc;
		String monkey2;
		
		public Monkey(String s) {
			String[] data = s.split(":");
			this.name = data[0];
			if(data[1].trim().length() < 4) {
				this.yelling = BigInteger.valueOf(Integer.parseInt(data[1].trim()));
			}else {
				String[] formula = data[1].trim().split(" ");
				this.monkey1 = formula[0].trim();
				this.calc = formula[1].trim();
				this.monkey2 = formula[2].trim();
			}
		}
		
		public BigInteger getValue(Map<String, Monkey> allMonkeys) {
			if(this.yelling != null) {
				return yelling;
			}else {
				switch (calc) {
				case "-":
					return allMonkeys.get(this.monkey1).getValue(allMonkeys).subtract(allMonkeys.get(this.monkey2).getValue(allMonkeys));
				case "+":
					return allMonkeys.get(this.monkey1).getValue(allMonkeys).add(allMonkeys.get(this.monkey2).getValue(allMonkeys));
				case "*":
					return allMonkeys.get(this.monkey1).getValue(allMonkeys).multiply(allMonkeys.get(this.monkey2).getValue(allMonkeys));
				case "/":
					return allMonkeys.get(this.monkey1).getValue(allMonkeys).divide(allMonkeys.get(this.monkey2).getValue(allMonkeys));
				case "=":
					// solution B, only return monkey1 value
					return allMonkeys.get(this.monkey1).getValue(allMonkeys);
				default:
					System.out.println("Hit default!!");
				}
			}
			return null;
		}
	}
}