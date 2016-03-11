package uLanguage;

import uLanguage.uOperator.uOperations;

/**
 *
 * @author ruudandriessen
 */
public class Box extends uFormula {
    public String action;
    public uFormula subFormula;
    
    public Box(String action, uFormula subFormula) {
        this.action = action;
        this.subFormula = subFormula;
        this.operator = uOperations.BOX;
    }
    
    @Override
    public String toString() {
        return "[" + action + "]" + subFormula;
    }
}
