/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;
import java.util.HashSet;
import uLanguage.Box;
import uLanguage.Conjunction;
import uLanguage.Diamond;
import uLanguage.Disjunction;
import uLanguage.GFP;
import uLanguage.LFP;
import uLanguage.Negation;
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
                // return Empty
                result = new HashSet<>();
                break;
            case TRUE:
                // return S
                result = lts.getStates();
                break;
            case NEGATION:
                // return S \ evaluate(!f)
                HashSet<State> S = new HashSet<>(lts.getStates());
                S.removeAll(evaluate(((Negation) f).formula, lts, e));
                result = S;
                break;
            case VARIABLE:
                // Get the value of the variable
                result = e.getVariable((Variable) f);
                break;
            case AND:
                // Return conjunction
                Conjunction conjun = (Conjunction) f;
                result = intersection(evaluate(conjun.leftFormula, lts, e), 
                                    evaluate(conjun.rightFormula, lts, e));
                break;
            case OR:
                // Return union
                Disjunction disjun = (Disjunction) f;
                result = union(evaluate(disjun.leftFormula, lts, e), 
                             evaluate(disjun.rightFormula, lts, e));
                break;
            case DIAMOND:
                // Instead of evaluating <a>f return evaluate([a]!f)
                
                // Create box and negate functions
                Diamond diamond = (Diamond) f;
                
                Negation negatedSubFormula = new Negation(diamond.subFormula);  // !f
                Box newBox = new Box(diamond.action, negatedSubFormula);        // [a]!f
                Negation negatedNewBox = new Negation(newBox);                  // ![a]!f
                 
                // Evaluate box operation
                result = evaluate(negatedNewBox, lts, e);
                break;
            case BOX:
                // Solve subform
                Box box = (Box) f;
                HashSet<State> subFormResult = evaluate(box.subFormula, lts, e);
                HashSet<State> values = new HashSet<>(lts.getStates());
                for (State s : lts.getStates()) {
                    HashSet<Edge> edges = s.getEdgesWithLabel(box.action);
                    for (Edge edge : edges) {
                        if (!subFormResult.contains(edge.getEnd()))
                            values.remove(s);
                    }
                }
                result = values;      
                break;
            case LFP:
                LFP lfp = (LFP) f;
                if (lfp.parent != null && lfp.parent.operator == uOperations.LFP) {
                    System.out.println("Resetting open subformula lfp");
                    // reset open subformulae of form nu Xk.g set env[k]=true
                    for (uFormula childFormula : lfp.getChildrenFormulas(uOperations.LFP)) {
                        GFP innerGFP = (GFP) childFormula;
                        e.setVariable(innerGFP.variable, new HashSet<>());
                    }
                }

                HashSet<State> Xoldlfp;
                do {
                    Xoldlfp = e.getVariable(lfp.variable);
                    e.setVariable(lfp.variable, evaluate(lfp.formula, lts, e));
                } while (Xoldlfp.size() != e.getVariable(lfp.variable).size());
                result = e.getVariable(lfp.variable);
                break;
            case GFP:
                GFP gfp = (GFP) f;
                if (gfp.parent != null && gfp.parent.operator == uOperations.GFP) {
                    System.out.println("Resetting open subformula gfp");
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
                result = e.getVariable(gfp.variable);
                break;
            default: 
                System.out.println("Warning - unknown operator to evaluate: " + f.operator);
                result = new HashSet<>();
                break;
        }
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
