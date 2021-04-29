import org.kohsuke.args4j.*;

import java.io.FileNotFoundException;
import java.io.IOException;

/* Вывод на консоль указанного (в аргументе командной строки) текстового файла с фильтрацией
    word задаёт слово для поиска (на консоль выводятся только содержащие его строки)
    -r (regex) вместо слова задаёт регулярное выражение для поиска
     (на консоль выводятся только строки, содержащие данное выражение)
    -v инвертирует условие фильтрации (выводится только то, что ему НЕ соответствует)
    -i игнорировать регистр слов
     Command Line:grep [-v] [-i] [-r] word inputname.txt
     Кроме самой программы, следует написать автоматические тесты к ней.
 */

public class GrepLauncher {

    @Option(name = "-v", metaVar = "InvertConditions")
    private Boolean invertConditions;
    @Option(name = "-i", metaVar = "IgnoreRegister")
    private Boolean ignoreRegister;

    @Option(name = "-r", metaVar = "Regex")
    private Boolean regexInsteadWord;

    @Argument(required = true, usage = "Word to search")
    private String theSearchedWord;

    @Argument(usage = "Input file name", index = 1)
    private String inputFileName;

    public static void main(String[] args) {
        new GrepLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar grep [-v] [-i] [-r] word inputname.txt");
            parser.printUsage(System.err);
        }
        try {
            Grep grep = new Grep(invertConditions, ignoreRegister, regexInsteadWord, theSearchedWord, inputFileName);
            System.out.println(grep.grep());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

