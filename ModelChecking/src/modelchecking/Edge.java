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
    public int startState;
    public String label;
    public int endState;
    
    public Edge(String x, String y, String z) {
        this.startState = Integer.parseInt(x);
        this.label = y;
        this.endState = Integer.parseInt(z);
    }
    
    @Override
    public String toString() {
        String start = "Start state: " + this.startState + "\n";
        String lab = "Label: " + this.label + "\n";
        String end = "End state: " + this.endState;
        return "Edge: " + "\n" + start + lab + end;
    }
}
