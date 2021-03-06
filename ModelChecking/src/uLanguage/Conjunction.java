package uLanguage;

/**
 *
 * @author ruudandriessen
 */
public class Conjunction extends LogicOperator {
    
    public Conjunction() {
        this.operator = uOperator.uOperations.AND;
    }
    
    public Conjunction(uFormula leftFormula, uFormula rightFormula) {
        this.leftFormula = leftFormula;
        this.rightFormula = rightFormula;
        this.operator = uOperator.uOperations.AND;
        
        this.addChild(leftFormula);
        this.addChild(rightFormula);
    }
    
    @Override
    public String toString() {
        return "(" + leftFormula + " && " + rightFormula + ")";
    }
}
