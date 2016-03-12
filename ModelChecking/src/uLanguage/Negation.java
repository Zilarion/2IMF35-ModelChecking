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
}
