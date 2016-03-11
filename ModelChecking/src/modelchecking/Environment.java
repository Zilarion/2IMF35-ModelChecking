/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author ruudandriessen
 */
class Environment {
    HashMap<String, HashSet<State>> variables;
    
    public Environment() {
        variables = new HashMap<>();
    }
    
    public void setVariable(String key, HashSet<State> value) {
        variables.put(key, value);
    }
    
    public HashSet<State> getVariable(String var) {
        if (variables.containsKey(var)) 
            return variables.get(var);
        return new HashSet<>();
    }
}
