/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    }
    
    public void run() {
        LTS lts = loadLTS("/Users/ruudandriessen/study/2imf35/2IMF35-ModelChecking/ModelChecking/resources/testcases/modal_operators/test.aut");
        while(true) {
            uFunction function = getInputFunction();
            HashSet<State> result = NaiveEvaluator.evaluate(function, lts, new Environment());
            System.out.println(result);
        }
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
    
    public uFunction getInputFunction() {
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
        s = s.replaceAll("\\s+","");
        // Except for mu/nu ones
        s = s.replaceAll("nu","nu ");
        s = s.replaceAll("mu","mu ");
        // Create function
        uFunction f = new uFunction(s);
        
        System.out.println(f);
        return f;
    }
}
