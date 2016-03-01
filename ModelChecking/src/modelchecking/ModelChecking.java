/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

/**
 *
 * @author ruudandriessen
 */
public class ModelChecking {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AldebaranParser a = new AldebaranParser();
        //System.out.println(a.readLTS().toString());
        System.out.println(a.readLTS().toDot());
    }
    
}
