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
        HashMap<Integer, State> states = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (String line; (line = br.readLine()) != null;) {
                Matcher m = initPattern.matcher(line);
                if (m.find()) {
                    lts = new LTS(m.group(1), m.group(2), m.group(3));
                    for (int x = 0; x < lts.getAbsoluteEdgeCount(); x++) {
                        String edgeLine = br.readLine();
                        m = edgePattern.matcher(edgeLine);
                        parseLTS(lts, states, m);
                    }
                }
            }
        }
        
        if (lts == null ) return lts;
        
        for (State s : states.values()) {
            lts.addNode(s);
        }
        return lts;
    }
    
    private LTS parseLTS(LTS lts, HashMap<Integer, State> states, Matcher m) throws IOException {
        if (m.find()) {
            int fromID = Integer.parseInt(m.group(1));
            String label = m.group(2);
            int toID = Integer.parseInt(m.group(3));
            State from, to;
            if (states.containsKey(toID)) {
                to = states.get(toID);
            } else {
                to = new State(toID);
                states.put(toID, to);
            }
            if (states.containsKey(fromID)) {
                from = states.get(fromID);
            } else {
                from = new State(fromID);
                states.put(fromID, from);
            }
            Edge edge = new Edge(from, label, to);
            HashSet<Edge> edges = lts.getEdges();
            edges.add(edge);
            
            from.getOutEdges().add(edge);
        }
        return lts;
    }
}
