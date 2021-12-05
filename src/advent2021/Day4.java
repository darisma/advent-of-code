package advent2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day4 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day4.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	static final int ROW_LENGTH = INPUT_DATA.get(0).length();

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + solveAdventA());
		System.out.println("Answer to B is: " + solveAdventB());
	}

	private static int solveAdventA() {

		List<Integer> bingoNumbers = Arrays.asList(INPUT_DATA.get(0).split(",", -1)).stream().map(Integer::parseInt).collect(Collectors.toList());
		List<BingoBoard> bingoBoards = buildBingoBoards();

		Optional<BingoBoard> winningBoard = playBingo(bingoBoards, bingoNumbers);
		if(winningBoard.isPresent()) {
			int sumOfUncalledNumbers =  winningBoard.get().getSumOfUncalledNumbers();
			int lastCalledNumber = winningBoard.get().getLastCalledNumber();
			return sumOfUncalledNumbers * lastCalledNumber;}
		return 0;
	}

	private static int solveAdventB() {

		List<Integer> bingoNumbers = Arrays.asList(INPUT_DATA.get(0).split(",", -1)).stream().map(Integer::parseInt).collect(Collectors.toList());
		List<BingoBoard> bingoBoards = buildBingoBoards();

		Optional<BingoBoard> losingBoard = playBingoToLose(bingoBoards, bingoNumbers);
		if(losingBoard.isPresent()) {
			int sumOfUncalledNumbers = losingBoard.get().getSumOfUncalledNumbers();
			int lastCalledNumber = losingBoard.get().getLastCalledNumber();
			return sumOfUncalledNumbers * lastCalledNumber;
		}
		return 0;
	}


	static Optional<BingoBoard> playBingo(List<BingoBoard> bingoBoards, List<Integer> bingoNumbers) {

		for ( Integer bingoNumber : bingoNumbers) {
			bingoBoards.stream().forEach(e -> e.callNumber(bingoNumber));
			if(bingoBoards.stream().anyMatch(BingoBoard::hasBingo)) {
				System.out.println("Found winner");
				return bingoBoards.stream().filter(BingoBoard::hasBingo).findFirst();
			}
		}
		return Optional.empty();

	}

	static Optional<BingoBoard> playBingoToLose(List<BingoBoard> bingoBoards, List<Integer> bingoNumbers) {

		for ( Integer bingoNumber : bingoNumbers) {
			if(bingoBoards.size() == 1) {
				if (bingoBoards.get(0).callNumber(bingoNumber)) {
					System.out.println("Found loser");
					return Optional.of(bingoBoards.get(0)); 
				}
				
			}else {
				bingoBoards.stream().forEach(e -> e.callNumber(bingoNumber));
				bingoBoards = bingoBoards.stream().filter(e -> !e.hasBingo()).collect(Collectors.toList());
			}
		}
		return Optional.empty();
	}

	static List<BingoBoard> buildBingoBoards() {
		List<BingoBoard> bingoBoards = new ArrayList<>();

		List<String> boardData = INPUT_DATA.subList(2, INPUT_DATA.size());
		int rows = 5;

		for(int i = 0; i < boardData.size(); i = i + 6) {
			List<String> subList = boardData.subList(i, i + rows);
			List<BingoRow> bingoRows = subList.stream().map(BingoRow::new).collect(Collectors.toList());
			bingoBoards.add(new BingoBoard(bingoRows));
		}

		return bingoBoards;
	}

	static void generateAndAddColumnRows(List<BingoRow> bingoRows) {
		List<BingoRow> columnRows = new ArrayList<>();
		for(int i = 0; i < bingoRows.get(0).getBoardNumbers().size(); i++) {

			List<Integer> createdRow = new ArrayList<>();
			for(int k = 0; k < bingoRows.size(); k++ ) {
				createdRow.add(bingoRows.get(k).getNthNumber(i));
			}
			columnRows.add(new BingoRow(createdRow));
		}
		bingoRows.addAll(columnRows);
	}

	static class BingoBoard {
		List<BingoRow> rows;
		List<Integer> calledNumbers = new ArrayList<>();
		boolean wasLast = false;

		public BingoBoard (List<BingoRow> bingoRows) {
			rows = bingoRows;
		}

		public boolean hasBingoAsLastBoard() {
			return wasLast;
		}

		public boolean hasBingo() {
			return rows.stream().anyMatch(BingoRow::allCalled) || anyColumnHasBingo(); 
		}

		private boolean anyColumnHasBingo() {
			for (int i = 0; i < 5; i++) {
				boolean allCalled = true;
				for(BingoRow br: rows) {
					if(!br.getBoardNumbers().get(i).called) {
						allCalled = false;
					}
				}
				if (allCalled) {
					return true;
				}
			}
			return false;
		}

		public boolean callNumber(Integer bingoNumber) {
			calledNumbers.add(bingoNumber);
			for(BingoRow row : rows) {
				for(BingoNumber number: row.getBoardNumbers()) {
					if(number.number == bingoNumber.intValue()) {
						number.called = true;
						if(rows.stream().allMatch(e -> e.getBoardNumbers().get(number.indexOnRow).called)) {
							return true;
						}
						if(row.allCalled()) {
							return true;
						}

					}
				}
			}
			return false;
		}

		public int getSumOfUncalledNumbers() {
			int sum = 0;
			for (BingoRow r: rows) { 
				for (BingoNumber n : r.getBoardNumbers()) {
					if (! n.called) {
						sum = sum + n.number;
					}
				}
			}
			return sum;
		}

		public int getLastCalledNumber() {
			return calledNumbers.get(calledNumbers.size() - 1);
		}
	}

	static class BingoRow {
		List<BingoNumber> boardNumbers;

		public BingoRow(String row) {
			row = row.trim().replaceAll(" +", " ");
			List<Integer> rowNumbers = Arrays.asList(row.split(" ")).stream().map(Integer::parseInt).collect(Collectors.toList());
			boardNumbers = new ArrayList<>();
			for(int i = 0; i < rowNumbers.size(); i++) {
				BingoNumber bNumber = new BingoNumber(rowNumbers.get(i), i);
				boardNumbers.add(bNumber);
			}
		}

		public boolean allCalled() {
			return ( boardNumbers.stream().filter(e -> !e.called).count() == 0);
		}

		public BingoRow(List<Integer> rowNumbers) {
			boardNumbers = new ArrayList<>();
			for(int i = 0; i < rowNumbers.size(); i++) {
				BingoNumber bNumber = new BingoNumber(rowNumbers.get(i), i);
				boardNumbers.add(bNumber);
			}
		}

		public List<BingoNumber> getBoardNumbers(){
			return boardNumbers;
		}

		public int getNthNumber(int index) {
			return boardNumbers.get(index).getNumber();
		}

	}

	static class BingoNumber {
		int number;
		boolean called;
		int indexOnRow;

		public BingoNumber(String number, int index) {
			this.number = Integer.parseInt(number);
			this.called = false;
			indexOnRow = index;
		}

		public BingoNumber(Integer number, int index) {
			this.number = number;
			this.called = false;
			indexOnRow = index;
		}

		public int getNumber() {
			return number;
		}
	}
}

