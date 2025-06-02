package parser.yaml;

import parser.core.CompositeNode;
import parser.core.LeafNode;
import parser.core.Node;
import parser.core.INodeVisitor;

public class YAMLPrettyPrintVisitorI implements INodeVisitor {
    private final StringBuilder sb = new StringBuilder();
    private int indent = 0;
    private static final String INDENT_STR = "  ";

    @Override
    public String getResult() {
        return sb.toString();
    }

    private void appendIndent() {
        sb.append(INDENT_STR.repeat(indent));
    }

    @Override
    public void visitComposite(CompositeNode node) {
        boolean isSequence = node.isArray(); // <-- your isArray() should check all children are anonymous
        boolean isRoot = node.getName() == null;

        if (!isRoot && isSequence) {
            // Named node, value is a sequence (like skills, numbers)
            appendIndent();
            sb.append(node.getName()).append(":\n");
            indent++;
            for (Node child : node.getChildren()) {
                appendIndent();
                sb.append("- ");
                if (child instanceof CompositeNode) {
                    // composite entry in a sequence: print structure on new line
                    sb.append("\n");
                    indent++;
                    child.accept(this);
                    indent--;
                } else if (child instanceof LeafNode) {
                    Object value = ((LeafNode) child).getValue();
                    sb.append(value == null ? "null" : value.toString());
                    sb.append("\n");
                }
            }
            indent--;
        } else if (!isRoot) {
            // Named node, value is a mapping
            appendIndent();
            sb.append(node.getName()).append(":\n");
            indent++;
            for (Node child : node.getChildren()) {
                child.accept(this);
            }
            indent--;
        } else if (isSequence) {
            // Anonymous sequence, usually only as root (rare in your model)
            for (Node child : node.getChildren()) {
                appendIndent();
                sb.append("- ");
                if (child instanceof CompositeNode) {
                    sb.append("\n");
                    indent++;
                    child.accept(this);
                    indent--;
                } else if (child instanceof LeafNode) {
                    Object value = ((LeafNode) child).getValue();
                    sb.append(value == null ? "null" : value.toString());
                    sb.append("\n");
                }
            }
        } else {
            // Root or mapping: just visit children
            for (Node child : node.getChildren()) {
                child.accept(this);
            }
        }
    }

    @Override
    public void visitLeaf(LeafNode node) {
        appendIndent();
        if (node.getName() != null && !node.getName().isEmpty()) {
            sb.append(node.getName()).append(": ");
        }
        Object value = node.getValue();
        sb.append(value == null ? "null" : value.toString()).append("\n");
    }
}
