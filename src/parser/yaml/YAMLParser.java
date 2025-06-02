package parser.yaml;

import parser.core.CompositeNode;
import parser.core.IParser;
import parser.core.LeafNode;
import parser.core.Node;

public class YAMLParser implements IParser {
    private YAMLTokenizer tokenizer;
    private YAMLToken currentToken;

    private void advance() {
        this.currentToken = tokenizer.getNextToken();
    }

    @Override
    public Node parse(String input) {
        this.tokenizer = new YAMLTokenizer(input);
        // Start parsing from the first token
        advance();
        CompositeNode root = new CompositeNode(null, false);
        // Continue parsing until we reach the end of the stream
        while (currentToken != null && currentToken.getType() != YAMLTokenType.STREAM_END) {
            Node node = parseNode();
            if (node != null) {
                root.addChild(node);
            }
        }
        return root;
    }

    private Node parseNode() {
        if (currentToken == null || currentToken.getType() == YAMLTokenType.STREAM_END)
            return null;

        int myIndent = currentToken.getIndent();

        switch (currentToken.getType()) {
            case KEY -> {
                String key = currentToken.getValue();
                advance();

                // Inline value: simple key/value
                if (currentToken != null &&
                        currentToken.getType() == YAMLTokenType.SCALAR &&
                        currentToken.getIndent() == myIndent) {
                    String value = currentToken.getValue();
                    advance();
                    return new LeafNode(key, value);
                }

                // Otherwise, this key is a block parent. Attach nested nodes.
                CompositeNode keyNode = new CompositeNode(key, false);
                while (currentToken != null &&
                        currentToken.getType() != YAMLTokenType.STREAM_END &&
                        currentToken.getIndent() > myIndent) {
                    Node child = parseNode();

                    if (child != null) {
                        // If it's an anonymous list node, flatten its children into the key's children
                        if (child instanceof CompositeNode && child.getName() == null) {
                            keyNode.setIsArray(true); // Mark as array if it has children
                            for (Node subchild : child.getChildren()) {
                                keyNode.addChild(subchild);

                            }
                        } else {
                            keyNode.addChild(child);
                        }
                    }
                }
                return keyNode;
            }
            case SEQUENCE_ENTRY -> {
                CompositeNode listNode = new CompositeNode(null, false);
                while (currentToken != null &&
                        currentToken.getType() == YAMLTokenType.SEQUENCE_ENTRY &&
                        currentToken.getIndent() == myIndent) {
                    listNode.addChild(new LeafNode(null, currentToken.getValue()));
                    advance();
                    // Optional: handle nested mappings after "-"
                    while (currentToken != null &&
                            currentToken.getType() != YAMLTokenType.SEQUENCE_ENTRY &&
                            currentToken.getType() != YAMLTokenType.STREAM_END &&
                            currentToken.getIndent() > myIndent) {
                        Node nested = parseNode();
                        if (nested != null) listNode.addChild(nested);
                    }
                }
                return listNode;
            }
            case SCALAR -> {
                String value = currentToken.getValue();
                advance();
                return new LeafNode(null, value);
            }
            default -> {
                advance();
                return null;
            }
        }
    }

}
