package uLanguage;

import uLanguage.uOperator.uOperations;

/**
 *
 * @author ruudandriessen
 */
public class Proposition extends uFormula {
    public boolean value;
    public Proposition(boolean value) {
        this.value = value;
        this.operator = this.value ? uOperations.TRUE : uOperations.FALSE;
    }
    
    @Override
    public String toString() {
        return value ? "true" : "false";
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

