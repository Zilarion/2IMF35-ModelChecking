package uLanguage;

/**
 *
 * @author ruudandriessen
 */
public abstract class LogicOperator extends uFormula {
    public uFormula leftFormula, rightFormula;

    public void setFormulas(uFormula leftFormula, uFormula rightFormula) {
        this.leftFormula = leftFormula;
        this.rightFormula = rightFormula;
        
        this.addChild(leftFormula);
        this.addChild(rightFormula);
    }
}
