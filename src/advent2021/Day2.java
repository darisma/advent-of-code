package advent2021;

import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day2 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day2.txt";
	private static final String DOWN = "down";
	private static final String UP = "up";
	private static final String FORWARD = "forward";
	
	static List<String[]> rivit = AIR.getStringStream(FILENAME)
			.map(s -> s.split(" "))
			.collect(Collectors.toList());
	
	static List<Movement> movements = AIR.getStringStream(FILENAME)
			.map(s -> new Movement(s.split(" ")[0], s.split(" ")[1]))
			.collect(Collectors.toList());
	
	public static void main (String[] args) {
		System.out.println("Answer to A is: " + solveAdventA());
		System.out.println("Answer to B is: " + solveAdventB());
		System.out.println("Answer to A2 is: " + solveAdventA2());
		System.out.println("Answer to B2 is: " + solveAdventB2());
	}
	
	private static int solveAdventA() {

		int horizontal = 0;
		int depth = 0;
		
		for(String[] movement : rivit) {
			switch (movement[0]) 
			{
				case DOWN:
					depth = depth + Integer.parseInt(movement[1]);
					break;
				case UP:
					depth = depth - Integer.parseInt(movement[1]);
					break;
				case FORWARD:
					horizontal = horizontal + Integer.parseInt(movement[1]);
					break;
				default:
					break;
			}
		}
		
        return horizontal * depth;
	}
	
	private static int solveAdventB()  {

		int horizontal = 0;
		int depth = 0;
		int aim = 0;
		
		for(String[] movement : rivit) {
			switch (movement[0]) 
			{
				case DOWN:
					aim = aim + Integer.parseInt(movement[1]);
					break;
				case UP:
					aim = aim - Integer.parseInt(movement[1]);
					break;
				case FORWARD:
					horizontal = horizontal + Integer.parseInt(movement[1]);
					depth = depth + aim * Integer.parseInt(movement[1]);
					break;
				default:
					break;
			}
		}
		
        return horizontal * depth;
	}

	private static int solveAdventA2() {

		int horizontal = 0;
		int depth = 0;
		
		for(Movement m : movements) {
			switch (m.getCommand()) 
			{
				case DOWN:
					depth = depth + m.getValue();
					break;
				case UP:
					depth = depth - m.getValue();
					break;
				case FORWARD:
					horizontal = horizontal + m.getValue();
					break;
				default:
					break;
			}
		}
		
        return horizontal * depth;
	}
	
	private static int solveAdventB2()  {

		int horizontal = 0;
		int depth = 0;
		int aim = 0;
		
		for(Movement m : movements) {
			switch (m.getCommand()) 
			{
				case DOWN:
					aim = aim + m.getValue();
					break;
				case UP:
					aim = aim - m.getValue();
					break;
				case FORWARD:
					horizontal = horizontal + m.getValue();
					depth = depth + aim * m.getValue();
					break;
				default:
					break;
			}
		}
		
        return horizontal * depth;
	}
	
	private static class Movement {
		
		public Movement(String command, String value) {
			this.command = command;
			this.value = Integer.parseInt(value);
		}
		
		private String command;
		private int value;

		public String getCommand() {
			return command;
		}
		public int getValue() {
			return value;
		}
	   
   }
}
