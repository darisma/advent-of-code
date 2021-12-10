package advent2021;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import utils.AdventInputReader;

public class Day10 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day10.txt";

	private static final Character ALSU = '(';
	private static final Character LOSU = ')';
	private static final Character ALHA = '[';
	private static final Character LOHA = ']';
	private static final Character ALAA = '{';
	private static final Character LOAA = '}';
	private static final Character ALVA = '<';
	private static final Character LOVA = '>';	
	
	private static final List<Character> OPENING_CHARS = Arrays.asList(ALSU, ALHA, ALAA, ALVA);
	private static final List<Character> CLOSING_CHARS = Arrays.asList(LOSU, LOHA, LOAA, LOVA);
	
	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day10().solveAdventA());
		System.out.println("Answer to B is: " + new Day10().solveAdventB());
	}

	private long solveAdventA() {
		
		return INPUT_DATA.stream().mapToLong(this::getErrorPoints).reduce(0, (a, b) -> a + b);
		
	}
	
	private long solveAdventB() {
		
		List<String> inComplete = INPUT_DATA.stream().filter(e -> getErrorPoints(e) == 0).collect(Collectors.toList());
		
		List<Long> completionPoints = inComplete.stream().map(this::getCompletionPoints).sorted().collect(Collectors.toList());
		
		return completionPoints.get(completionPoints.size()/2);
	}

	private Long getCompletionPoints(String f) {

		Deque<Character> stack = new ArrayDeque<>();
		
		f.chars().forEach(g -> processIncomplete(g, stack));
		
		return getCompletionScore(stack);
	}

	private long getCompletionScore(Deque<Character> stack) {
		long score = 0;
		
		while(!stack.isEmpty()) {
			score = score * 5 + getScoreFor(stack.pop());
		}
		return score;
	}

	private long getScoreFor(Character fromStack) {
		if(ALSU.equals(fromStack)){
			return 1;
		} else if(ALHA.equals(fromStack)) {
			return 2;
		}else if(ALAA.equals(fromStack)) {
			return 3;
		}else if(ALVA.equals(fromStack)) {
			return 4;
		}
		return 0;
	}

	private void processIncomplete(int g, Deque<Character> stack) {
		char c = (char)g;
		
		if(CLOSING_CHARS.contains(c)) {
			stack.pop();
		}
		
		if(OPENING_CHARS.contains(c)) {
			stack.push(c);
		}	
	}

	private long getErrorPoints(String e) {

		Deque<Character> stack = new ArrayDeque<>();
		
		OptionalInt errorCharacter = e.chars().filter(f -> getCorruptChar(f, stack)).findFirst();
		
		if(errorCharacter.isPresent()) {
			return getPoints((char)errorCharacter.getAsInt());
		}
		
		return 0;
	}

	private boolean getCorruptChar(int f, Deque<Character> stack) {
		char c = (char)f;
		if(CLOSING_CHARS.contains(c)) {
			if(stack.isEmpty()) {
				return false;
			}
			Character fromStack = stack.pop();
			
			if(doesNotMatchExpectedOpening(c, fromStack)) {
				return true;
			}
		}
		
		if(OPENING_CHARS.contains(c)) {
			stack.push(c);
		}

		return false;
	}

	private boolean doesNotMatchExpectedOpening(char c, Character fromStack) {
		if(LOSU.equals(c) && !fromStack.equals(ALSU)) {
				return true;
		}
		if(LOHA.equals(c) && !fromStack.equals(ALHA)) {
			return true;
		}
		if(LOAA.equals(c) && !fromStack.equals(ALAA)) {
			return true;
		}
		if(LOVA.equals(c) && !fromStack.equals(ALVA)) {
			return true;
		}
	
		return false;
	}

	private int getPoints(Character fromStack) {
		if(LOSU.equals(fromStack)){
			return 3;
		} else if(LOHA.equals(fromStack)) {
			return 57;
		}else if(LOAA.equals(fromStack)) {
			return 1197;
		}else if(LOVA.equals(fromStack)) {
			return 25137;
		}
		return 0;
	}

}
