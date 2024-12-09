package advent2024;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day6 {

	static AdventInputReader ir = new AdventInputReader();
	static final String FILENAME = "2024input/day6.txt";

	static final List<String> inputData = ir.getStringStream(FILENAME).collect(Collectors.toList());
	static char[][] map = generateMap(inputData);
	static int leveys = inputData.get(0).length();
	static int korkeus = inputData.size();
	static int startx = 0;
	static int starty = 0;

	private static final String EAST = "E";
	private static final String WEST = "W";
	private static final String NORTH = "N";
	private static final String SOUTH = "S";
	private static final char START = '^';

	public static void main(String[] args) {
		solveAdvent1a();
		solveAdvent1b();
	}

	private static void solveAdvent1a() {

		initStartingPoint();
		String startDirection = NORTH;

		GuardMove move = new GuardMove(startx, starty, startDirection);
		Set<String> visitedPlaces = new LinkedHashSet<>();
		visitedPlaces.add(move.getLocationString());
		while (!move.leftArea) {
			move = getNextMove(move);
			visitedPlaces.add(move.getLocationString());
		}

		System.out.println("Answer to a : " + visitedPlaces.size());
	}

	private static void solveAdvent1b() {
		int result = 0;
		String startDirection = NORTH;

		for (int i = 0; i < korkeus; i++) {
			for (int j = 0; j < leveys; j++) {
				map = generateMap(inputData);
				map[i][j] = '#';

				GuardMove move = new GuardMove(startx, starty, startDirection);
				Set<String> visitedPlaces = new LinkedHashSet<>();
				visitedPlaces.add(move.getLocationStringWithDirection());
				while (!move.leftArea) {
					move = getNextMove(move);
					if (!move.leftArea && visitedPlaces.contains(move.getLocationStringWithDirection())) {
						result++;
						move.loopDetected = true;
						break;
					}
					visitedPlaces.add(move.getLocationStringWithDirection());
				}
			}
		}

		System.out.println("Answer to b : " + result);
	}

	private static GuardMove getNextMove(GuardMove move) {
		if (getCharAtDirection(move) == Character.MIN_VALUE) {
			move.leftArea = true;
			return move;
		}
		if (getCharAtDirection(move) != '#') {
			return move.moveNext();
		}
		return move.turn90();

	}

	private static char[][] generateMap(List<String> rivit) {
		char[][] kartta = new char[rivit.size()][rivit.get(0).length()];
		for (int i = 0; i < rivit.size(); i++) {
			for (int j = 0; j < rivit.get(0).length(); j++) {
				kartta[i][j] = rivit.get(i).charAt(j);
			}
		}
		return kartta;
	}

	private static char getCharAtDirection(GuardMove position) {
		switch (position.direction) {
		case SOUTH:
			if (position.y == korkeus - 1) {
				return Character.MIN_VALUE;
			}
			return map[position.y + 1][position.x];
		case NORTH:
			if (position.y == 0) {
				return Character.MIN_VALUE;
			}
			return map[position.y - 1][position.x];
		case EAST:
			if (position.x == leveys - 1) {
				return Character.MIN_VALUE;
			}
			return map[position.y][position.x + 1];
		case WEST:
			if (position.x == 0) {
				return Character.MIN_VALUE;
			}
			return map[position.y][position.x - 1];
		}
		return Character.MIN_VALUE;
	}

	static class GuardMove {

		public boolean loopDetected;
		public boolean leftArea;
		int x;
		int y;
		String direction;

		public GuardMove(int x, int y, String dir) {
			this.x = x;
			this.y = y;
			direction = dir;
		}

		public GuardMove turn90() {
			String dir = getNewDirection();
			return new GuardMove(this.x, this.y, dir);
		}

		private String getNewDirection() {
			if (this.direction == NORTH) {
				return EAST;
			}
			if (this.direction == EAST) {
				return SOUTH;
			}
			if (this.direction == SOUTH) {
				return WEST;
			}
			return NORTH;
		}

		public String getLocationString() {
			return String.valueOf(x) + ":" + String.valueOf(y);
		}

		public String getLocationStringWithDirection() {
			return String.valueOf(x + ":" + String.valueOf(y) + direction);
		}

		public boolean withinGrid() {
			return x > -1 && x < leveys && y > -1 && y < korkeus;
		}

		public GuardMove moveNext() {
			switch (this.direction) {
			case SOUTH:
				return new GuardMove(this.x, this.y + 1, this.direction);
			case NORTH:
				return new GuardMove(this.x, this.y - 1, this.direction);
			case EAST:
				return new GuardMove(this.x + 1, this.y, this.direction);
			default:
				return new GuardMove(this.x - 1, this.y, this.direction);
			}
		}
	}

	private static void initStartingPoint() {
		for (int i = 0; i < inputData.size(); i++) {
			for (int j = 0; j < inputData.get(i).length(); j++) {
				if (inputData.get(i).charAt(j) == START) {
					startx = j;
					starty = i;
				}
			}
		}
	}

}