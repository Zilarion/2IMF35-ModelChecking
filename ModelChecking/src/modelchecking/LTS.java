/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Hein
 */
public class LTS {

    private final int initialState;
    private final int absoluteEdges;
    private final HashSet<Edge> edgeSet;
    private final HashMap<Integer,State> nodeMap;

    public LTS(String x, String y, String z) {
        this.initialState = Integer.parseInt(x);
        this.absoluteEdges = Integer.parseInt(y);
        int states = Integer.parseInt(z);
        this.edgeSet = new HashSet<>();
        this.nodeMap = new HashMap<>(states);
    }

    public int getInit() {
        return this.initialState;
    }

    public HashSet<Edge> getEdges() {
        return this.edgeSet;
    }
    
    public int getEdgeCount() {
        return this.edgeSet.size();
    }

    public HashMap<Integer,State> getStates() {
        return this.nodeMap;
    }
    
    public int getStateSize() {
        return nodeMap.size();
    }

    public void addNode(State n) {
        this.nodeMap.put(n.getID(),n);
    }
    
    public int getAbsoluteEdgeCount() {
        return absoluteEdges;
    }
    
    @Override
    public String toString() {
        String init = "Initial state: " + this.initialState + "\n";
        String trans = "Nr of transitions: " + this.edgeSet.size() + "\n";
        String stats = "Nr of states: " + this.nodeMap.size() + "\n";
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
}
