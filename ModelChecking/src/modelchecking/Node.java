/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Hein
 */
public class Node {
    public int id;
    public Set<Edge> outgoingEdges;
    
    public Node(String n) {
        this.id = Integer.parseInt(n);
        this.outgoingEdges = new HashSet();
    }
    
    public void addEdge(Edge e) {
        this.outgoingEdges.add(e);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!Node.class.isAssignableFrom(obj.getClass())) return false;
        final Node n = (Node) obj;
        return (this.id == n.id);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
        return hash;
    }
    
    @Override
    public String toString() {
        return Integer.toString(this.id);
    }
}
