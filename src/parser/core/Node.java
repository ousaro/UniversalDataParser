package parser.core;

import java.util.List;

public abstract  class Node {
    protected String name;
    protected int indent;

    public Node(String name) {
        this(name, 0);
    }

    public Node(String name, int indent) {

        this.name = name;
        this.indent = indent;
    }

    public String getName() { return name; }
    //public int getIndent() { return indent; }
    // Accept method for visitor pattern
    public abstract void accept(INodeVisitor visitor);

    // Utility (override in subtypes)
    public abstract List<Node> getChildren();


}
