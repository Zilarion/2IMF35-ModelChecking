package uLanguage;

import java.util.Objects;
import static uLanguage.uOperator.uOperations;

/**
 *
 * @author ruudandriessen
 */
public class Variable extends uFormula {
    public String name;
    public uFormula boundBy;
    
    public Variable(String name) {
        this.name = name;
        this.operator = uOperations.VARIABLE;
    }
    
    public boolean calculateBound(uFormula f) {
        uFormula searchParent = this.parent;
        do {
            if (searchParent.operator == uOperations.GFP || searchParent.operator == uOperations.LFP) {
                boundBy = searchParent;
                return true;
            }
            searchParent = searchParent.parent;
        } while (parent != f);
        boundBy = null;
        return false;
    }
    
    public boolean isBound(uFormula f) {        
        uFormula searchParent = this.parent;
        do {
            if (searchParent.operator == uOperations.GFP) return true;
            if (searchParent.operator == uOperations.LFP) return true;
            searchParent = searchParent.parent;
        } while (parent != f);
        return false;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Variable.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Variable other = (Variable) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
    @Override
    public int getNestingDepth() {
        return 0;
    }
    
    @Override
    public int getAlternationDepth() {
        return 0;
    }

    @Override
    public int getDependentAlternationDepth() {
        return 0;
    }
}
