package advent2022;

import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;
import utils.AdventInputReader;

public class Day20 {


	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2022input/day20.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day20().solveAdventA());
		System.out.println("Answer to B is: " + new Day20().solveAdventB());
	}

	private long solveAdventA() {
		// create Vector
		Vector<ElfNode> v = new Vector<ElfNode>();
		for(int i = 0; i < INPUT_DATA.size(); i++) {
			v.add(new ElfNode(i, Integer.parseInt(INPUT_DATA.get(i))));
		}

		// mix
		for(int i = 0; i < INPUT_DATA.size(); i ++) {
			mix(v, i);
		}

		// find position of zero as starting point
		int positionOfZero = getPositionOfZero(v);
		
		// find result
		int first = getIndexAfterZero(1000, positionOfZero);
		int second = getIndexAfterZero(2000, positionOfZero);
		int third = getIndexAfterZero(3000, positionOfZero);

		return v.get(first).mixValue + v.get(second).mixValue + v.get(third).mixValue;
	}

	private int getIndexAfterZero(int i, int positionOfZero) {
		int index = i + positionOfZero;
		if(index > INPUT_DATA.size() - 1) {
			while(index > INPUT_DATA.size() - 1) {
				index = index - INPUT_DATA.size();
			}
		}
		return index;
	}

	private int getPositionOfZero(Vector<ElfNode> v) {
		for(int i = 0; i < INPUT_DATA.size(); i++) {
			if(v.get(i).mixValue == 0) {
				return i;
			}
		}
		return -1;
	}

	private void mix(Vector<ElfNode> v, int i) {
		// find elfNode with originalPosition i

		int currentPosition = 0;
		ElfNode elfNode = null;
		
		for(int k = 0; k < INPUT_DATA.size(); k ++) {
			if(v.get(k).originalPosition == i) {
				elfNode = v.get(k);
				currentPosition = k;
				break;
			}
		}

		v.remove(elfNode);
		
		long move = elfNode.mixValue;
		move = move + currentPosition;

		// Added for second part
		if(Math.abs(move) > INPUT_DATA.size() - 1) {
			move = move % (INPUT_DATA.size() - 1);
		}
		
		// moving forward
		if(move + 1 > INPUT_DATA.size() - 1) {
			while(move + 1 > INPUT_DATA.size() - 1) {
				move = move - INPUT_DATA.size() + 1;
			}
		}
		// moving backward
		if(move - 1 < 0) {
			while(move - 1 < 0) {
				move = move + INPUT_DATA.size() - 1;
			}
		}

		v.insertElementAt(elfNode, (int)move);
	}

	private long solveAdventB() {
		// create Vector
		Vector<ElfNode> v = new Vector<ElfNode>();
		 
		// add decryption key
		long decryptionKey = 811589153;
		
		for(int i = 0; i < INPUT_DATA.size(); i++) {
			v.add(new ElfNode(i, Integer.parseInt(INPUT_DATA.get(i)) * decryptionKey));
		}

		// mix 10 times
		for (int j = 0; j < 10 ; j ++) {
			for(int i = 0; i < INPUT_DATA.size(); i ++) {
				mix(v, i);
			}
		}

		// find position of zero as starting point
		int positionOfZero = getPositionOfZero(v);
		
		// find result
		int first = getIndexAfterZero(1000, positionOfZero);
		int second = getIndexAfterZero(2000, positionOfZero);
		int third = getIndexAfterZero(3000, positionOfZero);

		return v.get(first).mixValue + v.get(second).mixValue + v.get(third).mixValue;

	}

	public class ElfNode {
		
		int originalPosition;
		long mixValue;
		
		public ElfNode(int p, long m) {
			this.originalPosition = p;
			this.mixValue = m;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(mixValue, originalPosition);
			return result;
		}

		@Override
		public String toString() {
			return "ElfNode [originalPosition=" + originalPosition + ", mixValue=" + mixValue + "]";
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ElfNode other = (ElfNode) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return mixValue == other.mixValue && originalPosition == other.originalPosition;
		}

		private Day20 getEnclosingInstance() {
			return Day20.this;
		}
	}

}
