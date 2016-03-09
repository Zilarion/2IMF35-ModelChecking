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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ruudandriessen
 */
public class ModelChecking {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        AldebaranParser a = new AldebaranParser();
        File file = new File("C:\\Users\\Hein\\Documents\\GitHub\\2IMF35-ModelChecking\\AldebaranFormat.txt");
        LTS lts = a.readFileLTS(file);
        HashMap nodes = lts.getNodes();
        Iterator it = nodes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Node n = (Node) pair.getValue();
            //System.out.println(pair.getValue().toString());
            for (Edge e : n.getOutEdges()) {
                System.out.println(e.toString());
            }
        }
        
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
