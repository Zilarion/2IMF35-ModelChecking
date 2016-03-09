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
            for (int x = 0; x < lts.transitions; x++) {
                String edgeLine = scanner.nextLine();
                m = edgePattern.matcher(edgeLine);
                if (m.find()) {
                    Edge edge = new Edge(m.group(1), m.group(2), m.group(3));
                    lts.edgeList.add(edge);
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
                    for (int x = 0; x < lts.transitions; x++) {
                        String edgeLine = br.readLine();
                        m = edgePattern.matcher(edgeLine);
                        if (m.find()) {
                            Edge edge = new Edge(m.group(1), m.group(2), m.group(3));
                            lts.edgeList.add(edge);
                        }
                    }
                }
            }
        }
        return lts;
    }
}
