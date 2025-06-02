import parser.core.IParser;
import parser.core.Node;
import parser.core.NodeVisitor;
import parser.factory.ParserFactory;
import parser.factory.PrettyPrintVisitorFactory;

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
        String jsonFilePath = "json.txt";
        String jsonInputFile = Files.readString(Path.of(jsonFilePath));

        // Example usage of the YAML parser
        String yamlFilePath = "yaml.txt";
        String yamlInputFile = Files.readString(Path.of(yamlFilePath));

        try {
            // Parse JSON input from a string
            IParser parserFile = ParserFactory.getParser("json");
            Node rootNodeFile = parserFile.parse(jsonInputFile);

            // Parse YAML input from a string
            IParser parserYaml = ParserFactory.getParser("yaml");
            Node rootNodeYaml = parserYaml.parse(yamlInputFile);

            // Print the parsed JSON structure
            NodeVisitor printerFile = PrettyPrintVisitorFactory.getPrettyPrintVisitor("json");
            rootNodeFile.accept(printerFile);

            // Print the parsed stucture
            //System.out.println("Parsed JSON from file successfully! \n" + printerFile.getResult());
            System.out.println("Parsed YAML from file successfully! \n" + rootNodeYaml.toString());


        } catch (Exception e) {
            System.err.println("Error parsing: " + e.getMessage());
        }


    }
}