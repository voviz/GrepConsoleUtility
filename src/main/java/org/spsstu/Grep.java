package org.spsstu;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Grep {
    private Boolean invert = false;
    private Boolean ignore = false;
    private Boolean regex = false;
    private String word;
    private final File file;

    Grep(Boolean inv, Boolean ign, Boolean reg, String theWord, File file) {
        if (inv != null) this.invert = inv;
        if (ign != null) this.ignore = ign;
        if (reg != null) this.regex = reg;
        word = theWord;
        this.file = file;
    }
    String grep() throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        String checkLine = line;
        Pattern pattern = Pattern.compile(word);
        boolean first = true;
        while (line != null) {
            boolean flag = false;
            if (ignore) {
                checkLine = line.toLowerCase();
                word = word.toLowerCase();
            }
            if (regex) {
                Matcher matcher = pattern.matcher(line);
                flag = matcher.find();
            }
            else if (checkLine.contains(word)) {
                flag = true;
            }
            if (invert) {
                flag = !flag;
            }
            if (flag) {
                if (first) {
                    result.append(line);
                    first = false;
                }
                else result.append(System.getProperty("line.separator")).append(line);
            }
            line = reader.readLine();
            checkLine = line;
        }
        reader.close();
        return result.toString();
    }
}
