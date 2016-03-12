/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;
import java.util.HashSet;
import uLanguage.GFP;
import uLanguage.LFP;
import uLanguage.Variable;
import uLanguage.Variable.Bound;
import uLanguage.uFormula;
import uLanguage.uOperator.uOperations;

/**
 *
 * @author ruudandriessen
 */
public class EmersonLeiEvaluator {
    public static HashSet<State> evaluate(uFormula f, LTS lts) {
        Environment e = new Environment();
        init(f, lts, e);
        return evaluate(f, lts, e);
    }
    
    public static void init(uFormula f, LTS lts, Environment e) {
        for (Variable v : f.getVariables()) {
            if (v.calculateBound(f)) {
                if (v.boundBy == Bound.LFPBound) {
                    e.setVariable(v, new HashSet<>());
                } else if (v.boundBy == Bound.GFPBound) {
                    e.setVariable(v, new HashSet<>(lts.getStates()));
                }
            }
        }
    }
    
    public static HashSet<State> evaluate(uFormula f, LTS lts, Environment e) {
        HashSet<State> result;
        switch (f.operator) {
            // All operations are the same except for LFP and GFP
            case FALSE:
            case TRUE:
            case NEGATION:
            case VARIABLE:
            case AND:
            case OR:
            case DIAMOND:
            case BOX:
                return NaiveEvaluator.evaluate(f, lts, e);
            case LFP:
                LFP lfp = (LFP) f;
                if (lfp.parent.operator == uOperations.LFP) {
                        // reset open subformulae of form nu Xk.g set env[k]=true
                        for (uFormula childFormula : lfp.getChildrenFormulas(uOperations.GFP)) {
                            GFP innerGFP = (GFP) childFormula;
                            e.setVariable(innerGFP.variable, new HashSet<>(lts.getStates()));
                        }
                }

                HashSet<State> Xoldlfp;
                do {
                        Xoldlfp = e.getVariable(lfp.variable);
                        e.setVariable(lfp.variable, evaluate(lfp.formula, lts, e));
                } while (Xoldlfp.size() != e.getVariable(lfp.variable).size());
                return e.getVariable(lfp.variable);
            case GFP:
                GFP gfp = (GFP) f;
                if (gfp.parent.operator == uOperations.GFP) {
                        // reset open subformulae of form nu Xk.g set env[k]=true
                        for (uFormula childFormula : gfp.getChildrenFormulas(uOperations.GFP)) {
                            GFP innerGFP = (GFP) childFormula;
                            e.setVariable(innerGFP.variable, new HashSet<>(lts.getStates()));
                        }
                }

                HashSet<State> Xoldgfp;
                do {
                        Xoldgfp = e.getVariable(gfp.variable);
                        e.setVariable(gfp.variable, evaluate(gfp.formula, lts, e));
                } while (Xoldgfp.size() != e.getVariable(gfp.variable).size());
                return e.getVariable(gfp.variable);
            default: 
                System.out.println("Warning - unknown operator to evaluate: " + f.operator);
                result = new HashSet<>();
                break;
        }
        System.out.println("Result: " + f.operator);
//        System.out.println(result);
        return result;
    }
    
    public static HashSet<State> union(HashSet<State> s1, HashSet<State> s2) {
        HashSet<State> s = new HashSet<>(s1);
        s.addAll(s2);
        return s;
    }
    
    public static HashSet<State> intersection(HashSet<State> s1, HashSet<State> s2) {
        HashSet<State> s = new HashSet<>(s1);
        s.retainAll(s2);
        return s;
    }
}
