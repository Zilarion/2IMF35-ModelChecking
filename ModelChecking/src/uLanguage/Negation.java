package uLanguage;

import uLanguage.uOperator.uOperations;

/**
 *
 * @author ruudandriessen
 */
public class Negation extends uFormula {
    public uFormula formula;
    public Negation(uFormula formula) {
        this.formula = formula;
        this.operator = uOperations.NEGATION;
        
        this.addChild(formula);
    }
    
    @Override
    public String toString() {
        return "!(" + formula + ")";
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
