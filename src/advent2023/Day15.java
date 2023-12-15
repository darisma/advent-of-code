package advent2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day15 {

    static AdventInputReader ir = new AdventInputReader();
    static final String FILENAME = "2023input/day15.txt";

    static List<String> INPUT_DATA = ir.getStringStream(FILENAME).collect(Collectors.toList());

    public static void main (String[] args) {
        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }

    private static long solveAdvent1a() {
        StringTokenizer st = new StringTokenizer(INPUT_DATA.get(0), ",");
        long result = 0;
        while(st.hasMoreTokens()) {
            result = result + getHashValue(st.nextToken());
        }
        return result;
    }

    private static long solveAdvent1b() {
        StringTokenizer st = new StringTokenizer(INPUT_DATA.get(0), ",");

        HashMap<Integer, ArrayList<Lens>> boxes = new HashMap<>();
        
        while(st.hasMoreTokens()) {
            updateBoxes(st.nextToken(), boxes);
        }

        return countTotalFocusingPower(boxes);
     }
    
    private static long countTotalFocusingPower(HashMap<Integer, ArrayList<Lens>> boxes) {
        long result = 0;
        for (int i = 0; i < 256; i ++) {
            ArrayList<Lens> boxesAtIndex = boxes.get(i);
            if(boxesAtIndex != null) {
                for(int j = 0; j < boxesAtIndex.size(); j++) {
                    result = result + (i + 1) * (j + 1) * boxesAtIndex.get(j).focalLength;
                }
            }
        }
        return result;
    }

    private static void updateBoxes(String s, HashMap<Integer, ArrayList<Lens>> boxes) {
        if(s.indexOf("-") != -1) {
           int hash = getHashValue(s.substring(0, s.length() - 1));
           if(boxes.get(hash) == null) {
               boxes.put(hash, new ArrayList<Lens>());
           }
           List<Lens> boxContents = boxes.get(hash);
           int indexOfLens = -1;
           for(int i = 0; i < boxContents.size(); i ++) {
               if(boxContents.get(i).label.equalsIgnoreCase(s.substring(0, s.length() - 1))) {
                   indexOfLens = i;
                   break;
               }
           }
           if(indexOfLens != -1) {
               boxContents.remove(indexOfLens);
           }
        }else {
            // update box
            int hash = getHashValue(s.substring(0, s.indexOf("=")));
            if(boxes.get(hash) == null) {
                boxes.put(hash, new ArrayList<Lens>());
            }
            List<Lens> boxContents = boxes.get(hash);

            int indexOfLens = -1;
            for(int i = 0; i < boxContents.size(); i ++) {
                if(boxContents.get(i).label.equalsIgnoreCase(s.substring(0, s.length() - 2))) {
                    indexOfLens = i;
                    break;
                }
            }
            if (indexOfLens != -1) {
                boxContents.set(indexOfLens, new Lens(s));
            }else {
                boxContents.add(new Lens(s));
            }
        }
    }

    private static int getHashValue(String s) {
        int current = 0;
        
        char[] chars = s.toCharArray();
        
        for(char c : chars) {
            current = current + (int) c;    
            current = current * 17;
            current = current % 256;
        }
        
        return current;
    }
  
    static class Lens{
        int focalLength;
        String label;
        
        public Lens (String s) {
            String[] data = s.split("=");
            this.label = data[0];
            this.focalLength = Integer.valueOf(data[1]);
        }

        @Override
        public String toString() {
            return "Lens [focalLength=" + focalLength + ", label=" + label + "]";
        }
    }
}