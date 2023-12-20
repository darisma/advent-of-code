package advent2023;

import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day20 {

    static AdventInputReader ir = new AdventInputReader();
    static final String FILENAME = "2023input/day20_test.txt";

    static List<String> INPUT_DATA = ir.getStringStream(FILENAME).collect(Collectors.toList());

    public static void main (String[] args) {
        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }

    private static long solveAdvent1a() {
    	// build list of modules 
        // add button module
        // update modules with inputs
        
        return 0;
    }

    private static long solveAdvent1b() {
    	return 0;
    }

    static class Module{
        String name;
        List<Module> destinations;
        List<Module> inputs;
        ModuleType type;
        boolean running;
        
        public Module(String s) {
            
        }
    }
    
    enum Pulse{
        LOW, HIGH
    }
    
    enum ModuleType{
        FLIPFLOP, CONJUNCTION, BROADCAST, BUTTON
    }
}