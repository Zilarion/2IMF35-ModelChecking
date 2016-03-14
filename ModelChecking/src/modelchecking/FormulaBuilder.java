package modelchecking;

import java.util.Arrays;
import java.util.HashSet;
import uLanguage.Box;
import uLanguage.Conjunction;
import uLanguage.Diamond;
import uLanguage.Disjunction;
import uLanguage.FixedPoint;
import uLanguage.GFP;
import uLanguage.LFP;
import uLanguage.LogicOperator;
import uLanguage.Proposition;
import uLanguage.Variable;
import uLanguage.uFormula;

/**
 *
 * @author ruudandriessen
 */
public class FormulaBuilder {
    final static HashSet<Character> trueLiteral = new HashSet<>(Arrays.asList('t'));
    final static HashSet<Character> falseLiteral = new HashSet<>(Arrays.asList('f'));
    final static HashSet<Character> recursionVariable = new HashSet<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));
    final static HashSet<Character> logicFormula = new HashSet<>(Arrays.asList('('));
    final static HashSet<Character> muFormula = new HashSet<>(Arrays.asList('m'));
    final static HashSet<Character> nuFormula = new HashSet<>(Arrays.asList('n'));
    final static HashSet<Character> diamondFormula = new HashSet<>(Arrays.asList('<'));
    final static HashSet<Character> boxFormula = new HashSet<>(Arrays.asList('['));
    final static HashSet<Character> formulaFirstSet = newHashSetUnion(trueLiteral, falseLiteral, recursionVariable, logicFormula, muFormula, nuFormula, diamondFormula, boxFormula);
    final static HashSet<Character> andOperatorFirstSet = new HashSet<>(Arrays.asList('&'));
    final static HashSet<Character> orOperatorFirstSet = new HashSet<>(Arrays.asList('|'));
    final static HashSet<Character> operatorSet = newHashSetUnion(andOperatorFirstSet, orOperatorFirstSet);
    final static HashSet<Character> actionNameFirstSet = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));
    
    static Integer i;
    
    public static HashSet<Character> newHashSetUnion(HashSet<Character>... sets) {
        HashSet<Character> set = new HashSet<>();
        for (HashSet<Character> s : sets) {
            set.addAll(s);
        }
        return set;
    }

    public static uFormula build(String s) throws uParseException {
        i = 0;
        if (formulaFirstSet.contains(s.charAt(i))) {
            uFormula f = parseFormula(s);
            if (i < s.length() - 1) {
                throw new uParseException();
            } else {
                return f;
            }
        } else {
            throw new uParseException();
        }
    }
    
    private static uFormula parseFormula(String s) throws uParseException {
        Character next = s.charAt(i);
        if (trueLiteral.contains(next)) {
            return parseTrueLiteral(s);
        } else if (falseLiteral.contains(next)) {
            return parseFalseLiteral(s);
        } else if (recursionVariable.contains(next)) {
            return parseRecursionVariable(s);
        } else if (logicFormula.contains(next)) {
            return parseLogicFormula(s);
        } else if (muFormula.contains(next)) {
            return parseMuFormula(s);
        } else if (nuFormula.contains(next)) {
            return parseNuFormula(s);
        } else if (diamondFormula.contains(next)) {
            return parseDiamondFormula(s);
        } else if (boxFormula.contains(next)) {
            return parseBoxFormula(s);
        }
        throw new uParseException();
    }
    
    private static Proposition parseTrueLiteral(String s) throws uParseException {
        expect(s, "true");
        return new Proposition(true);
    }
    
    private static Proposition parseFalseLiteral(String s) throws uParseException {
        expect(s, "false");
        return new Proposition(false);
    }
    
    private static Variable parseRecursionVariable(String s) {
        Variable v = new Variable(Character.toString(s.charAt(i)));
        i += 1;
        return v;
    }
    
    private static LogicOperator parseLogicFormula(String s) throws uParseException {
        expect(s, "(");
        uFormula f, r;
        LogicOperator op;
        if (formulaFirstSet.contains(s.charAt(i))) {
            f = parseFormula(s);
        } else {
            throw new uParseException();
        }
        if (operatorSet.contains(s.charAt(i))) {
            op = parseOperator(s);
        } else {
            throw new uParseException();
        }
        if (formulaFirstSet.contains(s.charAt(i))) {
            r = parseFormula(s);
        } else {
            throw new uParseException();
        }
        
        expect(s, ")");
        op.setFormulas(f, r);
        return op;
    }
    
    private static LogicOperator parseOperator(String s) throws uParseException {
        if (andOperatorFirstSet.contains(s.charAt(i))) {
            return parseLogicAndOperator(s);
        } else if (orOperatorFirstSet.contains(s.charAt(i))) {
            return parseLogicOrOperator(s);
        }
        throw new uParseException();
    }
    
    private static LogicOperator parseLogicAndOperator(String s) throws uParseException {
        expect(s, "&&");
        return new Conjunction();
    }
    
    private static LogicOperator parseLogicOrOperator(String s) throws uParseException {
        expect(s, "||");
        return new Disjunction();
    }
    
    private static FixedPoint parseMuFormula(String s) throws uParseException {
        expect(s, "mu ");
        Variable r;
        uFormula f;
        if (recursionVariable.contains(s.charAt(i))) {
            r = parseRecursionVariable(s);
        } else {
            throw new uParseException();
        }
        expect(s, ".");
        if (formulaFirstSet.contains(s.charAt(i))) {
            f = parseFormula(s);
        } else {
            throw new uParseException();
        }
        return new LFP(r, f);
    }
    
    private static FixedPoint parseNuFormula(String s) throws uParseException {
        expect(s, "nu ");
        Variable r;
        uFormula f;
        if (recursionVariable.contains(s.charAt(i))) {
            r = parseRecursionVariable(s);
        } else {
            throw new uParseException();
        }
        expect(s, ".");
        if (formulaFirstSet.contains(s.charAt(i))) {
            f = parseFormula(s);
        } else {
            throw new uParseException();
        }
        return new GFP(r, f);
    }
    
    private static uFormula parseBoxFormula(String s) throws uParseException {
        expect(s, "[");
        String action;
        if (actionNameFirstSet.contains(s.charAt(i))) {
            action = parseActionName(s);
        } else {
            throw new uParseException();
        }
        expect(s, "]");
        
        uFormula f;
        if (formulaFirstSet.contains(s.charAt(i))) {
            f = parseFormula(s);
        } else {
            throw new uParseException();
        }
        return new Box(action, f);
    }
    
    private static uFormula parseDiamondFormula(String s) throws uParseException {
        expect(s, "<");
        String action;
        if (actionNameFirstSet.contains(s.charAt(i))) {
            action = parseActionName(s);
        } else {
            throw new uParseException();
        }
        expect(s, ">");
        
        uFormula f;
        if (formulaFirstSet.contains(s.charAt(i))) {
            f = parseFormula(s);
        } else {
            throw new uParseException();
        }
        return new Diamond(action, f);
    }
    
    private static String parseActionName(String s) {
        String action = "";
        while (actionNameFirstSet.contains(s.charAt(i))) {
            action += s.charAt(i);
            i++;
        }
        return action;
    }
    
    private static void expect(String s, String match) throws uParseException {
        if (!s.startsWith(match, i)) {
            throw new uParseException();
        } else {
            i += match.length();
        }
    }
}
