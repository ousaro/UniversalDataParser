package parser.json;

import parser.core.ParseException;

public class JSONTokenizer {
    private final String input;
    private int pos = 0;
    private final int length;

    public JSONTokenizer(String input) {
        this.input = input.strip();
        this.length = input.length();
    }

    public JSONToken nextToken(){
        skipWhitespace();
        if(pos >= length) {
            return new JSONToken(JSONTokenType.EOF, null); // End of File
        }
        char c = input.charAt(pos);
        switch(c){
            case '{': pos++; return new JSONToken(JSONTokenType.LEFT_BRACE, "{");
            case '}': pos++; return new JSONToken(JSONTokenType.RIGHT_BRACE, "}");
            case '[': pos++; return new JSONToken(JSONTokenType.LEFT_BRACKET, "[");
            case ']': pos++; return new JSONToken(JSONTokenType.RIGHT_BRACKET, "]");
            case ':': pos++; return new JSONToken(JSONTokenType.COLON, ":");
            case ',': pos++; return new JSONToken(JSONTokenType.COMMA, ",");
            case '"': return stringToken();
            default:
                if(Character.isDigit(c) || c == '-') return numberToken();
                if(input.startsWith("true", pos)) {pos += 4; return new JSONToken(JSONTokenType.TRUE, "true");}
                if(input.startsWith("false", pos)) {pos += 5; return new JSONToken(JSONTokenType.FALSE, "false");}
                if(input.startsWith("null", pos)) {pos += 4; return new JSONToken(JSONTokenType.NULL, "null");}
                throw new ParseException("Unexpected character: " + c + " at position " + pos);
        }
    }

    private void skipWhitespace(){
        while(pos < length && Character.isWhitespace(input.charAt(pos))) { pos++; }
    }

    private JSONToken stringToken(){
        StringBuilder sb = new StringBuilder();
        pos++; // skip the opening quote
        while(pos < length) {
            char c = input.charAt(pos);
            if(c == '"') {
                pos++; // skip the closing quote
                return new JSONToken(JSONTokenType.STRING, sb.toString());
            }
            if(c == '\\' && pos+1<length) { // handle escape sequences
                pos++; // skip the backslash
                c = input.charAt(pos);
                if(c=='n') sb.append('\n');
                else if(c=='t') sb.append('\t');
                else sb.append(c); // add the character after the backslash

            } else {
                sb.append(c);
            }
            pos++;
        }
        throw new ParseException("Unterminated string at position " + (pos - sb.length() -1 )); // to account for the opening quote
    }

    private JSONToken numberToken(){
        int start = pos;
        if(input.charAt(pos) == '-') pos++; // handle negative numbers
        while(pos < length && Character.isDigit(input.charAt(pos))) pos++;
        if(pos < length && input.charAt(pos) == '.') {
            pos++; // skip the decimal point
            while(pos < length && Character.isDigit(input.charAt(pos))) pos++;
        }
        return new JSONToken(JSONTokenType.NUMBER, input.substring(start, pos));

    }
}
