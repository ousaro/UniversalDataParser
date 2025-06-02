package parser.yaml;

public class YAMLToken {
    private final YAMLTokenType type;
    private final String value;
    private final int line;
    private final int indent;

    public YAMLToken(YAMLTokenType type, String value, int line, int indent) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.indent = indent;
    }
    public YAMLTokenType getType() { return type; }
    public String getValue() { return value; }
    public int getLine() { return line; }
    public int getIndent() { return indent; }
}
