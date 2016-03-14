package uLanguage;

import java.util.HashSet;
import uLanguage.uOperator.uOperations;

/**
 *
 * @author ruudandriessen
 */
public abstract class FixedPoint extends uFormula {
    public Variable variable;
    public uFormula formula;
            
    @Override
    public int getNestingDepth() {
        return formula.getNestingDepth() + 1;
    }
    
    public HashSet<Variable> freeVariables() {
        HashSet<Variable> result = new HashSet<>();
        for (uFormula f : this.getChildrenFormulas(uOperations.VARIABLE)) {
            Variable v = (Variable) f;
            if (v.boundBy != null && !v.boundBy.equals(this)) {
                result.add(v);
            }
        }
        return result;
    }
}
