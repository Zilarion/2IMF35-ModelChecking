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
    HashMap<String, Boolean> variables;
    
    public Environment() {
        variables = new HashMap<>();
    }
    
    public void addVariable(String var, boolean value) {
        variables.put(var, value);
    }
    
    public HashSet<State> getVariable() {
        return new HashSet<State>();
    }
}
