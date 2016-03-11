/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

/**
 *
 * @author Hein
 */
public class Edge {
    private final State startState;
    private final String label;
    private final State endState;
    
    public Edge(State from, String l, State to) {
        this.startState = from;
        this.label = l;
        this.endState = to;
    }
    
    public State getStart() {
        return this.startState;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public State getEnd() {
        return this.endState;
    }
    
    @Override
    public String toString() {
        String start = "Start state: " + this.startState + "\n";
        String lab = "Label: " + this.label + "\n";
        String end = "End state: " + this.endState;
        return "Edge: " + "\n" + start + lab + end;
    }
}
