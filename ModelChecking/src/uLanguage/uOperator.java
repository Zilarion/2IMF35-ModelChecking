/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uLanguage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ruudandriessen
 */
public class uOperator {
    public static enum uOperations {
        FALSE, TRUE, VARIABLE, AND, OR, DIAMOND, BOX, LFP, GFP, NEGATION
    }
    
    static Pattern falsePattern = Pattern.compile("^[\\s]*false[\\s]*$");
    static Pattern truePattern = Pattern.compile("^[\\s]*true[\\s]*$");
    static Pattern variablePattern = Pattern.compile("^[\\s]*([A-Z])[\\s]*$");
    static Pattern actionPattern = Pattern.compile("^[\\s]*[a-z]*[\\s]*$");
    static Pattern andPattern = Pattern.compile("^\\(([\\S\\s]+)\\&\\&([\\S\\s]+)\\)$");
    static Pattern orPattern = Pattern.compile("^\\(([\\S\\s]+)\\|\\|([\\S\\s]+)\\)$");
    static Pattern diamondPattern = Pattern.compile("^\\<([a-z]+)\\>([\\s\\S]+)$");
    static Pattern boxPattern = Pattern.compile("^\\[([a-z]+)\\]([\\s\\S]+)$");
    static Pattern lfpPattern = Pattern.compile("^mu ([A-Z]).([\\s\\S]+)$");
    static Pattern gfpPattern = Pattern.compile("^nu ([A-Z]).([\\s\\S]+)$");
    
    public static Pattern getPattern(uOperations op) {
        switch (op) {
            case FALSE:
                return falsePattern;
            case TRUE:
                return truePattern;
            case VARIABLE:
                return variablePattern;
//            case ACTION: 
//                return actionPattern;
            case AND:
                return andPattern;
            case OR:
                return orPattern;
            case DIAMOND: 
                return diamondPattern;
            case BOX:
                return boxPattern;
            case LFP:
                return lfpPattern;
            case GFP:
                return gfpPattern;
        }
        System.out.println("Error - Could not find operator pattern for: " + op);
        return null;
    }
    
    public static uOperations getOperator(String input) {
        for (uOperations op : uOperations.values()) {
            if (op == uOperations.NEGATION) continue;
            Matcher m = getPattern(op).matcher(input);
            if (m.find()) {
                return op;
            }
        }
        System.out.println("Warning no operator found for input: " + input);
        return null;
    }
}

