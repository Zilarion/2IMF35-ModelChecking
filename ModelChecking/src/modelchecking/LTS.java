/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Hein
 */
public class LTS {

    private final int initialState;
    private final int transitions;
    private final int states;
    private final HashSet<Edge> edgeSet;
    private final HashMap nodeSet;

    public LTS(String x, String y, String z) {
        this.initialState = Integer.parseInt(x);
        this.transitions = Integer.parseInt(y);
        this.states = Integer.parseInt(z);
        this.edgeSet = new HashSet();
        this.nodeSet = new HashMap(this.states);
    }

    public int getInit() {
        return this.initialState;
    }

    public int getTrans() {
        return this.transitions;
    }

    public int getStates() {
        return this.states;
    }

    public void addNode(Node n) {
        this.nodeSet.put(n.getID(), n);
    }
    
    public HashMap getNodes() {
        return this.nodeSet;
    }
    
    public HashSet<Edge> getEdges() {
        return this.edgeSet;
    }

    @Override
    public String toString() {
        String init = "Initial state: " + this.initialState + "\n";
        String trans = "Nr of transitions: " + this.transitions + "\n";
        String stats = "Nr of states: " + this.states + "\n";
        String edges = "";
        for (Edge edge : this.edgeSet) {
            edges += edge.toString() + "\n";
        }
        return init + trans + stats + edges;
    }

    public String toDot() {
        String edges = "";
        for (Edge edge : this.edgeSet) {
            edges += edge.getStart() + " -> " + edge.getEnd() + "[label=" + edge.getLabel() + "];\n";
        }
        String graph = "digraph G {\n" + edges + "}";
        return graph;
    }
    
    
    public void emersonInitialize(uFunction f, HashMap<String, Boolean> A) {
        for (String variable : f.getVariables()) {
            
        }
    }
    
    public void evaluateEmerson(uFunction function, HashMap<String, Boolean> A) {
        switch(function.operator) {
            case FALSE:
                
            case TRUE:
            case VARIABLE:
            case ACTION:
                
            break;
            case AND:
            case OR:
            case DIAMOND:
            case BOX:
            case LFP:
            case GFP:
            break;
        }
    }
    
    public void evaluateNaive(uFunction f) {
        
    }
}
