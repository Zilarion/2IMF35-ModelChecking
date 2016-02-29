/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Hein
 */
public class AldebaranParser {
    Scanner scanner;
    Pattern initPattern;
    Pattern numberPattern;
    Pattern edgePattern;
    Pattern labelPattern;

    public AldebaranParser() {
        this.scanner = new Scanner(System.in);
        this.initPattern = Pattern.compile("des \\([0-9]+,[0-9]+,[0-9]+\\)");
        this.numberPattern = Pattern.compile("[0-9]+");
        this.edgePattern = Pattern.compile("\\(([0-9])+,(\\\"[0-9a-zA-Z, ()]*\\\"),([0-9]+)\\)");
    }
    
    public void readLTS() {
        int[] LTS = new int[3];
        List<List> edgeList = new ArrayList();
        String init = scanner.nextLine();
        Matcher m = initPattern.matcher(init);
        if (m.find()) {
            int i = 0;
            m = numberPattern.matcher(init);
            while (m.find()) {
            LTS[i] = Integer.parseInt(m.group());
            i++;
            }
            for (int x = 0; x < LTS[1]; x++) {
                String edgeLine = scanner.nextLine();
                m = edgePattern.matcher(edgeLine);
                if (m.find()) {
                    List edge = new ArrayList();
                    edge.add(m.group(1));
                    edge.add(m.group(2));
                    edge.add(m.group(3));
                    edgeList.add(edge);
                }
            }
        }
        System.out.println("initial state: " + LTS[0]);
        System.out.println("nr of transitions: " + LTS[1]);
        System.out.println("nr of states: " + LTS[2]);
        System.out.println(edgeList);
    }   
}
