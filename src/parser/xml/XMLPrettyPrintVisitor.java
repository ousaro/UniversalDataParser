package parser.xml;

import parser.core.CompositeNode;
import parser.core.LeafNode;
import parser.core.NodeVisitor;

public class XMLPrettyPrintVisitor implements NodeVisitor {
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
