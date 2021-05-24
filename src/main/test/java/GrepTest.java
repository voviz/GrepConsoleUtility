import org.kohsuke.args4j.CmdLineException;
import org.spsstu.GrepLauncher;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GrepTest {
    String newLine = System.getProperty("line.separator");
    private String main(String[] args) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;
        System.setOut(new PrintStream(baos));
        System.setErr(new PrintStream(baos));
        GrepLauncher.main(args);

        System.out.flush();
        System.err.flush();
        System.setOut(oldOut);
        System.setErr(oldErr);
        return (baos.toString());
    }

    @Test
    public void grep() throws URISyntaxException, IOException {
        URL url = GrepLauncher.class.getResource("/input.txt");
        File file = new File(url.toURI());

        assertEquals("Strange that I did not know him then," + newLine, main(new String[]{"Strange", file.getPath()}));
        assertEquals("Strange that I did not know him then," + newLine, main(new String[]{"-i", "strange", file.getPath()}));
        assertEquals("That friend of mine!" + newLine +
                "One friendly sign;" + newLine +
                "To make me see" + newLine +
                "My envy of the praise he had" + newLine +
                "For praising me." + newLine +
                "Once, in my pride! . . ." + newLine +
                "Until he died."+ newLine, main(new String[]{"-i", "-v", "HIM", file.getPath()}));
        assertEquals("Strange that I did not know him then," + newLine +
                "That friend of mine!" + newLine, main(new String[]{"-r", "[Tt][h][a][t]", file.getPath()}));
        assertEquals("Argument \"\" is required" + newLine + "java -jar grep [-v] [-i] [-r] word inputname.txt" + newLine,
                 main(new String[]{}));
        assertThrows(FileNotFoundException.class, () -> main(new String[]{"-i", "strange", "filefile.txt"}));
        assertEquals("option \"-r\" cannot be used with the option(s) [-i]" + newLine + "java -jar grep [-v] [-i] [-r] word inputname.txt" + newLine,
                main(new String[]{"-r", "-i","Strange", file.getPath()}));
    }

}
