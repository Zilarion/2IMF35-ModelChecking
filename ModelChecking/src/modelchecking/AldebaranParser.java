/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelchecking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
        this.initPattern = Pattern.compile("des \\(([0-9]+),([0-9]+),([0-9]+)\\)");
        this.edgePattern = Pattern.compile("\\(([0-9]+),\\\"([0-9a-zA-Z, ()]*)\\\",([0-9]+)\\)");
    }

    public LTS readFileLTS(File file) throws FileNotFoundException, IOException {
        LTS lts = null;
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (String line; (line = br.readLine()) != null;) {
                Matcher m = initPattern.matcher(line);
                if (m.find()) {
                    lts = new LTS(m.group(1), m.group(2), m.group(3));
                    for (int x = 0; x < lts.getAbsoluteEdgeCount(); x++) {
                        String edgeLine = br.readLine();
                        m = edgePattern.matcher(edgeLine);
                        parseLTS(lts, m);
                    }
                }
            }
        }
        return lts;
    }
    
    private LTS parseLTS(LTS lts, Matcher m) {
        if (m.find()) {
            State from = new State(m.group(1));
            String label = m.group(2);
            State to = new State(m.group(3));
            HashMap<Integer,State> states = lts.getStates();
            if (states.containsKey(to.getID())) {
                to = states.get(to.getID());
                
            }
            if (!states.containsKey(from.getID())) {
                from = states.get(from.getID());
            }
            lts.addNode(to);
            lts.addNode(from);
            Edge edge = new Edge(from, label, to);
            HashSet edges = lts.getEdges();
            edges.add(edge);

            from.getOutEdges().add(edge);
        }
        return lts;
    }
}
