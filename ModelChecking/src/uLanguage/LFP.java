package uLanguage;

import java.util.List;
import uLanguage.uOperator.uOperations;

/**
 *
 * @author ruudandriessen
 */
public class LFP extends FixedPoint {
    public LFP(Variable variable, uFormula formula) {
        this.variable = variable;
        this.formula = formula;
        this.operator = uOperations.LFP;
        
        this.addChild(variable);
        this.addChild(formula);
    }
    
    @Override
    public String toString() {
        return "mu " + variable + "." + formula;
    }

    @Override
    public int getAlternationDepth() {
        int max = 0;
        for (uFormula f : this.getChildrenFormulas(uOperations.GFP)) {
            int ad = f.getAlternationDepth();
            if (ad > max) {
                max = ad;
            }
        }
        return 1 + max;
    }

    @Override
    public int getDependentAlternationDepth() {
        int subFormMax = -1;
        for (uFormula f : this.getChildrenFormulas(uOperations.GFP)) {
            List<uFormula> variables = f.getChildrenFormulas(uOperations.VARIABLE);
            
            if (variables.contains(this.variable)) {
                subFormMax = Math.max(subFormMax, f.getDependentAlternationDepth());
            }
        }
        return Math.max(formula.getDependentAlternationDepth(), 1 + subFormMax);
    }
}
