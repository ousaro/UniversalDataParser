package parser.core;

public interface INodeVisitor {

    public String getResult();

    void visitComposite(CompositeNode node);

    void visitLeaf(LeafNode node);
}
