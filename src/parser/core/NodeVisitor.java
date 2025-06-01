package parser.core;

public interface NodeVisitor {

    void visitComposite(CompositeNode node);

    void visitLeaf(LeafNode node);
}
