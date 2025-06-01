package parser.core;

import java.util.Collections;
import java.util.List;

public class LeafNode extends Node {
    private final Object value;

    public LeafNode(String name, Object value) {
        super(name);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public List<Node> getChildren() {
        // Leaf nodes have no children
        return Collections.emptyList();
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visitLeaf(this);
    }

    @Override
    public String toString() {
        return "LeafNode{name='" + getName() + "', value=" + value + "}";
    }

}
