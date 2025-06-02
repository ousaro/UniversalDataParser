package parser.json;


public class JSONToken {
    public final JSONTokenType type;
    public final String value;

    public JSONToken(JSONTokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public JSONTokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
