/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ruudandriessen
 */
public class ModelChecking {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        while(true) {
            System.out.println("----------------------");
            System.out.println("Model checking v0.1a");
            System.out.println("Please enter uInput: ");
            String s = "";
            try {
                s = br.readLine();
            } catch (IOException ex) {
                Logger.getLogger(ModelChecking.class.getName()).log(Level.SEVERE, null, ex);
            }
            s = s.replaceAll("\\s+","");
            s = s.replaceAll("nu","nu ");
            s = s.replaceAll("mu","mu ");
            uFunction f = new uFunction(s);
            System.out.println(f);
        }
    }
    
}
