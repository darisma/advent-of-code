package advent2022;

import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day2 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2022input/day2.txt";
	private static final String OPPONENT_ROCK = "A";
	private static final String OPPONENT_PAPER = "B";
	private static final String OPPONENT_SCISSORS = "C";
	private static final String OWN_ROCK = "X";
	private static final String OWN_PAPER = "Y";
	private static final String OWN_SCISSORS = "Z";
	private static final String LOSE = "X";
	private static final String DRAW = "Y";
	private static final String WIN = "Z";
	
	static List<String[]> rivit = AIR.getStringStream(FILENAME)
			.map(s -> s.split(" "))
			.collect(Collectors.toList());
	

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + solveAdventA());
		System.out.println("Answer to B is: " + solveAdventB());

	}
	
	private static int solveAdventA() {

		int totalScore = 0;
		
		for (String[] kps : rivit) {
			totalScore += addResult(kps);
			totalScore += addOwn(kps[1]);
		}
		
        return totalScore;
	}
	
	private static int addOwn(String ownSign) {
		if(ownSign.equals(OWN_PAPER)) {
			return 2;
		}
		if(ownSign.equals(OWN_ROCK)) {
			return 1;
		}
		return 3;
	}

	private static int addResult(String[] kps) {
		if(win(kps)) {
			return 6;
		}
		if(draw(kps)) {
			return 3;
		}
		return 0;
	}

	private static boolean draw(String[] kps) {
		if(kps[0].equals(OPPONENT_ROCK) && kps[1].equals(OWN_ROCK)) {
			return true;
		}
		if(kps[0].equals(OPPONENT_SCISSORS) && kps[1].equals(OWN_SCISSORS)) {
			return true;
		}
		if(kps[0].equals(OPPONENT_PAPER) && kps[1].equals(OWN_PAPER)) {
			return true;
		}
		return false;
	}

	private static boolean win(String[] kps) {
		if(kps[0].equals(OPPONENT_ROCK) && kps[1].equals(OWN_PAPER)) {
			return true;
		}
		if(kps[0].equals(OPPONENT_SCISSORS) && kps[1].equals(OWN_ROCK)) {
			return true;
		}
		if(kps[0].equals(OPPONENT_PAPER) && kps[1].equals(OWN_SCISSORS)) {
			return true;
		}
		return false;
	}

	private static int solveAdventB()  {

		int totalScore = 0;
		
		for (String[] kps : rivit) {
			totalScore += addResult(kps[1]);
			totalScore += addOwn(whatDidIPlay(kps));
		}
		
        return totalScore;
	}

	private static String whatDidIPlay(String[] kps) {
		if(kps[1].equals(LOSE)) {
			if(kps[0].equals(OPPONENT_PAPER)) {
				return OWN_ROCK;
			}
			if(kps[0].equals(OPPONENT_SCISSORS)) {
				return OWN_PAPER;
			}
			return OWN_SCISSORS;
		}
		if(kps[1].equals(DRAW)) {
			if(kps[0].equals(OPPONENT_PAPER)) {
				return OWN_PAPER;
			}
			if(kps[0].equals(OPPONENT_ROCK)) {
				return OWN_ROCK;
			}
			return OWN_SCISSORS;
		}
		if(kps[0].equals(OPPONENT_PAPER)) {
			return OWN_SCISSORS;
		}
		if(kps[0].equals(OPPONENT_ROCK)) {
			return OWN_PAPER;
		}
		return OWN_ROCK;
	}

	private static int addResult(String string) {
		if(string.equals(LOSE)) {
			return 0;
		}
		if(string.equals(WIN)) {
			return 6;
		}
		return 3;
	}

}
