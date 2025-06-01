package parser.core;

import java.util.List;

public abstract  class Node {
    protected String name;

    public Node(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    // Accept method for visitor pattern
    public abstract void accept(NodeVisitor visitor);

    // Utility (override in subtypes)
    public abstract List<Node> getChildren();


}
