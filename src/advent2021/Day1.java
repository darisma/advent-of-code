package advent2021;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day1 {

	static AdventInputReader ir = new AdventInputReader();
	static final String FILENAME = "2021input/day1.txt";
	
	static List<Integer> sonarReadings = ir.getStringStream(FILENAME)
			.map(Integer::parseInt)
			.collect(Collectors.toList());
	
	public static void main (String[] args) {
		System.out.println("Answer to 1a is: " + solveAdvent1a());
		System.out.println("Answer to 1b is: " + solveAdvent1b());
		System.out.println("Another answer to 1a is: " + solveAdvent1aX());
		System.out.println("Another answer to 1b is: " + solveAdvent1bX());
	}
	
	private static int solveAdvent1a() {

		Integer previous = null;
		int result = 0;
		
		for(Integer i:sonarReadings) {
			if(previous != null && i > previous) {
				result++;
			}
			previous = i;
		}
		
        return result;
	}
	
	private static int solveAdvent1b()  {

		List<Integer> measurementWindows = new ArrayList<>();
		for(int i = 0; i < sonarReadings.size(); i++) {
			measurementWindows.add(sonarReadings.get(i));
			if(measurementWindows.size() > 1) {
				measurementWindows.set(i - 1, measurementWindows.get(i - 1) + sonarReadings.get(i));
			}
			if(measurementWindows.size() > 2) {
				measurementWindows.set(i - 2, measurementWindows.get(i - 2) + sonarReadings.get(i));
			}
			
		}
		
		Integer previous = null;
		int result = 0;
		
		for(Integer i:measurementWindows) {
			if(previous != null && i > previous) {
				result++;
			}
			previous = i;
		}
		
        return result;
	}
	
	private static int solveAdvent1aX() {
		
		int [] ints = sonarReadings.stream().mapToInt(Integer::intValue).toArray();
		
		int result = 0;
		
		for (int i = 1 ; i < ints.length ; i++ ) {
			if(ints[i] > ints[i - 1]) {
				result ++;
			}
		}
		
        return result;
	}
	
	private static int solveAdvent1bX()  {

		int [] ints = sonarReadings.stream().mapToInt(Integer::intValue).toArray();
		
		int [] groups = new int[ints.length];
		
		// fill array of measurement window values
		for(int i = 0; i < groups.length - 2 ; i++) {
			groups[i] = ints[i] + ints[i + 1] + ints[i + 2];
		}
		
		int result = 0;
		
		for (int i = 1 ; i < groups.length ; i++ ) {
			if(groups[i] > groups[i - 1]) {
				result ++;
			}
		}
		
        return result;
	}
}
