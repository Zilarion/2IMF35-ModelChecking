package uLanguage;

import uLanguage.uOperator.uOperations;

/**
 *
 * @author ruudandriessen
 */
public abstract class uFormula {
    protected uFormula parent;
    public uOperations operator;
    
    public void setParent(uFormula parent) {
        this.parent = parent;
    }
}