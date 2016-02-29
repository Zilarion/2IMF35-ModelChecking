/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hein
 */
public class LTS {
    public int initialState;
    public int transitions;
    public int states;
    public List<Edge> edgeList;
    
    public LTS(String x, String y, String z) {
        this.initialState = Integer.parseInt(x);
        this.transitions = Integer.parseInt(y);
        this.states = Integer.parseInt(z);
        this.edgeList = new ArrayList();
    }
    
    @Override
    public String toString() {
        String init = "Initial state: " + this.initialState + "\n";
        String trans = "Nr of transitions: " + this.transitions + "\n";
        String stats = "Nr of states: " + this.states + "\n";
        String edges = "";
        for (Edge edge : this.edgeList) {
            edges += edge.toString() + "\n";
        }
        return init + trans + stats + edges;
    }
}
