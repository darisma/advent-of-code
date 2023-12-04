package advent2023;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day3 {

    static AdventInputReader ir = new AdventInputReader();
    static final String FILENAME = "2023input/day3.txt";
    
    static final List<String> INPUT_DATA = ir.getStringStream(FILENAME).collect(Collectors.toList());
    
    public static void main (String[] args) {
		
        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }
    
    private static int solveAdvent1a() {
    	int result = 0;
    	
    	for(int i = 0; i < INPUT_DATA.size(); i++) {
    		result = result + sumOfPartNumbers(INPUT_DATA.get(i), i);
    	}
    	
    	return result;
    }

	private static int solveAdvent1b()  {
		int result = 0;
		
		for(int i = 0; i < INPUT_DATA.size(); i++) {
			result = result + sumOfGearRatios(INPUT_DATA.get(i), i);
		}
		
		return result;
    }
    
    private static int sumOfGearRatios(String s, int rowNumber) {
		int result = 0;
		
		for(Integer gear: getGearIndices(s)) {
			result = result + getGearPoint(getPreviousLine(rowNumber), getNextLine(rowNumber), s, rowNumber, gear);
		}
		
		return result;
	}

	private static Optional<String> getNextLine(int i) {
		if(i < INPUT_DATA.size() - 1) {
			return Optional.of(INPUT_DATA.get(i + 1));
		}

		return Optional.empty();
	}

	private static Optional<String> getPreviousLine(int i) {
		if(i > 0) {
			return Optional.of(INPUT_DATA.get(i - 1));
		}

		return Optional.empty();
	}

	private static int getGearPoint(Optional<String> previous, Optional<String> next, String current, int rowNumber, Integer gear) {

		Optional<Integer> onLeft = getGearNumberList(current, gear - 1);
		
		Optional<Integer> onRight = getGearNumberList(current, gear + 1);
		List<Integer> onTop = countAmountOfNumbers(previous, gear);
		List<Integer> onBottom = countAmountOfNumbers(next, gear);
		
		List<Integer> all = new ArrayList<>();
		if(onLeft.isPresent()) {
			all.add(onLeft.get());
		}
		if(onRight.isPresent()) {
			all.add(onRight.get());
		}
		all.addAll(onTop);
		all.addAll(onBottom);

		if (all.size() == 2){
			System.out.println("returning " + all.get(0) * all.get(1)+ " for gear: " + gear + " on row " + current);
			return all.get(0) * all.get(1);
		}
		
		return 0;
	}

	private static Optional<Integer> getGearNumberList(String s, int index) {
		 
		if(Character.isDigit(s.charAt(index))) {
			int startIndex = index;
			while(startIndex > 0 && Character.isDigit(s.charAt(startIndex - 1))) {
				startIndex = startIndex - 1;
			}
			int endIndex = index;
			while(endIndex < s.length()  && Character.isDigit(s.charAt(endIndex))) {				
				endIndex = endIndex + 1;
			}
			return Optional.of(Integer.parseInt(s.substring(startIndex, endIndex)));
		}
		return Optional.empty();
	}

	private static List<Integer> countAmountOfNumbers(Optional<String> string, int gearIndex) {
		if(string.isPresent() && Character.isDigit(string.get().charAt(gearIndex - 1)) 
				&& Character.isDigit(string.get().charAt(gearIndex + 1)) && !Character.isDigit(string.get().charAt(gearIndex))) {
			Optional<Integer> number = getGearNumberList(string.get(), gearIndex - 1);
			Optional<Integer> anotherNumber = getGearNumberList(string.get(), gearIndex + 1);
			return List.of(number.get(), anotherNumber.get());
		}

		if(string.isPresent() && Character.isDigit(string.get().charAt(gearIndex - 1))){
			return List.of(getGearNumberList(string.get(), gearIndex - 1).get());
		}
	    
		if(string.isPresent() && Character.isDigit(string.get().charAt(gearIndex))) {
			return List.of(getGearNumberList(string.get(), gearIndex).get());
		}
	    
	    if(string.isPresent() && Character.isDigit(string.get().charAt(gearIndex + 1))) {
			return List.of(getGearNumberList(string.get(), gearIndex + 1).get());
		}
	    
		return Collections.emptyList();
	}

	private static int sumOfPartNumbers(String s, int rowNumber) {

		int result = 0;
		
		char[] chars = s.toCharArray();
		
		boolean readingNumber = false;
		int numberStartIndex = 0;
		int numberLength = 0;
		for(int i = 0; i < chars.length ; i++) {
			if(Character.isDigit(chars[i]) && !readingNumber){
				numberLength = 1;
				readingNumber = true;
				numberStartIndex = i;
			}else if(Character.isDigit(chars[i]) && readingNumber && i < chars.length - 1){
				numberLength++;
			}else if(Character.isDigit(chars[i]) && readingNumber && i == chars.length - 1){
				numberLength++;
				result = result + addPartNumber(s, numberStartIndex, numberLength, rowNumber);
				readingNumber = false;
				numberLength = 0;
			}else if(!Character.isDigit(chars[i]) && readingNumber) {
				result = result + addPartNumber(s, numberStartIndex, numberLength, rowNumber);
				numberLength = 0;
				readingNumber = false;
			}else if(!Character.isDigit(chars[i]) && !readingNumber) {
				numberLength = 0;
				readingNumber = false;
			}
		}
		return result;
	}

	private static int addPartNumber(String s, int numberStartIndex, int numberLength, int rowNumber) {
		int number = Integer.parseInt(s.substring(numberStartIndex, numberStartIndex + numberLength));
		if(isNumberPartNumber(s, numberStartIndex, numberLength, rowNumber)) {
			return number;
		}
		return 0;
	}

	private static boolean isNumberPartNumber(String s, int numberStartIndex, int numberLength, int rowNumber) {
		boolean symbolOnLeft = numberStartIndex > 0 && isSymbol(s.toCharArray()[numberStartIndex - 1]);
		boolean symbolOnRight = numberStartIndex + numberLength < s.length() && isSymbol(s.toCharArray()[numberStartIndex + numberLength]);
		boolean symbolOnPreviousRow = rowNumber > 0 && testPreviousRow(rowNumber, numberStartIndex, numberLength);
		boolean symbolOnNextRow = rowNumber < INPUT_DATA.size() - 1 && testNextRow(rowNumber, numberStartIndex, numberLength);
 		return symbolOnLeft || symbolOnRight || symbolOnPreviousRow || symbolOnNextRow;
	}

	private static boolean testPreviousRow(int rowNumber, int numberStartIndex, int numberLength) {
		int start = 0;
		if(numberStartIndex > 0 ) {
			start = numberStartIndex - 1;
		}
		
		int end = INPUT_DATA.get(rowNumber - 1).length();
		if(numberStartIndex + numberLength < end) {
			end = numberStartIndex + numberLength + 1;
		}
		
		for(char c : INPUT_DATA.get(rowNumber - 1).substring(start, end).toCharArray()) {
			if(isSymbol(c)) {
				return true;
			}
		}
		
		return false;
	}

	private static boolean testNextRow(int rowNumber, int numberStartIndex, int numberLength) {
		int start = 0;
		if(numberStartIndex > 0 ) {
			start = numberStartIndex - 1;
		}
		
		int end = INPUT_DATA.get(rowNumber + 1).length();
		if(numberStartIndex + numberLength < end) {
			end = numberStartIndex + numberLength + 1;
		}
		
		for(char c : INPUT_DATA.get(rowNumber + 1).substring(start, end).toCharArray()) {
			if(isSymbol(c)) {
				return true;
			}
		}
		
		return false;
	}

	private static boolean isSymbol(char c) {
    	return !Character.isDigit(c) && c != '.';
    }

	private static List<Integer> getGearIndices(String s) {
		List<Integer> digits = new ArrayList<>();
		
		for(int i = 0 ; i < s.length() ; i++) {
			if(s.charAt(i) == '*') {
				digits.add(i);
			}
		}
		return digits;
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        int i = Integer.parseInt(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
}
