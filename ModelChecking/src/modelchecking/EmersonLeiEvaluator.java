/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;
import java.util.HashSet;
import uLanguage.uFormula;

/**
 *
 * @author ruudandriessen
 */
public class EmersonLeiEvaluator {
    public static HashSet<State> evaluate(uFormula f, LTS lts) {
//        init(f);
        return evaluate(f, lts, new Environment());
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
//            case DIAMOND:
//            case BOX:
//                result = NaiveEvaluator.evaluate(f, lts, e);
//                break;
//            case LFP:
//                
//                
//                result = new HashSet<>();
//                do {                 
//                    e.setVariable(f.leftChild.def, result);
//                    result = evaluate(f.rightChild, lts, e);
//                } while(!intersection(result, e.getVariable(f.leftChild.def)).equals(result));
//                
//                e.setVariable(f.leftChild.def, result);
//                break;
//            case GFP:
//                if (f.scope == Scope.LFP) {
//                    
//                }
//                
//                result = new HashSet<>(lts.getStates());
//                do {
//                    e.setVariable(f.leftChild.def, result);
//                    result = evaluate(f.rightChild, lts, e);
//                } while (!intersection(result, e.getVariable(f.leftChild.def)).equals(result));
//                e.setVariable(f.leftChild.def, result);
//                break;
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
