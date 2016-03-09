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
        this.edgePattern = Pattern.compile("\\(([0-9])+,(\\\"[0-9a-zA-Z, ()]*\\\"),([0-9]+)\\)");
    }

    public LTS readLTS() {
        LTS lts = null;
        String init = scanner.nextLine();
        Matcher m = initPattern.matcher(init);
        if (m.find()) {
            lts = new LTS(m.group(1), m.group(2), m.group(3));
            for (int x = 0; x < lts.getTrans(); x++) {
                String edgeLine = scanner.nextLine();
                m = edgePattern.matcher(edgeLine);
                if (m.find()) {
                    Node from = new Node(m.group(1));
                    String label = m.group(2);
                    Node to = new Node(m.group(3));
                    HashMap nodes = lts.getNodes();
                    int fromID = from.getID();
                    int toID = to.getID();
                    if (nodes.containsKey(fromID)) {
                        from = (Node) nodes.get(fromID);
                    }
                    if (nodes.containsKey(toID)) {
                        to = (Node) nodes.get(toID);
                    }

                    nodes.put(fromID, from);
                    nodes.put(toID, to);
                    Edge edge = new Edge(from, label, to);
                    HashSet edges = lts.getEdges();
                    edges.add(edge);

                    from.getOutEdges().add(edge);
                }
            }
        }
        return lts;
    }

    public LTS readFileLTS(File file) throws FileNotFoundException, IOException {
        LTS lts = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (String line; (line = br.readLine()) != null;) {
                Matcher m = initPattern.matcher(line);
                if (m.find()) {
                    lts = new LTS(m.group(1), m.group(2), m.group(3));
                    for (int x = 0; x < lts.getTrans(); x++) {
                        String edgeLine = br.readLine();
                        m = edgePattern.matcher(edgeLine);
                        if (m.find()) {
                            Node from = new Node(m.group(1));
                            String label = m.group(2);
                            Node to = new Node(m.group(3));
                            HashMap nodes = lts.getNodes();
                            int fromID = from.getID();
                            int toID = to.getID();
                            if (nodes.containsKey(fromID)) {
                                from = (Node) nodes.get(fromID);
                            }
                            if (nodes.containsKey(toID)) {
                                to = (Node) nodes.get(toID);
                            }

                            nodes.put(fromID, from);
                            nodes.put(toID, to);
                            Edge edge = new Edge(from, label, to);
                            HashSet edges = lts.getEdges();
                            edges.add(edge);

                            from.getOutEdges().add(edge);
                        }
                    }
                }
            }
        }
        return lts;
    }
}
