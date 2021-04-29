import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep {
    private Boolean invert = false;
    private Boolean ignore = false;
    private Boolean regex = false;
    private String word;
    private final String fileName;
    private final StringBuilder result = new StringBuilder();

    public Grep(Boolean inv, Boolean ign, Boolean reg, String theWord, String file) throws IOException {
        if (inv != null) this.invert = inv;
        if (ign != null) this.ignore = ign;
        if (reg != null) this.regex = reg;
        word = theWord;
        fileName = file;
    }
    public String grep() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        boolean flag = false;
        while (line != null) {
            if (ignore) {
                line = line.toLowerCase();
                if (!regex) word = word.toLowerCase();
            }
            if (regex) {
                Pattern pattern = Pattern.compile(word);
                Matcher matcher = pattern.matcher(line);
                flag = matcher.find();
            }
            else if (line.contains(word)) {
                flag = true;
            }
            if (ignore) {
                flag = !flag;
            }
            if (flag) result.append(line).append("\n");
            line = reader.readLine();
        }
        return result.toString();
    }
}
