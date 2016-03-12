/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import uLanguage.uFormula;

/**
 *
 * @author ruudandriessen
 */
public class ModelChecking {

    static final ClassLoader loader = ModelChecking.class.getClassLoader();

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        new ModelChecking().run();
//        new ModelChecking().run(args);
    }
    
    public void run() {
        LTS lts = loadLTS("/Users/ruudandriessen/study/2imf35/2IMF35-ModelChecking/ModelChecking/resources/testcases/modal_operators/test.aut");
        while(true) {
            uFormula formula = getInputFunction();
            HashSet<State> result = EmersonLeiEvaluator.evaluate(formula, lts);
            System.out.println(result);
        }
    }

    public void run(String[] args) {
        String inputLTS = "";
        String inputFunction = "";
        String output = "";
        String algorithm = "";
        
        for (int i=0; i<args.length; i++) {
            switch(args[i]) {
                case "-lts":
                    inputLTS = args[i+1];
                    i++;
                    break;
                case "-f":
                    inputFunction = args[i+1];
                    i++;
                    break;
                case "-o":
                    output = args[i+1];
                    i++;
                    break;
                case "-alg":
                    algorithm = args[i+1];
                    i++;
                    break;
                default: return;
            }
        }

        LTS lts = loadLTS(inputLTS);
        uFormula function = loadFunction(inputFunction);
        HashSet<State> result = null;
        if (algorithm.equals("naive")) {
            result = NaiveEvaluator.evaluate(function, lts);
        } else if (algorithm.equals("improved")) {
            result = EmersonLeiEvaluator.evaluate(function, lts);
        }
        writeOutput(result, output);
    }

    public LTS loadLTS(String path) {
        try {
            AldebaranParser a = new AldebaranParser();

            File file = new File(path);
            LTS lts = a.readFileLTS(file);
            return lts;
        } catch (IOException ex) {
            Logger.getLogger(ModelChecking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public uFormula loadFunction(String path) {
        BufferedReader br = null;
        uFormula formula = null;
        try {
            File file = new File(path);
            br = new BufferedReader(new FileReader(file));
            String s = br.readLine();
            // Strip all spaces
            s = s.replaceAll("\\s+", "");
            // Except for mu/nu ones
            s = s.replaceAll("nu", "nu ");
            s = s.replaceAll("mu", "mu ");
            // Create function
            formula = FormulaBuilder.buildFormula(s);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModelChecking.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ModelChecking.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(ModelChecking.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return formula;
    }

    public uFormula getInputFunction() {
        System.out.println("----------------------");
        System.out.println("Model checking v0.1a");
        System.out.println("Please enter uInput: ");
        String s = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            s = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ModelChecking.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Strip all spaces
        s = s.replaceAll("\\s+", "");
        // Except for mu/nu ones
        s = s.replaceAll("nu", "nu ");
        s = s.replaceAll("mu", "mu ");
        // Create function
        return FormulaBuilder.buildFormula(s);
    }

    public void writeOutput(HashSet<State> result, String path) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(path, "UTF-8");
            writer.println(result);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModelChecking.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ModelChecking.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }
}
