package uLanguage;

/**
 *
 * @author ruudandriessen
 */
public class GFP extends uFormula {
    public Variable variable;
    public uFormula formula;
    
    public GFP(Variable variable, uFormula formula) {
        this.variable = variable;
        this.formula = formula;
        this.operator = uOperator.uOperations.GFP;
    }
    
    @Override
    public String toString() {
        return "nu " + variable + "." + formula;
    }
}
