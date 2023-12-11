package advent2023;

import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day10 {

	static AdventInputReader ir = new AdventInputReader();
	static final String FILENAME = "2023input/day10.txt";

	static List<String> INPUT_DATA = ir.getStringStream(FILENAME).collect(Collectors.toList());
	static char[][] map = generateMap( INPUT_DATA );
	
	private static final String EAST = "E";
	private static final String WEST = "W";
	private static final String NORTH = "N";
	private static final String SOUTH = "S";
	
	private static final char NS = '|';
	private static final char WE = '-';
	private static final char NE = 'L';
	private static final char NW = 'J';
	private static final char SE = 'F';
	private static final char SW = '7';
	private static final char START = 'S';
	
	static int leveys = INPUT_DATA.get(0).length();
	static int korkeus = INPUT_DATA.size();
	
	public static void main (String[] args) {
		System.out.println("Answer to 1a is: " + solveAdvent1a());
		System.out.println("Answer to 1b is: " + solveAdvent1b());
	}

    private static long solveAdvent1a() {

		// find starting position
		int startingX = 0;
		int startingY = 0;
		
		for(int i = 0; i < leveys; i ++) {
			for(int j = 0; j < korkeus; j ++) {
				if(map[i][j] == START) {
					startingX = j;
					startingY = i;
				}
			}
		}

		PipeMove move = new PipeMove(startingX, startingY, null);
		// follow pipe until back in same position
		int counter = 0;
		do {
			move = moveAlong(move);
			counter ++;
		}
		while(move.x != startingX || move.y != startingY);
		
    	return counter / 2;
	}

	private static long solveAdvent1b() {
    	int predictionSum = 0;
    	return predictionSum;
	}

	private static PipeMove moveAlong(PipeMove move) {
		switch (map[move.y][move.x]) {
		case NS:
				if(move.direction.equalsIgnoreCase(NORTH)) {
					return new PipeMove(move.x, move.y - 1, NORTH);
				}
				return new PipeMove(move.x, move.y + 1, SOUTH);
		case WE:
			if(move.direction.equalsIgnoreCase(EAST)) {
				return new PipeMove(move.x + 1, move.y, EAST);
			}
			return new PipeMove(move.x - 1, move.y, WEST);
		case NW:
			if(move.direction.equalsIgnoreCase(EAST)) {
				return new PipeMove(move.x, move.y - 1, NORTH);
			}
			return new PipeMove(move.x - 1, move.y, WEST);
		case NE:
			if(move.direction.equalsIgnoreCase(WEST)) {
				return new PipeMove(move.x, move.y - 1, NORTH);
			}
			return new PipeMove(move.x + 1, move.y, EAST);
		case SW:
			if(move.direction.equalsIgnoreCase(EAST)) {
				return new PipeMove(move.x, move.y + 1, SOUTH);
			}
			return new PipeMove(move.x - 1, move.y, WEST);
		case SE:
			if(move.direction.equalsIgnoreCase(WEST)) {
				return new PipeMove(move.x, move.y + 1, SOUTH);
			}
			return new PipeMove(move.x + 1, move.y, EAST);
		case START:
			return getStartMove(move);
		}

		return null;
	}

	private static PipeMove getStartMove(PipeMove position) {
		char north = getCharAtDirection(position, NORTH);
		if(north == SW || north == NS || north == SE) {
			return new PipeMove(position.x, position.y - 1, NORTH);
		}
		char west = getCharAtDirection(position, WEST);
		if(west == WE || west == NE || west == SE) {
			return new PipeMove(position.x - 1, position.y, WEST);
		}
		char east = getCharAtDirection(position, EAST);
		if(east == WE || east == NW || east == SW) {
			return new PipeMove(position.x + 1, position.y, EAST);		
		}
		return new PipeMove(position.x, position.y + 1, SOUTH);
	}

	private static char getCharAtDirection(PipeMove position, String direction) {
		switch (direction) {
		case SOUTH:
			return map[position.y + 1][position.x];
		case NORTH:
			return map[position.y - 1][position.x];
		case EAST:
			return map[position.y][position.x + 1];
		case WEST:
			return map[position.y][position.x - 1];
		}
		return Character.MIN_VALUE;
	}

	private static char[][] generateMap(List<String> rivit) {
		char[][] kartta = new char[rivit.size()][rivit.get(0).length()];
		for(int i = 0; i < rivit.size(); i++) {
			for(int j = 0; j < rivit.get(0).length(); j++) {
				kartta[i][j] = rivit.get(i).charAt(j);
			}
		}
		return kartta;
	}
	
	static class PipeMove{
		
		int x;
		int y;
		String direction;
		
		public PipeMove(int x, int y, String dir) {
			this.x = x;
			this.y = y;
			direction = dir;
		}
	}
}