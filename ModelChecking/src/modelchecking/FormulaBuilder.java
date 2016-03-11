package modelchecking;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import uLanguage.Box;
import uLanguage.Conjunction;
import uLanguage.Diamond;
import uLanguage.Disjunction;
import uLanguage.GFP;
import uLanguage.LFP;
import uLanguage.Proposition;
import uLanguage.Variable;
import uLanguage.uFormula;
import uLanguage.uOperator;
import uLanguage.uOperator.uOperations;

/**
 *
 * @author ruudandriessen
 */
public class FormulaBuilder {
    public static uFormula buildFormula(String s) {
        uOperations op = uOperator.getOperator(s);
        Pattern p  = uOperator.getPattern(op);
        if (p == null) {
            System.out.println("Error, invalid pattern");
            return null;
        } else {
            Matcher m = p.matcher(s);
            String lGroup = "", rGroup = "";
            if (m.find() && op != uOperations.FALSE && op != uOperations.TRUE && op != uOperations.VARIABLE) {
                lGroup = m.group(1) == null ? m.group(3) : m.group(1);
                rGroup = m.group(2) == null ? m.group(4) : m.group(2);
            }
            switch (op) {
                case FALSE:
                    return new Proposition(false);
                case TRUE:
                    return new Proposition(true);
                case VARIABLE:
                    return new Variable(s);
                case AND:
                    return new Conjunction(buildFormula(lGroup), buildFormula(rGroup));
                case OR:
                    return new Disjunction(buildFormula(lGroup), buildFormula(rGroup));
                case DIAMOND:
                    return new Diamond(lGroup, buildFormula(rGroup));
                case BOX:
                    return new Box(lGroup, buildFormula(rGroup));
                case LFP:
                    return new LFP((Variable) buildFormula(lGroup), buildFormula(rGroup));
                case GFP:
                    return new GFP((Variable) buildFormula(lGroup), buildFormula(rGroup));
                default:
                    System.out.println("Warning - did not find any valid match");
                    return null;
            }
        }
    }
}
