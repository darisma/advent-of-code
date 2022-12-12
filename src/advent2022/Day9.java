package advent2022;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.lang.Math;

import utils.AdventInputReader;

public class Day9 {


	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2022input/day9.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day9().solveAdventA());
		System.out.println("Answer to B is: " + new Day9().solveAdventB());
	}

	private long solveAdventA() {

		GameState state = new GameState();

		for(String line: INPUT_DATA) {
			state = processLine( state, line.split(" ")[0], Integer.parseInt(line.split(" ")[1]));
		}

		return state.getTailVisited().size();
	}


	private long solveAdventB() {
		GameState state = new GameState();

		for(String line: INPUT_DATA) {
			state = processLine2( state, line.split(" ")[0], Integer.parseInt(line.split(" ")[1]));
		}
		return state.getTailVisited().size();
	}

	GameState processLine( GameState state, String suunta, Integer moves) {

		for(int i = 0; i < moves ; i++) {
			state.moveHeadTo(suunta);
			state.moveTail(state.getHeadPosition());
		}

		return state;

	}

	GameState processLine2( GameState state, String suunta, Integer moves) {
		
		for(int i = 0; i < moves ; i++) {
			state.moveHeadTo(suunta);
			state.moveKnots();
			state.moveTail(state.getKnotPosition().get(8));
		}

		return state;
	}

	private class GameState {
		private static final String SUUNTA_UP = "U";
		private static final String SUUNTA_DOWN = "D";
		private static final String SUUNTA_RIGHT = "R";
		private static final String SUUNTA_LEFT = "L";
		Coord headPosition;
		HashMap <Integer, Coord> knotPosition = new HashMap<>();
		Coord tailPosition;

		Set<Coord> tailVisited;

		public GameState() {
			headPosition = new Coord(0,0);
			tailPosition = new Coord(0,0);
			tailVisited = new HashSet<>();
			tailVisited.add(tailPosition);
			knotPosition.put(1, new Coord(0,0));
			knotPosition.put(2, new Coord(0,0));
			knotPosition.put(3, new Coord(0,0));
			knotPosition.put(4, new Coord(0,0));
			knotPosition.put(5, new Coord(0,0));
			knotPosition.put(6, new Coord(0,0));
			knotPosition.put(7, new Coord(0,0));
			knotPosition.put(8, new Coord(0,0));

		}

		public void moveKnots() {
			for(int i = 1 ; i < 9 ; i++ ) {
				knotPosition.put(i, updateKnot(i));
			}

		}

		public HashMap<Integer, Coord> getKnotPosition() {
			return knotPosition;
		}
		
		public void moveTail(Coord oneBeforeTail ) {

			// suoraan ylös tai alas

			if( oneBeforeTail.x == tailPosition.x) {
				if(oneBeforeTail.y > tailPosition.y + 1) {
					this.tailPosition = new Coord (tailPosition.x, tailPosition.y + 1);
					tailVisited.add(tailPosition);
					return;
				}
				if(oneBeforeTail.y < tailPosition.y - 1) {
					this.tailPosition = new Coord (tailPosition.x, tailPosition.y - 1);
					tailVisited.add(tailPosition);
					return;
				}
			}

			// suoraan oikeelle tai vasemmalle

			if(oneBeforeTail.y == tailPosition.y) {
				if(oneBeforeTail.x > tailPosition.x + 1) {
					this.tailPosition = new Coord (tailPosition.x + 1, tailPosition.y);
					tailVisited.add(tailPosition);
					return;
				}
				if(oneBeforeTail.x < tailPosition.x - 1) {
					this.tailPosition = new Coord (tailPosition.x - 1, tailPosition.y);
					tailVisited.add(tailPosition);
					return;
				}
			}

			// johonkin kulmaan

			if(oneBeforeTail.x != tailPosition.x && oneBeforeTail.y != tailPosition.y) {
				// jos kumpaankin suuntaan on kak eroa, mennään erilailla
				if(Math.abs(oneBeforeTail.x - tailPosition.x) > 1 && Math.abs(oneBeforeTail.y - tailPosition.y) > 1) {
					if(oneBeforeTail.x > tailPosition.x && oneBeforeTail.y > tailPosition.y) {
						this.tailPosition = new Coord (tailPosition.x + 1, tailPosition.y + 1);
						tailVisited.add(tailPosition);
						return;
					}
					if(oneBeforeTail.x > tailPosition.x && oneBeforeTail.y < tailPosition.y) {
						this.tailPosition = new Coord (tailPosition.x + 1, tailPosition.y - 1);
						tailVisited.add(tailPosition);
						return;
					}
					if(oneBeforeTail.x < tailPosition.x && oneBeforeTail.y > tailPosition.y) {
						this.tailPosition = new Coord (tailPosition.x - 1, tailPosition.y + 1);
						tailVisited.add(tailPosition);
						return;
					}
					if(oneBeforeTail.x < tailPosition.x && oneBeforeTail.y < tailPosition.y) {
						this.tailPosition = new Coord (tailPosition.x - 1, tailPosition.y - 1);
						tailVisited.add(tailPosition);
						return;
					}
				}
				
				// kumpaan suuntaan on kaks eroa, siihen suuntan yks, ja sitten se missä on yks eroa,
				// niin se laitetaan samaksi kuin headilla.
				if(oneBeforeTail.x > tailPosition.x + 1) {
					this.tailPosition = new Coord (tailPosition.x + 1, oneBeforeTail.y);
					tailVisited.add(tailPosition);
					return;
				}

				if(oneBeforeTail.x < tailPosition.x - 1) {
					this.tailPosition = new Coord (tailPosition.x - 1, oneBeforeTail.y);
					tailVisited.add(tailPosition);
					return;
				}

				if(oneBeforeTail.y > tailPosition.y + 1) {
					this.tailPosition = new Coord ( oneBeforeTail.x, tailPosition.y + 1);
					tailVisited.add(tailPosition);
					return;
				}

				if(oneBeforeTail.y < tailPosition.y - 1) {
					this.tailPosition = new Coord ( oneBeforeTail.x, tailPosition.y - 1);
					tailVisited.add(tailPosition);
					return;
				}
			}
		}

		public Coord updateKnot(Integer k) {
			if(k.intValue() == 1) {
				// suoraan ylös tai alas
				Coord knot = knotPosition.get(k);
				if( headPosition.x == knot.x) {
					if(headPosition.y > knot.y + 1) {
						return knot.moveDown(String.valueOf(k));
					}
					if(headPosition.y < knot.y - 1) {
						return knot.moveUp(String.valueOf(k));
					}
				}

				// suoraan oikeelle tai vasemmalle

				if(headPosition.y == knot.y) {
					if(headPosition.x > knot.x + 1) {
						return knot.moveRight(String.valueOf(k));
					}
					if(headPosition.x < knot.x - 1) {
						return knot.moveLeft(String.valueOf(k));
					}
				}

				// johonkin kulmaan

				if(headPosition.x != knot.x && headPosition.y != knot.y) {
					// kumpaan suuntaan on kaks eroa, siihen suuntan yks, ja sitten se missä on yks eroa,
					// niin se laitetaan samaksi kuin headilla.
					if(headPosition.x > knot.x + 1) {
						return new Coord (knot.x + 1, headPosition.y);
					}

					if(headPosition.x < knot.x - 1) {
						return new Coord (knot.x - 1, headPosition.y);
					}

					if(headPosition.y > knot.y + 1) {
						return new Coord ( headPosition.x, knot.y + 1);
					}

					if(headPosition.y < knot.y - 1) {
						return new Coord ( headPosition.x, knot.y - 1);
					}
				}
			}else {
				// suoraan ylös tai alas
				Coord knot = knotPosition.get(k);
				Integer prev = Integer.valueOf(k - 1);
				Coord prevKnot = knotPosition.get(prev);
				if( prevKnot.x == knot.x) {
					if(prevKnot.y > knot.y + 1) {
						return knot.moveDown(String.valueOf(k));
					}
					if(prevKnot.y < knot.y - 1) {
						return knot.moveUp(String.valueOf(k));
					}
				}

				// suoraan oikeelle tai vasemmalle

				if(prevKnot.y == knot.y) {
					if(prevKnot.x > knot.x + 1) {
						return knot.moveRight(String.valueOf(k));
					}
					if(prevKnot.x < knot.x - 1) {
						return knot.moveLeft(String.valueOf(k));
					}
				}

				// johonkin kulmaan

				if(prevKnot.x != knot.x && prevKnot.y != knot.y) {
					
					// jos kumpaankin suuntaan on kak eroa, mennään erilailla
					if(Math.abs(prevKnot.x - knot.x) > 1 && Math.abs(prevKnot.y - knot.y) > 1) {
						if(prevKnot.x > knot.x && prevKnot.y > knot.y) {
							return new Coord (knot.x + 1, knot.y + 1);
						}
						if(prevKnot.x > knot.x && prevKnot.y < knot.y) {
							return new Coord (knot.x + 1, knot.y - 1);
						}
						if(prevKnot.x < knot.x && prevKnot.y > knot.y) {
							return new Coord (knot.x - 1, knot.y + 1);
						}
						if(prevKnot.x < knot.x && prevKnot.y < knot.y) {
							return new Coord (knot.x - 1, knot.y - 1);
						}
					}
					
					// kumpaan suuntaan on kaks eroa, siihen suuntan yks, ja sitten se missä on yks eroa,
					// niin se laitetaan samaksi kuin headilla.
					if(prevKnot.x > knot.x + 1) {
						return new Coord (knot.x + 1, prevKnot.y);
					}

					if(prevKnot.x < knot.x - 1) {
						return new Coord (knot.x - 1, prevKnot.y);
					}

					if(prevKnot.y > knot.y + 1) {
						return new Coord ( prevKnot.x, knot.y + 1);
					}

					if(prevKnot.y < knot.y - 1) {
						return new Coord ( prevKnot.x, knot.y - 1);
					}
				}
				
			}
			return knotPosition.get(k);
		}

		public void moveHeadTo(String suunta) {
			if(suunta.equals(SUUNTA_UP)){
				this.setHeadPosition(getHeadPosition().moveUp("HEAD"));
			}
			if(suunta.equals(SUUNTA_DOWN)) {
				this.setHeadPosition(getHeadPosition().moveDown("HEAD"));
			}
			if(suunta.equals(SUUNTA_RIGHT)) {
				this.setHeadPosition(getHeadPosition().moveRight("HEAD"));
			}
			if(suunta.equals(SUUNTA_LEFT)) {
				this.setHeadPosition(getHeadPosition().moveLeft("HEAD"));
			}
		}

		public Coord getHeadPosition() {
			return headPosition;
		}

		public void setHeadPosition(Coord headPosition) {
			this.headPosition = headPosition;
		}

		public Set<Coord> getTailVisited() {
			return tailVisited;
		}

	}

	private class Coord {

		int x;
		int y;

		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Coord moveLeft(String coordName) {
			//System.out.println("Coord " + coordName + " moves LEFT");
			return new Coord(this.x - 1, this.y);
		}

		public Coord moveRight(String coordName) {
			//System.out.println("Coord " + coordName + " moves RIGHT");
			return new Coord(this.x + 1, this.y);
		}

		public Coord moveDown(String coordName) {
			//System.out.println("Coord " + coordName + " moves DOWN");
			return new Coord(this.x, this.y + 1);
		}

		public Coord moveUp(String coordName) {
			//System.out.println("Coord " + coordName + " moves UP");
			return new Coord(this.x, this.y - 1);
		}

		@Override
		public String toString() {
			return "Coord [x=" + x + ", y=" + y + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Coord other = (Coord) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
	}
}
