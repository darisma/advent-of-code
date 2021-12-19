package advent2021;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day16 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day16.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day16().solveAdventA());
		System.out.println("Answer to B is: " + new Day16().solveAdventB());
	}

	private long solveAdventA() {
		return sumVersionNumbers(hexToBinary(INPUT_DATA.get(0)).trim());

	}

	private long sumVersionNumbers(String bitString) {

		long sum = 0;
		for (int i = 0; i < bitString.length() ; ) {
			if(bitString.substring(i).chars().allMatch(e -> e == '0')) {
				break;
			}
			// version is first
			int version = binToInt(bitString.substring(i, i + 3));
			System.out.println("Found version: " + version);
			sum = sum + version;

			// type follows
			int type = binToInt(bitString.substring(i + 3, i + 6));
			System.out.println("Type is: " + type);
			i = i + 6;

			if(type == 4) {
				// Literal value
				// skip as long as there is the "final part of the number" (starts with 0)
				// in chunks of 5
				char testChar = '1';
				do {
					System.out.println("Doing while");
					testChar = bitString.charAt(i);
					i = i + 5;
				}while (testChar != '0');
			}else {
				// Operator packet
				// first bit describes the lengthType 11/ 15 bits to read
				int lengthType = 15;
				boolean lengthInBitsMode = bitString.charAt(i) == '1'; // i
				if( lengthInBitsMode) {
					lengthType = 11;
				}

				i = i + 1; // i was the mode bit
				int subLength = binToInt(bitString.substring(i, i + lengthType)); 
				i = i + lengthType;

				if(!lengthInBitsMode) {
					sum = sum + sumVersionNumbers(bitString.substring(i, i + subLength));
					i = i + subLength;
				}
			}
		}

		return sum;

	}

	private long solveAdventB() {
		return 1;
	}

	int binToInt(String s) {
		return Integer.parseInt(new BigInteger(s, 2).toString(10));
	}

	public static String hexToBinary(String hex) {
		return new BigInteger(hex, 16).toString(2);
	}

}