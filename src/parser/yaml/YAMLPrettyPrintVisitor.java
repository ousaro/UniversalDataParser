package parser.yaml;

import parser.core.CompositeNode;
import parser.core.LeafNode;
import parser.core.NodeVisitor;

public class YAMLPrettyPrintVisitor implements NodeVisitor {
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
