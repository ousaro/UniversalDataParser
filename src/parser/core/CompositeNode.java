package parser.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// used for objects like arrays, XML elements, YAML maps/lists, etc.
public class CompositeNode extends Node {
    private final List<Node> children = new ArrayList<>();

    public CompositeNode(String name) {
        super(name);
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
