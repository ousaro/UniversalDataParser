package parser.xml;

import parser.core.CompositeNode;
import parser.core.LeafNode;
import parser.core.INodeVisitor;

public class XMLPrettyPrintVisitorI implements INodeVisitor {
    @Override
    public String getResult() {
        return "";
    }

    @Override
    public void visitComposite(CompositeNode node) {

    }

    @Override
    public void visitLeaf(LeafNode node) {

    }
}
