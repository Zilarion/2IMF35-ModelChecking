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
import uLanguage.uFormula;
import uLanguage.uFormula.Bound;
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
            // TODO f.getVariables() still gets duplicates 
            // this is okay since the environment works with strings but not ideal
            // Especially with long formulas
            if (v.calculateBound(f)) {
                if (v.boundBy != null && v.boundBy .operator == uOperations.LFP) {
                    e.setVariable(v, new HashSet<>());
                } else if (v.boundBy != null && v.boundBy.operator == uOperations.GFP) {
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
                if (lfp.getBinder() == Bound.GFPBound) {
                    // Get all children LFPs
                    for (uFormula childFormula : lfp.getChildrenFormulas(uOperations.LFP)) {
                        LFP innerLFP = (LFP) childFormula;
                        // Check if we have free variables and reset if we do
                        if (innerLFP.freeVariables().size() > 0) {
                            e.setVariable(innerLFP.variable, new HashSet<>());
                        }
                    }
                }

                HashSet<State> Xoldlfp;
                HashSet<State> intersectionLfp;
                do {
                    Xoldlfp = e.getVariable(lfp.variable);
                    e.setVariable(lfp.variable, evaluate(lfp.formula, lts, e));
                    intersectionLfp = intersection(Xoldlfp, e.getVariable(lfp.variable));
                } while (!(intersectionLfp.equals(Xoldlfp) && intersectionLfp.equals(e.getVariable(lfp.variable))));
                result = e.getVariable(lfp.variable);     
                break;
            case GFP:
                GFP gfp = (GFP) f;
                if (gfp.getBinder() == Bound.LFPBound) {
                    for (uFormula childFormula : gfp.getChildrenFormulas(uOperations.GFP)) {
                        GFP innerGFP = (GFP) childFormula;
                        if (innerGFP.freeVariables().size() > 0) {
                            e.setVariable(innerGFP.variable, new HashSet<>(lts.getStates()));
                        }
                    }
                }

                HashSet<State> Xoldgfp;
                HashSet<State> intersectionGfp;
                do {
                    Xoldgfp = e.getVariable(gfp.variable);
                    e.setVariable(gfp.variable, evaluate(gfp.formula, lts, e));
                    intersectionGfp = intersection(Xoldgfp, e.getVariable(gfp.variable));
                } while (!(intersectionGfp.equals(Xoldgfp) && intersectionGfp.equals(e.getVariable(gfp.variable))));
                result = e.getVariable(gfp.variable);
                break;
            default: 
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
