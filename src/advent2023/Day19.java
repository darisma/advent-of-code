package advent2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day19 {

    static AdventInputReader ir = new AdventInputReader();
    static final String FILENAME = "2023input/day19.txt";

    static List<String> INPUT_DATA = ir.getStringStream(FILENAME).collect(Collectors.toList());

    static HashMap<String, Workflow> WORKFLOWS = new HashMap<>();
    static List<Part> PARTS = new ArrayList<>();
    
    public static void main (String[] args) {
        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }

    private static long solveAdvent1a() {
        int result = 0;
        
        for(String s: INPUT_DATA) {
            if(s.length() > 0 && s.charAt(0) != '{') {
                Workflow wf = new Workflow(s);
                WORKFLOWS.put(wf.name, wf);
            }else if(s.length() > 0)
                PARTS.add(new Part(s));
        }

        for(Part p : PARTS) {
            String workflow = "in";
            while(!(workflow.equalsIgnoreCase("A") || workflow.equalsIgnoreCase("R"))) {
                workflow = WORKFLOWS.get(workflow).resolvePart(p);
            }
            if(workflow.equalsIgnoreCase("A")) {
                result = result + p.getSum();
            }
        }
        return result;
    }   

    private static long solveAdvent1b() {
        int result = 0;
        
        List<Workflow> workflows = new ArrayList<>();
        Set<Integer> xTresh = new HashSet<>();
        Set<Integer> mTresh = new HashSet<>();
        Set<Integer> aTresh = new HashSet<>();
        Set<Integer> sTresh = new HashSet<>();
        
        
        for(String s: INPUT_DATA) {
            if(s.length() > 0 && s.charAt(0) != '{') {
                Workflow wf = new Workflow(s);
                for(Rule r: wf.rules) {
                    if(r.variableName.equalsIgnoreCase("x")) {
                        xTresh.add(r.value);
                    }
                    if(r.variableName.equalsIgnoreCase("m")) {
                        mTresh.add(r.value);
                    }
                    if(r.variableName.equalsIgnoreCase("a")) {
                        aTresh.add(r.value);
                    }
                    if(r.variableName.equalsIgnoreCase("s")) {
                        sTresh.add(r.value);
                    }

                }
                workflows.add(wf);
            }
        }
        
        return result;
         
    }

    static class Workflow{

        String name;
        ArrayList<Rule> rules;
        String ruleElse;

        public Workflow(String s) {
            this.rules = new ArrayList<>();
            this.name = s.substring(0, s.indexOf("{"));
            String[] rulesSplit = s.substring(s.indexOf("{") + 1).split(",");
            for(int i = 0; i < rulesSplit.length -1; i ++) {
                this.rules.add(new Rule(rulesSplit[i]));
            }
            this.ruleElse = rulesSplit[rulesSplit.length - 1].substring(0, rulesSplit[rulesSplit.length - 1].length() - 1);
        }

        public String resolvePart(Part p) {
            String resolved = "";
            for(Rule rule : rules) {
                if(rule.resolve(p).isPresent() ) {
                    resolved = rule.valueIfMatches;
                    break;
                }
            }

            if(resolved.equalsIgnoreCase("R")) {
                System.out.println("Part has been rejected");
            }
            else if(resolved.equalsIgnoreCase("A")) {
                System.out.println("Part has been accepted");
            }
            if(resolved.length() > 0 ) {
                return resolved;
            }
            System.out.println("Not resolved, else rule is " + ruleElse);

            return ruleElse;
        }

        @Override
        public String toString() {
            return "Workflow [name=" + name + ", rules=" + rules + ", ruleElse=" + ruleElse + "]";
        }
    }

    static class Rule{

        String variableName;
        ComparisonRule comparisonRule;
        int value;
        String valueIfMatches;

        public Rule(String s) {
            // e.g. a<2006:qkq
            this.variableName = String.valueOf(s.charAt(0));
            if(s.charAt(1) == '<') {
                this.comparisonRule = ComparisonRule.LESS_THAN;
            }else if(s.charAt(1) == '>') {
                this.comparisonRule = ComparisonRule.GREATER_THAN;
            }else {
                this.comparisonRule = ComparisonRule.UNKNOWN;
            }
            this.value = Integer.parseInt(s.substring(2, s.indexOf(":")));
            this.valueIfMatches = s.substring(s.indexOf(":") + 1);
        }

        public Optional<String> resolve(Part p) {
            if( variableName.equalsIgnoreCase("x")) {
                if(this.comparisonRule == ComparisonRule.GREATER_THAN) {
                    if(p.x > this.value) {
                        return Optional.of(valueIfMatches);
                    }
                }
                else if (this.comparisonRule == ComparisonRule.LESS_THAN){
                    if(p.x < this.value) {
                        return Optional.of(valueIfMatches);
                    }
                }
            }else if( variableName.equalsIgnoreCase("m")) {
                if(this.comparisonRule == ComparisonRule.GREATER_THAN) {
                    if(p.m > this.value) {
                        return Optional.of(valueIfMatches);
                    }
                }                else if (this.comparisonRule == ComparisonRule.LESS_THAN){
                    if(p.m < this.value) {
                        return Optional.of(valueIfMatches);
                    }}
            }else if (variableName.equalsIgnoreCase("a")) {
                if(this.comparisonRule == ComparisonRule.GREATER_THAN) {
                    if(p.a > this.value) {
                        return Optional.of(valueIfMatches);
                    }
                }
                else if (this.comparisonRule == ComparisonRule.LESS_THAN){
                    if(p.a < this.value) {
                        return Optional.of(valueIfMatches);
                    }
                }

            }else if (variableName.equalsIgnoreCase("s")) {
                if(this.comparisonRule == ComparisonRule.GREATER_THAN) {
                    if(p.s > this.value) {
                        return Optional.of(valueIfMatches);
                    }
                }
                else if (this.comparisonRule == ComparisonRule.LESS_THAN){
                    if(p.s < this.value) {
                        return Optional.of(valueIfMatches);
                    }
                }
            }
            return Optional.empty();
       }

        @Override
        public String toString() {
            return "Rule [variableName=" + variableName + ", comparisonRule=" + comparisonRule + ", value=" + value
                    + ", valueIfMatches=" + valueIfMatches + "]";
        }
    }

    static class Part{
        int x;
        int m;
        int a;
        int s;

        public Part(String s) {
            String[] values = s.split(",");
            this.x = Integer.parseInt(values[0].substring(3));
            this.m = Integer.parseInt(values[1].substring(2));
            this.a = Integer.parseInt(values[2].substring(2));
            this.s = Integer.parseInt(values[3].substring(2, values[3].length() - 1));
        }

        public int getSum() {
            return x + m + a + s;
        }

        @Override
        public String toString() {
            return "Part [x=" + x + ", m=" + m + ", a=" + a + ", s=" + s + "]";
        }
   }

    enum ComparisonRule{
        LESS_THAN, GREATER_THAN, UNKNOWN
    }
}