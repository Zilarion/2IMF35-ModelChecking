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
        this.addChild(subFormula);
    }
    
    @Override
    public String toString() {
        return "[" + action + "]" + subFormula;
    }

    @Override
    public int getNestingDepth() {
        return subFormula.getNestingDepth();
    }

    @Override
    public int getAlternationDepth() {
        return subFormula.getAlternationDepth();
    }

    @Override
    public int getDependentAlternationDepth() {
        return subFormula.getDependentAlternationDepth();
    }
}
