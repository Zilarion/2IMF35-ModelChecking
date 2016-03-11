/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

import java.util.HashSet;
import modelchecking.uOperator.uOperations;

/**
 *
 * @author ruudandriessen
 */
public class NaiveEvaluator {
    public static HashSet<State> evaluate(uFunction f, LTS lts, Environment e) {
        switch (f.operator) {
            case FALSE:
                // return Empty
                return new HashSet<>();
            case TRUE:
                // return S
                return lts.getStates();
            case NEGATION:
                // return S \ evaluate(!f)
                HashSet<State> S = lts.getStates();
                S.removeAll(evaluate(f.leftChild, lts, e));
                return S;
            case VARIABLE:
                // Get the value of the variable
                return e.getVariable(f.def);
            case ACTION:
                // TODO
                return new HashSet<>();
            case AND:
                // Return intersection
                return intersection(evaluate(f.leftChild, lts, e), 
                                    evaluate(f.rightChild, lts, e));
            case OR:
                // Return union
                return union(evaluate(f.leftChild, lts, e), 
                             evaluate(f.rightChild, lts, e));
            case DIAMOND:
                // Instead of evaluating <a>f return evaluate([a]!f)
                
                // Create box and negate functions
                uFunction box = new uFunction(uOperations.BOX);
                uFunction negatedFunction = new uFunction(uOperations.NEGATION);
                
                // Keep the same label
                box.setLeftChild(f.leftChild);
                // Set the function to be the same as before
                negatedFunction.setLeftChild(f.rightChild);
                
                // Set the box right child to the negation
                box.setRightChild(negatedFunction);
                
                // Evaluate box operation
                return evaluate(box, lts, e);
            case BOX:
                // Solve subform
                HashSet<State> subForm = evaluate(f.rightChild, lts, e);
                HashSet<State> values = new HashSet<>();
                for (State s : lts.getStates()) {
                    HashSet<Edge> edges = s.getEdgesWithLabel(f.leftChild.def);
                    System.out.println(edges);
                    for (Edge edge : edges) {
                        if (subForm.contains(edge.getEnd()))
                            values.add(edge.getEnd());
                    }
                }
                return values;            
            case LFP:
                e.setVariable(f.leftChild.def, new HashSet<>());
                e.setVariable(f.leftChild.def, evaluate(f.rightChild, lts, e));
                return new HashSet<>();
            case GFP:
                e.setVariable(f.leftChild.def, lts.getStates());
                return new HashSet<>();
            default: 
                System.out.println("Warning - unknown operator to evaluate: " + f.operator);
                return new HashSet<>();
        }
    }
    
    public static HashSet<State> union(HashSet<State> s1, HashSet<State> s2) {
        s1.addAll(s2);
        return s1;
    }
    
    public static HashSet<State> intersection(HashSet<State> s1, HashSet<State> s2) {
        s1.retainAll(s2);
        return s1;
    }
}
