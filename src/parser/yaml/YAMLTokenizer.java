package parser.yaml;

public class YAMLTokenizer {
    private final String input;
    private int pos = 0;
    private final int length;
    private int line = 1;
    private int nextIndent = 0;
    private boolean startOfLine = true;

    public YAMLTokenizer(String input) {
        this.input = input;
        this.length = input.length();
    }

    public YAMLToken nextToken() {
        skipWhitespaceAndComments();
        if (pos >= length) {
            return new YAMLToken(YAMLTokenType.STREAM_END, null, line, 0);
        }

        int indent = countCurrentIndentation();
        consumeIndentation(indent);

        // Standard YAML document start/end
        if (peek("---")) {
            pos += 3;
            startOfLine = false;
            return new YAMLToken(YAMLTokenType.DOCUMENT_START, "---", line, indent);
        }
        if (peek("...")) {
            pos += 3;
            startOfLine = false;
            return new YAMLToken(YAMLTokenType.DOCUMENT_END, "...", line, indent);
        }

        // Sequence entry
        if (peek("-")) {
            pos++;
            if (pos < length && input.charAt(pos) == ' ') pos++;
            String value = parseUnquotedScalar();
            startOfLine = false;
            return new YAMLToken(YAMLTokenType.SEQUENCE_ENTRY, value, line, indent);
        }

        // Key
        int keyStart = pos;
        while (pos < length && input.charAt(pos) != ':' && input.charAt(pos) != '\n' && input.charAt(pos) != '#') {
            pos++;
        }
        // If found ':', treat as key
        if (pos < length && input.charAt(pos) == ':') {
            String key = input.substring(keyStart, pos).strip();
            pos++;
            // Check if immediate value after colon (key: value)
            int valueStart = pos;
            while (pos < length && input.charAt(pos) != '\n' && input.charAt(pos) != '#') {
                pos++;
            }
            String value = input.substring(valueStart, pos).strip();
            startOfLine = false;
            if (value.isEmpty()) {
                return new YAMLToken(YAMLTokenType.KEY, key, line, indent);
            } else {
                // Inline value, emit KEY token first, later parser will read SCALAR
                // But for simplicity let's emit as two tokens
                // (Key)
                YAMLToken keyTok = new YAMLToken(YAMLTokenType.KEY, key, line, indent);
                // (Value as SCALAR)
                YAMLToken valueTok = new YAMLToken(YAMLTokenType.SCALAR, value, line, indent);
                // Schedule to return value token on next call
                this.lastValueToken = valueTok;
                return keyTok;
            }
        }

        // If no key, parse as scalar
        if (pos < length) {
            String value = parseUnquotedScalar();
            startOfLine = false;
            return new YAMLToken(YAMLTokenType.SCALAR, value, line, indent);
        }
        return null;
    }

    // --- Internal helpers ---

    private YAMLToken lastValueToken = null; // For handling inline value after key

    private void skipWhitespaceAndComments() {
        while (pos < length) {
            char ch = input.charAt(pos);
            if (ch == ' ' || ch == '\t') {
                pos++;
            } else if (ch == '\n') {
                pos++;
                line++;
                startOfLine = true;
            } else if (ch == '#') {
                while (pos < length && input.charAt(pos) != '\n') pos++;
            } else {
                break;
            }
        }
    }

    private int countCurrentIndentation() {
        int temp = pos;
        int count = 0;
        while (temp < length && (input.charAt(temp) == ' ')) {
            count++;
            temp++;
        }
        return count;
    }

    private void consumeIndentation(int count) {
        for (int i = 0; i < count && pos < length; i++) {
            if (input.charAt(pos) == ' ') pos++;
        }
    }

    private boolean peek(String s) {
        return pos + s.length() <= length && input.substring(pos, pos + s.length()).equals(s);
    }

    private String parseUnquotedScalar() {
        int start = pos;
        while (pos < length && input.charAt(pos) != '\n' && input.charAt(pos) != '#') {
            pos++;
        }
        String value = input.substring(start, pos).strip();
        return value;
    }

    // On each call, if a value token is pending (from inline key: value), return that first
    public YAMLToken getNextToken() {
        if (lastValueToken != null) {
            YAMLToken temp = lastValueToken;
            lastValueToken = null;
            return temp;
        }
        return nextToken();
    }
}
