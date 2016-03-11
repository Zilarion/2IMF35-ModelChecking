package uLanguage;

/**
 *
 * @author ruudandriessen
 */
public class Disjunction extends uFormula  {
    public uFormula leftFormula, rightFormula;
    
    public Disjunction(uFormula leftFormula, uFormula rightFormula) {
        this.leftFormula = leftFormula;
        this.rightFormula = rightFormula;
        this.operator = uOperator.uOperations.OR;
    }
    
    @Override
    public String toString() {
        return "(" + leftFormula + " || " + rightFormula + ")";
    }
}
