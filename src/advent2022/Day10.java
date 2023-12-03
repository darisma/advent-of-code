package advent2022;

import java.util.List;
import java.util.stream.Collectors;
import utils.AdventInputReader;

public class Day10 {


	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2022input/day10.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());
	private static final String NOOP = "noop";

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day10().solveAdventA());
		System.out.println("Answer to B is: " + new Day10().solveAdventB());
	}

	private long solveAdventA() {
		int cycle = 0;
		int signal = 1;
		int totalSignal = 0;
		for(String s: INPUT_DATA) {
			if(s.split(" ")[0].equals(NOOP)) {
				cycle ++;
				if(importantCycle(cycle)) {
					totalSignal += cycle * signal;
				}
			}
			else {
				cycle ++;
				if(importantCycle(cycle)) {
					totalSignal += cycle * signal;
				}
				cycle ++;
				if(importantCycle(cycle)) {
					totalSignal += cycle * signal;
				}
				signal += Integer.parseInt(s.split(" ")[1]);
			}
			
		}
		return totalSignal;
	}

	private long solveAdventB() {

		int cycle = 0;
		int signal = 1;
		for(String s: INPUT_DATA) {
			if(s.split(" ")[0].equals(NOOP)) {
				System.out.print(spriteHits(cycle, signal));
				cycle ++;

			}	
			else {
				System.out.print(spriteHits(cycle, signal));
				cycle ++;
				System.out.print(spriteHits(cycle, signal));
				cycle ++;
				signal += Integer.parseInt(s.split(" ")[1]);
			}
			
		}
		
		return 0;
	}

	
	private String spriteHits(int cycle, int signal) {
		int cyclePos = cycle % 40;
		if(Math.abs(signal - cyclePos) < 2) {
			if(cyclePos == 0) {
				return System.lineSeparator() + "#";
			}
			return "#";
		}
		if(cyclePos == 0) {
			return System.lineSeparator() + ".";
		}
		return ".";
	}

	private boolean importantCycle(int cycle) {
		if (cycle == 20 || (cycle - 20) % 40 == 0) {
			return true;
		}
		return false;
	}



}
