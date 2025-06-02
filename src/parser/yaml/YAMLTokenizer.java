package parser.yaml;

public class YAMLTokenizer {
    private final String input;
    private int pos = 0;
    private final int length;
    private int line = 1;
    private boolean startOfLine = true;

    public YAMLTokenizer(String input) {
        this.input = input.strip();
        this.length = this.input.length();
    }

    public YAMLToken nextToken() {
        skipWhitespaceAndComments();
        if (pos >= length) {
            return new YAMLToken(YAMLTokenType.STREAM_END, null, line, 0);
        }

        int indent = 0;
        if (startOfLine) {
            while (pos < length && input.charAt(pos) == ' ') {
                pos++;
                indent++;
            }
            startOfLine = false;
        }


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
        // Find the end of the key (until ':', newline, or comment)
        while (pos < length && input.charAt(pos) != ':' && input.charAt(pos) != '\n' && input.charAt(pos) != '#') {
            pos++;
        }
        // If found ':', treat as key
        if (pos < length && input.charAt(pos) == ':') {
            String key = input.substring(keyStart, pos).strip();
            pos++;
            // Check if immediate value after colon (key: value)
            int valueStart = pos;
            // find the end of the value (until newline or comment)
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
                // Schedule to return value token on next call
                this.lastValueToken = new YAMLToken(YAMLTokenType.SCALAR, value, line, indent);
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
            if (ch == '\n') {
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

    private boolean peek(String s) {
        return pos + s.length() <= length && input.startsWith(s, pos);
    }

    private String parseUnquotedScalar() {
        int start = pos;
        while (pos < length && input.charAt(pos) != '\n' && input.charAt(pos) != '#') {
            pos++;
        }
        return input.substring(start, pos).strip();
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
