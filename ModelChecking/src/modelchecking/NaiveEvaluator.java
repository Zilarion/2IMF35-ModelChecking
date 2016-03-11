/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

import java.util.HashSet;

/**
 *
 * @author ruudandriessen
 */
public class NaiveEvaluator {
    public HashSet<State> evaluateNaive(uFunction f, LTS lts, Environment e) {
        switch (f.operator) {
            case FALSE:
                return new HashSet<>();
            case TRUE:
                return lts.getStates();
            case VARIABLE:
                return e.getVariable(f.def);
            case ACTION:
            case AND:
            case OR:
            case DIAMOND:
            case BOX:
            case LFP:
            case GFP:
            default: 
                return new HashSet<>();
        }
    }
}
