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

/**
 *
 * @author ruudandriessen
 */
public class NaiveEvaluator {
    
    public static HashSet<State> evaluate(uFormula f, LTS lts) {
        return NaiveEvaluator.evaluate(f, lts, new Environment());
    }
    
    public static HashSet<State> evaluate(uFormula f, LTS lts, Environment e) {
        HashSet<State> result;
        switch (f.operator) {
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
                                    evaluate(conjun.leftFormula, lts, e));
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
                result = new HashSet<>();
                do {                 
                    e.setVariable(lfp.variable, result);
                    result = evaluate(lfp.formula, lts, e);
                } while(!intersection(result, e.getVariable(lfp.variable)).equals(result));
                
                e.setVariable(lfp.variable, result);
                break;
            case GFP:
                GFP gfp = (GFP) f;
                result = new HashSet<>(lts.getStates());
                do {
                    e.setVariable(gfp.variable, result);
                    result = evaluate(gfp.formula, lts, e);
                } while (!intersection(result, e.getVariable(gfp.variable)).equals(result));
                
                e.setVariable(gfp.variable, result);
                break;
            default: 
                System.out.println("Warning - unknown operator to evaluate: " + f.operator);
                result = new HashSet<>();
                break;
        }
//        System.out.println("Result: " + f.operator);
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
