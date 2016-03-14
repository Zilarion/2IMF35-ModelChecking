package uLanguage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import uLanguage.uOperator.uOperations;

/**
 *
 * @author ruudandriessen
 */
public abstract class uFormula {
    public enum Bound {LFPBound, GFPBound, NotBound};
    
    public uFormula parent;
    public uOperations operator;
    public List<uFormula> children;
    
    public uFormula() {
        this.children = new ArrayList<>();
    }
    
    public void setParent(uFormula parent) {
        this.parent = parent;  
    }
    
    protected void addChild(uFormula f) {
        this.children.add(f);
        f.setParent(this);
    }
    
    public Set<Variable> getVariables() {
        Set<Variable> variables = new HashSet<>();
        if (this.operator == uOperations.VARIABLE) {
            variables.add((Variable) this);
        }
        for (uFormula f : children) {
            variables.addAll(f.getVariables());
        }
        return variables;
    }
    
    public Bound getBinder() {
        uFormula searchParent = this.parent;
        while (searchParent != null) {
            if (searchParent.operator == uOperations.GFP) {
                return Bound.GFPBound;
            }
            if (searchParent.operator == uOperations.LFP) {
                return Bound.LFPBound;
            }
            
            searchParent = searchParent.parent;
        }
        return Bound.NotBound;
    }
    
    public List<uFormula> getChildrenFormulas(uOperations op) {
        List<uFormula> matches = new ArrayList<>();
        if (this.operator == op) {
            matches.add(this);
        }
        for (uFormula f : children) {
            matches.addAll(f.getChildrenFormulas(op));
        }
        return matches;
    }
    
    public abstract int getNestingDepth();
    public abstract int getAlternationDepth();
    public abstract int getDependentAlternationDepth();
}