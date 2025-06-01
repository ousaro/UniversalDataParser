package parser.json;

public enum JSONTokenType{
    LEFT_BRACE, // {
    RIGHT_BRACE, // }
    LEFT_BRACKET, // [
    RIGHT_BRACKET, // ]
    COLON, // :
    COMMA, // ,
    STRING, // "string"
    NUMBER, // 123, 123.45
    TRUE, // true
    FALSE, // false
    NULL, // null
    EOF // End of File
}
