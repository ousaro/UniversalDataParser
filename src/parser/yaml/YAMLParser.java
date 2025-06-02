package parser.yaml;

import parser.core.CompositeNode;
import parser.core.IParser;
import parser.core.LeafNode;
import parser.core.Node;

public class YAMLParser implements IParser {
    private YAMLTokenizer tokenizer;
    private YAMLToken currentToken;

    private void advance() {
        currentToken = tokenizer.getNextToken();
    }

    @Override
    public Node parse(String input) {
        this.tokenizer = new YAMLTokenizer(input);
        advance();
        CompositeNode root = new CompositeNode(null, false);
        while (currentToken != null && currentToken.getType() != YAMLTokenType.STREAM_END) {
            Node node = parseNode(0);
            if (node != null) {
                root.addChild(node);
            }
        }
        return root;
    }

    private Node parseNode(int parentIndent) {
        if (currentToken == null || currentToken.getType() == YAMLTokenType.STREAM_END) return null;
        int myIndent = currentToken.getIndent();

        switch (currentToken.getType()) {
            case KEY -> {
                String key = currentToken.getValue();
                advance();
                if (currentToken != null &&
                        currentToken.getType() == YAMLTokenType.SCALAR &&
                        currentToken.getIndent() == myIndent) {
                    String value = currentToken.getValue();
                    advance();
                    return new LeafNode(key, value);
                }
                CompositeNode block = new CompositeNode(key, false);
                while (currentToken != null &&
                        currentToken.getType() != YAMLTokenType.STREAM_END &&
                        currentToken.getIndent() > myIndent) {
                    Node child = parseNode(currentToken.getIndent());
                    if (child != null) block.addChild(child);
                }
                return block;
            }
            case SEQUENCE_ENTRY -> {
                CompositeNode list = new CompositeNode(null, false);
                int listIndent = myIndent;
                while (currentToken != null &&
                        currentToken.getType() == YAMLTokenType.SEQUENCE_ENTRY &&
                        currentToken.getIndent() == listIndent) {
                    list.addChild(new LeafNode(null, currentToken.getValue()));
                    advance();
                    // Handle nested content under this list entry
                    while (currentToken != null &&
                            currentToken.getType() != YAMLTokenType.SEQUENCE_ENTRY &&
                            currentToken.getType() != YAMLTokenType.STREAM_END &&
                            currentToken.getIndent() > listIndent) {
                        Node nested = parseNode(currentToken.getIndent());
                        if (nested != null) list.addChild(nested);
                    }
                }
                return list;
            }
            case SCALAR -> {
                String value = currentToken.getValue();
                advance();
                return new LeafNode(null, value);
            }
            default -> { advance(); return null; }
        }
    }
}
