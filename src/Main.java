import parser.core.IParser;
import parser.core.Node;
import parser.core.PrettyPrintVisitor;
import parser.factory.ParserFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Universal Parser Framwork stargin up!");

        // Example usage of the JSON parser
        String filePath = "data.txt";
        String jsonInputFile = Files.readString(Path.of(filePath));

        try {
            IParser parserFile = ParserFactory.getParser("json");
            Node rootNodeFile = parserFile.parse(jsonInputFile);

            PrettyPrintVisitor printerFile = new PrettyPrintVisitor();
            rootNodeFile.accept(printerFile);
            System.out.println("Parsed JSON from file successfully! \n" + printerFile.getResult());
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
        }


    }
}