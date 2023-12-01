package advent2021;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day18 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day18test.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day18().solveAdventA());
		System.out.println("Answer to B is: " + new Day18().solveAdventB());
	}

	private long solveAdventA() {
		
		Map<Pair, Pair> pairs = createPairList(INPUT_DATA);
		
		//Map<Pair, Pair> addedPairs = addPairs(pairs);
		
		System.out.println(pairs);
		
		return 0;

	}

	private Pair addPairs(Map<Pair, Pair> pairs) {

		Pair basePair = null;
		
		for(Entry<Pair, Pair> entry: pairs.entrySet()) {
			addToPair(basePair, entry.getKey(), entry.getValue());
		}
	
		return basePair;
	}

	private void addToPair(Pair basePair, Pair pair, Pair parent) {
		if(basePair == null) {
			basePair = new Pair();
			basePair.depth = 0;
			basePair.pairx = pair;
		}else if(basePair.depth == 0 && basePair.pairy == null) {
			basePair.pairy = pair;
		}else {
			basePair = new Pair(basePair, pair);
		}
		basePair = explodeAndSplit(basePair);
	}

	private Pair explodeAndSplit(Pair basePair) {
		//TODO: exposions and spliterature
		
		return basePair;
	}

	private Map<Pair, Pair> createPairList(List<String> inputData) {
		
		HashMap<Pair, Pair> pairs = new HashMap<>();
		
		for(String line : inputData) {
			Pair temp = new Pair();
			temp.depth = 0;
			Deque<Pair> stack = new ArrayDeque<>();
			boolean readingY = false;
			for(int i = 0; i < line.length() ; i ++) {
				if(line.charAt(i) == '[') {
					if(readingY) {
						readingY = false;
					}
					if(!stack.isEmpty()) {
						temp = new Pair();
						temp.depth = stack.getFirst().depth + 1;
					}
					stack.push(temp);
				}
				if(line.charAt(i) == ']') {
					Pair p = stack.pop();
					System.out.println("A pair is ready: " + p);
					Pair parent = null;
					if(!stack.isEmpty()) {
						parent = stack.getFirst();
						if(parent.x == null && parent.pairx == null) {
							parent.pairx = p;
						}else {
							parent.pairy = p;
						}
					}
					pairs.put(p, parent);
					readingY = false;
				}
				if(line.charAt(i) == ',') {
					readingY = true;
				}
				if(Character.isDigit(line.charAt(i))) {
					if(readingY) {
						stack.getFirst().y = Integer.parseInt(line.substring(i, i+1));
					}else {
					 stack.getFirst().x = Integer.parseInt(line.substring(i, i+1));
					}
				}
			}
		}
		
		return pairs;
	}

	
	private long solveAdventB() {
		return 1;
	}

	private class Pair {


		Pair pairx;
		Pair pairy;
		Integer x;
		Integer y;
		
		int depth;
		
		public Pair() {
			
		}
		

		public Pair(int x, int y) {

			this.x = x;
			this.y = y;
		}
		
		public Pair(Pair x, Pair y) {

			this.pairx = x;
			this.pairy = y;
		}

		public Pair(Pair x, int y) {

			this.pairx = x;
			this.y = y;
		}

		public Pair(int x, Pair y) {

			this.x = x;
			this.pairy = y;
		}

		private boolean xIsPair() {
			return pairx != null;
		}
		
		private boolean yIsPair() {
			return pairy != null;
		}

		@Override
		public String toString() {
			return "Pair [pairx=" + pairx + ", pairy=" + pairy + ", x=" + x + ", y=" + y + ", depth=" + depth + "]";
		}


	}
/**
 * package advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day18 {

    public void solve(List<String> input) {
        Stack<SnailNumber> sums = new Stack<>();
        List<SnailNumber> combinations = new ArrayList<>();

        for (String line : input) {
            SnailNumber sn = parse(line);
            sums.insertElementAt(sn.clone(), 0); // reverse the stack so we pop the top line first
            combinations.add(sn.clone());
        }
        SnailNumber sum = sums.pop();
        while (!sums.isEmpty()) {
            sum = sum.add(sums.pop());
        }
        System.out.println("Part 1: " + sum.magnitude());

        int maxm = -1;
        for (SnailNumber n1 : combinations) {
            for (SnailNumber n2 : combinations) {
                if (n1.equals(n2)) continue;
                maxm = Math.max(maxm, n1.clone().add(n2.clone()).magnitude());
                maxm = Math.max(maxm, n2.clone().add(n1.clone()).magnitude());
            }
        }
        System.out.println("Part 2: " + maxm);
    }

    static class SnailNumber {
        private SnailNumber left;
        private SnailNumber right;
        private Integer value;
        private SnailNumber parent;

        public SnailNumber(SnailNumber left, SnailNumber right) {
            this.left = left;
            this.right = right;
            left.parent = this;
            right.parent = this;
        }

        public SnailNumber(Integer value) {
            this.value = value;
        }

        public SnailNumber add(SnailNumber toAdd) {
            SnailNumber sn = new SnailNumber(this, toAdd);
            sn.reduce();
            return sn;
        }

        public SnailNumber clone() {
            if (isDigit()) return new SnailNumber(value);
            return new SnailNumber(left.clone(), right.clone());
        }

        public int magnitude() {
            if (isDigit()) return value;
            return 3 * left.magnitude() + 2 * right.magnitude();
        }

        private void reduce() {
            while (explode() || split());
        }

        private boolean isDigit() {
            return value != null;
        }

        private int level() {
            if (parent == null) return 0;
            return parent.level() + 1;
        }

        private SnailNumber origin() {
            SnailNumber current = this;
            while (current.parent != null) current = current.parent;
            return current;
        }

        private List<SnailNumber> allDigits() {
            if (isDigit()) return List.of(this);
            List<SnailNumber> all = new ArrayList<>();
            all.addAll(left.allDigits());
            all.addAll(right.allDigits());
            return all;
        }

        private Optional<SnailNumber> protect(List<SnailNumber> list, int i) {
            return (i < 0 || i >= list.size()) ? Optional.empty() : Optional.of(list.get(i));
        }

        private Optional<SnailNumber> nearestDigit(SnailNumber number, int delta) {
            List<SnailNumber> digits = origin().allDigits();
            return protect(digits, digits.indexOf(number) + delta);
        }

        private boolean explode() {
            if (!isDigit()) {
                if (level() == 4) {
                    nearestDigit(left, -1).ifPresent(sn -> sn.value += left.value);
                    nearestDigit(right, 1).ifPresent(sn -> sn.value += right.value);
                    left = null; right = null;
                    value = 0;
                    return true;
                } else {
                    return left.explode() || right.explode();
                }
            }
            return false;
        }

        private boolean split() {
            if (isDigit()) {
                if (value >= 10) {
                    left = new SnailNumber(value / 2);
                    right = new SnailNumber(value / 2 + value % 2);
                    left.parent = this; right.parent = this;
                    value = null;
                    return true;
                }
            } else {
                return left.split() || right.split();
            }
            return false;
        }

        @Override
        public String toString() {
            if (isDigit()) return value.toString();
            return "[" + left + "," + right + "]";
        }
    }

    private SnailNumber parse(String s) {
        if (s.length() == 1) {
            return new SnailNumber(Integer.parseInt(s));
        }
        int level = 0;
        int splitAt = -1;
        for (int i = 0; i < s.length() && splitAt == -1; i++) {
            switch (s.charAt(i)) {
                case '[' -> level++;
                case ']' -> level--;
                case ',' -> {
                    if (level == 1) {
                        splitAt = i;
                    }
                }
            }
        }
        return new SnailNumber(parse(s.substring(1, splitAt)), parse(s.substring(splitAt + 1, s.length() - 1)));
    }

    public static void main(String[] args) throws IOException {
        Day18 solver = new Day18();
        List<String> lines = Files.lines(Paths.get("./data/day18.txt")).collect(Collectors.toList());
        long start = System.currentTimeMillis();
        solver.solve(lines);
        System.out.printf("Done after %d millis%n", System.currentTimeMillis() - start);
    }

}
 */
}