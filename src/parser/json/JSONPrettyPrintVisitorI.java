package parser.json;

import parser.core.CompositeNode;
import parser.core.LeafNode;
import parser.core.Node;
import parser.core.INodeVisitor;

public class JSONPrettyPrintVisitorI implements INodeVisitor {

    private final StringBuilder sb = new StringBuilder();
    private int indent = 0; // Current indentation level for pretty printing
    private static final String INDENT_STR = "  "; // Two spaces for indentation for the sake of readability

    public String getResult() {
        return sb.toString();
    }

    private void appendIndent() {
        sb.append(INDENT_STR.repeat(Math.max(0, indent))); // Append indentation based on the current level
    }


    @Override
    public void visitComposite(CompositeNode node) {
        boolean isRoot = indent == 0; // Check if this is the root node
        boolean isArray = node.isArray(); // Check if the node is an array

        if (isArray && node.getName() != null) {
            sb.append("\"").append(node.getName()).append("\": [\n");
        } else if (!isArray && node.getName() != null) {
            sb.append("\"").append(node.getName()).append("\": {\n");
        } else if (isArray) {
            sb.append("[\n");
        } else if (isRoot) {
            sb.append("{\n");
        } else {
            sb.append("{\n");
        }

        indent++; // Increase indentation for children
        int childCount = node.getChildren().size();
        int currentIndex = 0;

        for(Node child : node.getChildren()){
            appendIndent();
            child.accept(this);
            currentIndex++;
            if(currentIndex < childCount) sb.append(","); // Add a comma
            sb.append('\n'); // No comma after the last child
        }

        indent--; // Decrease indentation after processing children
        appendIndent(); // Append indentation for closing brace
        sb.append(isArray ? "]" : "}"); // Close array or object

    }

    @Override
    public void visitLeaf(LeafNode node) {
        // Print leaf node with its name and value
        if(node.getName() != null) {
            sb.append("\"").append(node.getName()).append("\": "); // Print leaf name
        }

        Object value = node.getValue();
        if(value == null) {
            sb.append("null");
        } else if(value instanceof String){
            sb.append("\"").append(value).append("\""); // String values are quoted
        }else{
            sb.append(value); // Other values are printed as is
        }
    }
}
