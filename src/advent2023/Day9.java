package advent2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day9 {

	static AdventInputReader ir = new AdventInputReader();
	static final String FILENAME = "2023input/day9.txt";

	static List<String> reportData = ir.getStringStream(FILENAME).collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to 1a is: " + solveAdvent1a());
		System.out.println("Answer to 1b is: " + solveAdvent1b());
	}

    private static long solveAdvent1a() {
    	int predictionSum = 0;
    	for(String reportLine : reportData) {
    		predictionSum += predictNextValue(reportLine);
    	}
    	return predictionSum;
	}

    private static int predictNextValue(String reportLine) {

    	Stack<OasisReadings> stack = new Stack<>();
    	stack.add(new OasisReadings(reportLine));
    	
    	while(stack.peek().getReadings().stream().filter(e -> e != 0).count() != 0) {
    		stack.add(stack.peek().generateNextSequence());
    	}

    	int prediction = 0;
    	
    	while(!stack.isEmpty()) {
    		prediction += stack.pop().getLastReading();
    	}
     	return prediction;
	}

	private static long solveAdvent1b() {
    	int predictionSum = 0;
    	for(String reportLine : reportData) {
    		predictionSum += predictPreviousValue(reportLine);
    	}
    	return predictionSum;
	}

	private static int predictPreviousValue(String reportLine) {
		
    	Stack<OasisReadings> stack = new Stack<>();
    	stack.add(new OasisReadings(reportLine));
    	
    	while(stack.peek().getReadings().stream().filter(e -> e != 0).count() != 0) {
    		stack.add(stack.peek().generateNextSequence());
    	}

    	int prediction = 0;
    	stack.pop();
    	while(!stack.isEmpty()) {
    		prediction = stack.pop().getFirstReading() - prediction;
    	}
    	
    	return prediction;
	}

	static class OasisReadings{
		
		private List<Integer> readings = new ArrayList<>();
		
		public OasisReadings(List<Integer> preparedReadings) {
			this.readings = preparedReadings;
		}
		
		public int getFirstReading() {
			return readings.get(0);
		}

		public int getLastReading() {
			return readings.get(readings.size() - 1);
 		}

		public OasisReadings(String s) {
			StringTokenizer st = new StringTokenizer(s);
			while(st.hasMoreTokens()) {
				readings.add(Integer.parseInt(st.nextToken()));
			}
		}

		public List<Integer> getReadings() {
			return readings;
		}

		public void setReadings(List<Integer> readings) {
			this.readings = readings;
		}
		
		public OasisReadings generateNextSequence() {
			List<Integer> nextSequenceReadings = new ArrayList<>();
			for(int i=0; i < readings.size() - 1; i ++ ) {
				nextSequenceReadings.add(Integer.valueOf(readings.get(i + 1) - readings.get(i)));
			}
			return new OasisReadings(nextSequenceReadings);
		}

		@Override
		public String toString() {
			return "OasisReadings [readings=" + readings + "]";
		}
	}
}