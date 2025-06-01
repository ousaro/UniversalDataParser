import parser.factory.ParserFactory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Universal Parser Framwork stargin up!");

        // Example usage of the JSON parser
        String jsonInput = "{ \"name\": \"John\", \"age\": 30, \"isStudent\": false, \"courses\": [\"Math\", \"Science\"] }";
        try {
            var parser = ParserFactory.getParser("json");
            var rootNode = parser.parse(jsonInput);
            System.out.println("Parsed JSON successfully!" + rootNode.toString());
            //rootNode.accept(new NodePrinter()); // Assuming NodePrinter is a class that prints the node structure
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
        }


    }
}