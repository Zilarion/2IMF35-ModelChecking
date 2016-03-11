package uLanguage;

import uLanguage.uOperator.uOperations;

/**
 *
 * @author ruudandriessen
 */
public class LFP extends uFormula {
    public Variable variable;
    public uFormula formula;
    
    public LFP(Variable variable, uFormula formula) {
        this.variable = variable;
        this.formula = formula;
        this.operator = uOperations.LFP;
    }
    
    @Override
    public String toString() {
        return "mu " + variable + "." + formula;
    }
}
