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
    public Node startState;
    public String label;
    public Node endState;
    
    public Edge(Node from, String l, Node to) {
        this.startState = from;
        this.label = l;
        this.endState = to;
    }
    
    @Override
    public String toString() {
        String start = "Start state: " + this.startState + "\n";
        String lab = "Label: " + this.label + "\n";
        String end = "End state: " + this.endState;
        return "Edge: " + "\n" + start + lab + end;
    }
}
