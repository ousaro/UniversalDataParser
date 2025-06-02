package parser.core;

public interface NodeVisitor {

    public String getResult();

    void visitComposite(CompositeNode node);

    void visitLeaf(LeafNode node);
}
