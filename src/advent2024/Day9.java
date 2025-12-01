package advent2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day9 {

	static AdventInputReader ir = new AdventInputReader();
	static final String FILENAME = "2024input/day9.txt";

	static final List<String> inputData = ir.getStringStream(FILENAME).collect(Collectors.toList());


	public static void main(String[] args) {
		solveAdvent1a();
		solveAdvent1b();
	}

	private static void solveAdvent1a() {
	    long result = 0;

	    Stack<Block> allBlocks = readReverseAllBlocks(inputData.get(0));

	    List<Block> filledBlocks = new ArrayList<>();

	    Integer totalLength = allBlocks.stream().mapToInt(e -> e.length).sum();

	    Stack<Block> reverseBlocks = readAllBlocks(inputData.get(0));

	    Block b = allBlocks.pop();
	    Block backBlock = reverseBlocks.pop();
	    Block fillBlock = new Block(backBlock.id, 0, 0);
	    List<Block> fillBlocks = new ArrayList<>();

	    while(b.id < backBlock.id) {
            while(b.free > 0) {
                if(backBlock.length == 0) {
                    if(fillBlock.length > 0) {
                        fillBlocks.add(fillBlock);
                    }
                    fillBlock = new Block(0,0,0);
                    backBlock = reverseBlocks.pop();
                }
                if(fillBlock.length == 0) {
                    fillBlock = new Block(backBlock.id, 1, 0);
                    backBlock.length --;
                }else {
                    fillBlock.length ++;
                    backBlock.length --;
                }
                b.free --;

            }
            filledBlocks.add(b);
            filledBlocks.addAll(fillBlocks);
            fillBlocks = new ArrayList<>();
            if(fillBlock.length > 0) {
                filledBlocks.add(new Block(fillBlock.id, fillBlock.length, 0));
            }
            fillBlock.length=0;
            b = allBlocks.pop();
	    }

	    while(filledBlocks.stream().mapToInt(e -> e.length).sum() < totalLength) {
	        filledBlocks.get(filledBlocks.size() - 1).length++;
	    }

	    result = calculateResult(filledBlocks);

		System.out.println("Answer to a : " + result);
	}

    private static long calculateResult(List<Block> filledBlocks) {
	    long result = 0;

	    int position = 0;
	    for(Block b:filledBlocks) {

	        result = result + b.getScore(position);
	        position = position + b.length;

	    }

	    return result;
    }

    private static Stack<Block> readAllBlocks(String string) {

	    Stack<Block> blocks = new Stack<>();

	    for (int i = 0; i < string.length(); i++) {
	        if(i % 2 == 0) {
	            Block b = new Block(i/2, Integer.valueOf(string.substring(i, i+1)), 0);
	            blocks.push(b);
	        }else {
	            // get block just added
	            Block block = blocks.pop();
	            block.setFree(Integer.valueOf(string.substring(i, i + 1)));
	            blocks.push(block);
	        }
	    }
	    return blocks;
    }

    private static Stack<Block> readReverseAllBlocks(String string) {

        Stack<Block> blocks = new Stack<>();

        for (int i = 0; i < string.length(); i++) {
            if(i % 2 == 0) {
                Block b = new Block(i/2, Integer.valueOf(string.substring(i, i+1)), 0);
                blocks.push(b);
            }else {
                // get block just added
                Block block = blocks.pop();
                block.setFree(Integer.valueOf(string.substring(i, i + 1)));
                blocks.push(block);
            }
        }

        Stack<Block> blockses = new Stack<>();
        while(!blocks.isEmpty()) {
            blockses.push(blocks.pop());
        }
        return blockses;
    }


    private static void solveAdvent1b() {
		int result = 0;

		System.out.println("Answer to b : " + result);
	}

	static class Block{
	    int id; // number
	    int length; // length of block
	    int free; // free space

	    public Block(int id, int length, int free) {
	        this.id = id;
	        this.length = length;
	        this.free = free;
	    }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getFree() {
            return free;
        }

        public void setFree(int free) {
            this.free = free;
        }

        public long getScore(int initialPosition) {
            long score = 0;
            for(int i = initialPosition; i < initialPosition + length ; i ++) {
                score = score + initialPosition * id;
            }
            return score;
        }

        @Override
        public String toString() {
            return "Block [id=" + id + ", length=" + length + ", free=" + free + "]";
        }



	}

}