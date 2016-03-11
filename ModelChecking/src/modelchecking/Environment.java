/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

import java.util.HashMap;
import java.util.HashSet;
import uLanguage.Variable;

/**
 *
 * @author ruudandriessen
 */
class Environment {
    HashMap<String, HashSet<State>> variables;
    
    public Environment() {
        variables = new HashMap<>();
    }
    
    public void setVariable(Variable key, HashSet<State> value) {
        variables.put(key.name, value);
    }
    
    public HashSet<State> getVariable(Variable var) {
        if (variables.containsKey(var.name)) 
            return variables.get(var.name);
        return new HashSet<>();
    }
}
