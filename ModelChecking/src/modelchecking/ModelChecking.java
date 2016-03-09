/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author ruudandriessen
 */
public class ModelChecking {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        AldebaranParser a = new AldebaranParser();
        //System.out.println(a.readLTS().toString());
        File file = new File("C:\\Users\\Hein\\Documents\\GitHub\\2IMF35-ModelChecking\\AldebaranFormat.txt");
        System.out.println(a.readFileLTS(file).toDot());
    }
    
}
