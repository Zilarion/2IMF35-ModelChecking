package uLanguage;

/**
 *
 * @author ruudandriessen
 */
public class Diamond extends uFormula {
    public String action;
    public uFormula subFormula;
    
    public Diamond(String action, uFormula subFormula) {
        this.action = action;
        this.subFormula = subFormula;
        this.operator = uOperator.uOperations.DIAMOND;
        this.addChild(subFormula);
    }
    
    @Override
    public String toString() {
        return "[" + action + "]" + subFormula;
    }
}
