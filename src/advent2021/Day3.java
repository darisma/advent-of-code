package advent2021;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day3 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day3.txt";
	
	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());
	
	static final int ROW_LENGTH = INPUT_DATA.get(0).length();
	
	static final List<Integer> DIAGNOSTIC_DATA = INPUT_DATA.stream().map(e -> Integer.parseInt(e, 2)).collect(Collectors.toList());

	
	public static void main (String[] args) {
		System.out.println("Answer to A is: " + solveAdventA());
		System.out.println("Answer to B is: " + solveAdventB());
		System.out.println("Another answer to B is: " + solveAdventBRecursive());
	}
	
	private static int solveAdventA() {
		
		BitSet bits = new BitSet();

		for(int bitIndex = 0 ; bitIndex < ROW_LENGTH ; bitIndex++) {
			if(getBitForIndex(DIAGNOSTIC_DATA, bitIndex) > DIAGNOSTIC_DATA.size() / 2) {
				bits.set(bitIndex);
			}
		}
		
		int gamma = convert(bits);
		
		bits.xor(getMask());
		
		int epsilon = convert(bits);
		
		return gamma * epsilon;
	}

	private static int solveAdventB()  {
		
		List<Integer> tempList = new ArrayList<>(DIAGNOSTIC_DATA);
		
		// oxygen
		for(int bitIndex = ROW_LENGTH - 1 ; bitIndex >= 0 ; bitIndex--) {
			
			int bitCount = getBitForIndex(tempList, bitIndex);

			if( bitCount >= tempList.size() / 2) {
				tempList = getListOfMatchingData(tempList, bitIndex, 1);
				
			} else {
				tempList = getListOfMatchingData(tempList, bitIndex, 0);
			}
			
			if (tempList.size() == 1) {
				break;
			}
		}
		
		int oxygenValue = tempList.get(0);
		
		// start with a full list
		tempList = new ArrayList<>(DIAGNOSTIC_DATA);
	
		// CO2
		for(int bitIndex = ROW_LENGTH - 1 ; bitIndex >= 0 ; bitIndex--) {
			
			int bitCount = getBitForIndex(tempList, bitIndex);
			
			if( bitCount < tempList.size() / 2) {
				tempList = getListOfMatchingData(tempList, bitIndex, 1);
			} else {
				tempList = getListOfMatchingData(tempList, bitIndex, 0);
			}
			if (tempList.size() == 1)
				break;
		}
		
		int co2Value = tempList.get(0);
		
        return oxygenValue * co2Value;
	}

	private static int solveAdventBRecursive() {
		
		int oxyValue = getValue(DIAGNOSTIC_DATA, ROW_LENGTH - 1, 1);
		int co2Value = getValue(DIAGNOSTIC_DATA, ROW_LENGTH - 1, 0);
		return oxyValue * co2Value;
	}
	
	private static int getValue(List<Integer> diagnosticData, int bitIndex, int mostBits) {
		int bitCount = getBitForIndex(diagnosticData, bitIndex);
		int searchBits = 1;
		
		// search for 0 bits if oxy && low count of 1's or CO2 and high count
		if ( ( bitCount < (float) diagnosticData.size() / 2 && mostBits == 1) ||
				(bitCount >= (float) diagnosticData.size() / 2 && mostBits == 0)
				)  {
			 searchBits = 0;
		}
		
		List<Integer> tempList = getListOfMatchingData(diagnosticData, bitIndex, searchBits);
	
		if (tempList.size() == 1) {
			return tempList.get(0);
		}
		
		return getValue(tempList, bitIndex - 1, mostBits);
	}

	private static List<Integer> getListOfMatchingData(List<Integer> tempList, int bitIndex, int i) {
		return tempList.stream().filter(e -> getBit(e, bitIndex) == i).collect(Collectors.toList());
	}

	private static int getBitForIndex(List<Integer> diagnosticData, int bitIndex) {
		return diagnosticData.stream().map(e -> getBit(e, bitIndex)).reduce(0, (subtotal, element) -> subtotal + element);
	}
	
	private static int convert(BitSet bits) {
		    int value = 0;
		    for (int i = 0; i < bits.length(); ++i) {
		      value += bits.get(i) ? (1 << i) : 0;
		    }
		    return value;
		  }
	
	private static int getBit(int n, int k) {
	    return (n >> k) & 1;
	}
	
	private static BitSet getMask() {
		BitSet mask = new BitSet();
		mask.set(0, ROW_LENGTH);
		return mask;
	}
}
