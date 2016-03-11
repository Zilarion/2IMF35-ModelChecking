package uLanguage;

import static uLanguage.uOperator.uOperations;

/**
 *
 * @author ruudandriessen
 */
public class Variable extends uFormula {
    public String name;
    
    public Variable(String name) {
        this.name = name;
        this.operator = uOperations.VARIABLE;
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
}
