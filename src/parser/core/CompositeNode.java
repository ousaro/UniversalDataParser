package parser.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// used for objects like arrays, XML elements, YAML maps/lists, etc.
public class CompositeNode extends Node {
    private final List<Node> children = new ArrayList<>();
    private boolean isArray;

    public CompositeNode(String name, boolean isArray) {
        this(name, isArray, 0);
    }

    public CompositeNode(String name, boolean isArray, int indent) {
        super(name);
        this.isArray = isArray;
        this.indent = indent;
    }


    public void addChild(Node child) {
        children.add(child);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visitComposite(this);
    }

    @Override
    public List<Node> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public boolean isArray() { return isArray; }

    public void setIsArray(boolean isArray) {
        if (this.isArray == isArray) return; // No change
        this.isArray = isArray;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CompositeNode{name='").append(getName()).append("', children=[");
        for (Node child : children) {
            sb.append(child.toString()).append(", ");
        }
        if (!children.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove trailing comma and space
        }
        sb.append("]}");
        return sb.toString();
    }
}
