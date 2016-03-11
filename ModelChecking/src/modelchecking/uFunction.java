/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modelchecking.uOperator.uOperations;

/**
 *
 * @author ruudandriessen
 */
public class uFunction {
    String def;
    uOperations operator;
    uFunction leftChild, rightChild;
    int depth;
    
    public uFunction(String def) {
        this(def, 0);
    }
    
    public uFunction(String def, int depth) {
        this.def = def;
        this.depth = depth;
        this.setOperator(uOperator.getOperator(def));
    }
    
    public uFunction(String def, uOperations operator) {
        this.def = def;
        this.depth = 0;
        this.operator = operator;
    }
    
    public uFunction(uOperations operator) {
        this.def = "";
        this.depth = 0;
        this.operator = operator;
    }
    
    public void setOperator(uOperations op) {
        this.operator = op;
        
        if (op == null) {
            System.out.println("Error: null operator for definition: " + def);
            return;
        }
        Pattern p  = uOperator.getPattern(op);
        
        if (p == null) {
            System.out.println("Error, invalid pattern");
        } else {
            Matcher m = p.matcher(def);
        
            switch (op) {
                case FALSE:
                case TRUE:
                case VARIABLE:
                case ACTION:
                    // Nothing to be done
                break;
                case AND:
                case OR:
                case DIAMOND:
                case BOX:
                case LFP:
                case GFP:
                    if (m.find()) {
                        String lChild = m.group(1) == null ? m.group(3) : m.group(1);
                        String rChild = m.group(2) == null ? m.group(4) : m.group(2);
                        leftChild = new uFunction(lChild, depth + 1);
                        rightChild = new uFunction(rChild, depth + 1);
                    }
                break;
            }
        }
    }
    
    public List<String> getVariables() {
        List<String> variables = new ArrayList<>();
        switch(this.operator) {
            case VARIABLE:
                variables.add(this.def);
            break;
            case AND:
            case OR:
            case DIAMOND:
            case BOX:
            case LFP:
            case GFP:
                variables.addAll(leftChild.getVariables());
                variables.addAll(rightChild.getVariables());
            break;
        }
        return variables;
    }
    
    public void setLeftChild(uFunction f) {
        this.leftChild = f;
    }
    
    public void setRightChild(uFunction f) {
        this.rightChild = f;
    }
    
    @Override
    public String toString() {
        String depthTab = "";
        for(int i = 0; i < depth; i++) {
            depthTab += '\t';
        }
        String lChild = (leftChild == null) ? "null" :  "" + leftChild + "";
        String rChild = (rightChild == null) ? "null" :  "" + rightChild + "";
        String result = "uFunction {\n" +
                        depthTab + "\tdef: " + def + ",\n" +
                        depthTab + "\toperator: " + operator + ",\n" +
                        depthTab + "\tleftChild: " + lChild + ",\n" +
                        depthTab + "\trightChild: " + rChild + "\n" + 
                        depthTab + "}";
        return result;
    }
}
