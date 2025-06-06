import parser.core.IParser;
import parser.core.Node;
import parser.core.INodeVisitor;
import parser.factory.ParserFactory;
import parser.factory.PrettyPrintVisitorFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        System.out.println("Universal Parser Framwork stargin up!");


        // Example usage of the JSON parser
        String jsonFilePath = "json.json";
        String jsonInputFile = Files.readString(Path.of(jsonFilePath));

        // Example usage of the YAML parser
        String yamlFilePath = "yaml.yaml";
        String yamlInputFile = Files.readString(Path.of(yamlFilePath));

        try {
            // Parse JSON input from a string
            IParser parserFile = ParserFactory.getParser("json");
            Node rootNodeFile = parserFile.parse(jsonInputFile);

            // Parse YAML input from a string
            IParser parserYaml = ParserFactory.getParser("yaml");
            Node rootNodeYaml = parserYaml.parse(yamlInputFile);

            // Print the parsed JSON structure
            INodeVisitor JSONprinterFile = PrettyPrintVisitorFactory.getPrettyPrintVisitor("json");
            rootNodeFile.accept(JSONprinterFile);

            INodeVisitor YAMLprinterFile = PrettyPrintVisitorFactory.getPrettyPrintVisitor("yaml");
            rootNodeYaml.accept(YAMLprinterFile);

            // Print the parsed stucture
            //System.out.println("Parsed JSON from file successfully! \n" + JSONprinterFile.getResult());
            System.out.println("Parsed YAML from file successfully! \n" + YAMLprinterFile.getResult());


        } catch (Exception e) {
            System.err.println("Error parsing: " + e.getMessage());
        }


    }
}