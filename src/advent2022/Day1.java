package advent2022;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day1 {

	static AdventInputReader ir = new AdventInputReader();
	static final String FILENAME = "2022input/day1.txt";
	
	static List<String> calorieReadings = ir.getStringStream(FILENAME).collect(Collectors.toList());
	
	public static void main (String[] args) {
		System.out.println("Answer to 1a is: " + solveAdvent1a());
		System.out.println("Answer to 1b is: " + solveAdvent1b());
	}
	
	private static int solveAdvent1a() {

		List<Integer> elfCalories = new ArrayList<>();
		
		int calories = 0;
		for(String s : calorieReadings) {
			if(s.length() > 0) {
				calories += Integer.parseInt(s);
			}
			if(s.length() == 0) {
				elfCalories.add(calories);
				calories = 0;
			}
		}
		elfCalories.add(calories);
		
        return elfCalories.stream().mapToInt(e -> e).max().getAsInt();
	}
	
	private static int solveAdvent1b()  {

		List<Integer> elfCalories = new ArrayList<>();
		
		int calories = 0;
		for(String s : calorieReadings) {
			if(s.length() > 0) {
				calories += Integer.parseInt(s);
			}
			if(s.length() == 0) {
				elfCalories.add(calories);
				calories = 0;
			}
		}
		elfCalories.add(calories);
		
        return elfCalories.stream().sorted(Comparator.reverseOrder()).limit(3).mapToInt(e -> e).sum();
	}
}
