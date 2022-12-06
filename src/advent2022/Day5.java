package advent2022;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day5 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2022input/day5.txt";
	private static final String MOVE_DELIMITER = " ";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day5().solveAdventA());
		System.out.println("Answer to B is: " + new Day5().solveAdventB());

	}

	private String solveAdventA() {
		HashMap<Integer, Stack<String>> start = createStartStacks(INPUT_DATA.subList(0, 8));

		for(String movement : INPUT_DATA.subList(10, INPUT_DATA.size())) {
			makeMoves(start, movement);
		}
		
		String result = getResult(start);
		
		return result;
	}

	private void makeMoves(HashMap<Integer, Stack<String>> start, String movement) {
		
		String[] moveString = movement.split(MOVE_DELIMITER);
		int amountOfmovedCrates = Integer.parseInt(moveString[1]);
		int moveFrom = Integer.parseInt(moveString[3]);
		int moveTo = Integer.parseInt(moveString[5]);
		for (int i = 0; i < amountOfmovedCrates; i++) {
			start.get(moveTo).push(start.get(moveFrom).pop());
		}
	}

	
	private void makeMoves9001(HashMap<Integer, Stack<String>> start, String movement) {
		
		String[] moveString = movement.split(MOVE_DELIMITER);
		int amountOfmovedCrates = Integer.parseInt(moveString[1]);
		int moveFrom = Integer.parseInt(moveString[3]);
		int moveTo = Integer.parseInt(moveString[5]);
		Stack<String> tempStack = new Stack<>();
		for (int i = 0; i < amountOfmovedCrates; i++) {
			tempStack.push(start.get(moveFrom).pop());
		}
		for (int i = 0; i < amountOfmovedCrates; i++) {
			start.get(moveTo).push(tempStack.pop());
		}
	}
	private String getResult(HashMap<Integer, Stack<String>> start) {
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i < 10 ; i++) {
			sb.append(start.get(Integer.valueOf(i)).pop());
		}
		return sb.toString();
	}

	private HashMap<Integer, Stack<String>> createStartStacks(List<String> subList) {
		HashMap<Integer, Stack<String>> stacks = new HashMap<>();
		
		for(String s : subList) {
			for(int i = 1; i < 10; i++) {
				String value = s.substring(i * 4 - 3 , i * 4 - 2 );
				if( value.trim().length() > 0) {
					if(stacks.get(Integer.valueOf(i)) == null) {
						stacks.put(Integer.valueOf(i), new Stack<String>());
					}
					stacks.get(Integer.valueOf(i)).push(value);
				}
			}
		}
		for (int i = 1; i < 10 ; i++) {
			stacks.put(Integer.valueOf(i), reverseStack(stacks.get(Integer.valueOf(i))));
		}
		return stacks;
	}

	private Stack<String> reverseStack(Stack<String> stack) {
		Stack<String> reversedStack = new Stack<>();
		while(!stack.isEmpty()) {
			reversedStack.push(stack.pop());
		}
		return reversedStack;
	}

	private String solveAdventB() {
		HashMap<Integer, Stack<String>> start = createStartStacks(INPUT_DATA.subList(0, 8));

		for(String movement : INPUT_DATA.subList(10, INPUT_DATA.size())) {
			makeMoves9001(start, movement);
		}
		
		return getResult(start);
	}
}
