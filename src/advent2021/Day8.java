package advent2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import utils.AdventInputReader;

public class Day8 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day8.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day8().solveAdventA());
		System.out.println("Answer to B is: " + new Day8().solveAdventB());

	}

	private long solveAdventA() {
		
		List<String> outputParts = INPUT_DATA.stream().map(this::getOutputPart).collect(Collectors.toList());
		List<String> bits = outputParts.stream().flatMap(e -> Arrays.asList(e.split(" ", -1)).stream()).collect(Collectors.toList());
		
		return bits.stream().filter(e -> e.length() == 2 || e.length() == 3 || e.length() == 4 || e.length() == 7).count();

	}

	private long solveAdventB() {
		List<String[]> solvedSignals = INPUT_DATA.stream().map(this::getSignalPart).collect(Collectors.toList())
				.stream().map(this::solvedSignalsPerDigit).collect(Collectors.toList());
		
		List<String> digitParts = INPUT_DATA.stream().map(this::getOutputPart).collect(Collectors.toList());

		return getDigitsFromOutput(solvedSignals, digitParts).stream().mapToLong(Integer::longValue).sum();
	}

	private List<Integer> getDigitsFromOutput(List<String[]> solvedSignals, List<String> digitParts) {

		List<Integer> integerList = new ArrayList<>();
		for(int i = 0; i < solvedSignals.size() ; i ++) {
			integerList.add(solveInteger(solvedSignals.get(i), digitParts.get(i)));
		}
		return integerList;
	}

	private Integer solveInteger(String[] solved, String integerString) {
		String result = "";
		for(String digit : Arrays.asList(integerString.split(" ", -1)) ) {
			for(int i = 0 ; i < solved.length ; i ++) {
				if(containsAll(solved[i], digit) && solved[i].length() == digit.length()) {
					result = result.concat(String.valueOf(i));
				}
			}
		}
		
		return Integer.parseInt(result);
	}

	private String[] solvedSignalsPerDigit (String signalPart) {
		String[] keys = new String[10];
		List<String> signals = Arrays.asList(signalPart.split(" ", -1));
		// Digit one
		keys[1] = signals.stream().filter(e -> e.length() == 2).findFirst().get();
		// digit seven
		keys[7] = signals.stream().filter(e -> e.length() == 3).findFirst().get();
		// digit four
		keys[4] = signals.stream().filter(e -> e.length() == 4).findFirst().get();
		// digit eigth
		keys[8] = signals.stream().filter(e -> e.length() == 7).findFirst().get();
		
		// topline is not in 1, but is in 7
		//String topLine = getTopLine(keys[1], keys[7]);
		// top left and middle only are not in 1 but are in 4
		String topLeftAndMiddle = getTopLeftAndMiddle(keys[1], keys[4]);

		// 5 löytyy niin että length = 5 ja löytyy nelosen "omat"
		keys[5] = signals.stream().filter(e -> e.length() == 5 && containsAll(e, getTopLeftAndMiddle(keys[1], keys[4]))).findFirst().get();

		// ysi löytyy niin, että löytyy topleft and middle, löytyy seiska ja length 6
		keys[9] = signals.stream().filter(e -> e.length() == 6 && containsAll(e, keys[7] + topLeftAndMiddle)).findFirst().get();

		// kolmonen löytyy niin, että length = 5 ja löytyy samat kuin ykkösessä
		keys[3] = signals.stream().filter(e -> e.length() == 5 && containsAll(e, keys[1])).findFirst().get();

		// kakkonen löytyy niin, että length = 5 eikä ole kolmenen tai viitonen
		keys[2] = signals.stream().filter(e -> e.length() == 5 && !e.equalsIgnoreCase(keys[5]) && !e.equalsIgnoreCase(keys[3])).findFirst().get();
		
		// kuutonen löytyy niin, että length = 6 eikä löydy ykköstä
		keys[6] = signals.stream().filter(e -> e.length() == 6 && !containsAll(e, keys[1])).findFirst().get();

		// nolla löytyy niin, että length =6 eikä ole ysi eikä kuutonen;
		keys[0] = signals.stream().filter(e -> e.length() == 6 && !e.equalsIgnoreCase(keys[9]) && !e.equalsIgnoreCase(keys[6])).findFirst().get();
		
		// length of 5 -> 2, 3, 5,
		// length of 6 -> 6, 9, 0
		
		return keys;
	}
	
	private boolean containsAll(String s, String requiredChars) {
		boolean containsAll = true;
		for(int i = 0 ; i < requiredChars.length() ; i ++) {
			String subString = requiredChars.substring(i, i+1);
			if(! s.contains(subString)) {
				return false;
			}
		}
		return containsAll;
	}

	private String getTopLeftAndMiddle(String one, String four) {
		String topLeft = "";
		for(int i = 0; i < four.length(); i++) {
			String subString = four.substring(i, i+1);
			if(!one.contains(subString) ) {
				topLeft = topLeft.concat(subString);
			}
		}
		return topLeft;
	}
	
	private String getOutputPart(String fullLine) {
		return fullLine.substring(fullLine.indexOf("|") +2, fullLine.length());
	}

	private String getSignalPart(String fullLine) {
		return fullLine.substring(0, fullLine.indexOf("|") -1);
	}
	
}
