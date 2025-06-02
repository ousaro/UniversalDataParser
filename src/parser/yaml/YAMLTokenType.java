package parser.yaml;

public enum YAMLTokenType {
    STREAM_END,         // End of input (EOF)
    DOCUMENT_START,     // '---'
    DOCUMENT_END,       // '...'
    KEY,                // 'key:' - mapping key
    SCALAR,             // a simple value
    SEQUENCE_ENTRY,     // '- value' (list item)
}
