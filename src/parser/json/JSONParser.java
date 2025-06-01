package parser.json;

import parser.core.*;

public class JSONParser implements IParser {
    private JSONTokenizer tokenizer;
    private JSONToken currentToken;

    @Override
    public Node parse(String input) {
        this.tokenizer = new JSONTokenizer(input);
        this.currentToken = tokenizer.nextToken();
        Node root = parseValue(null); // start parsing from the root creating AST from the input

        if(currentToken.type != JSONTokenType.EOF) {
            throw new ParseException("Unexpected token after end of input: " + currentToken.type  );
        }

        return root;
    }

    // Recursive descent parsing for JSON values
    private Node parseValue(String name) {
        switch(currentToken.type) {
            case LEFT_BRACE: return parseObject(name);
            case LEFT_BRACKET: return parseArray(name);
            case STRING:
                String strValue = currentToken.value;
                consume(JSONTokenType.STRING);
                return new LeafNode(name, strValue);
            case NUMBER:
                String numValue = currentToken.value;
                consume(JSONTokenType.NUMBER);
                return new LeafNode(name, Double.parseDouble(numValue));
            case TRUE: consume(JSONTokenType.TRUE); return new LeafNode(name, Boolean.TRUE);
            case FALSE: consume(JSONTokenType.FALSE); return new LeafNode(name, Boolean.FALSE);
            case NULL: consume(JSONTokenType.NULL); return new LeafNode(name, null);
            default:
                throw new ParseException("Invalid JSON value at: " + currentToken.type);
        }
    }

    private Node parseObject(String name){
        consume(JSONTokenType.LEFT_BRACE);
        CompositeNode object = new CompositeNode(name, false) ;
        if(currentToken.type != JSONTokenType.RIGHT_BRACE){
            do{
                if(currentToken.type != JSONTokenType.STRING) throw new ParseException("Expected string key, found: " + currentToken.type);
                String key = currentToken.value; // get the key for the object
                consume(JSONTokenType.STRING);// consume the string token which is the key pass to colon
                consume(JSONTokenType.COLON); // consume the colon after the key then get the value
                Node value = parseValue(key); // parse the value associated with the key
                object.addChild(value); // add the key-value pair to the object
                if(currentToken.type != JSONTokenType.COMMA)
                    break; // if the next token is not a comma, we are done with this object
                consume(JSONTokenType.COMMA); // consume the comma to continue parsing

            }while(true);
        }

        consume(JSONTokenType.RIGHT_BRACE);
        return object;
    }

    private Node parseArray(String name) {
        consume(JSONTokenType.LEFT_BRACKET);
        CompositeNode array = new CompositeNode(name, true); // create an array node
        if (currentToken.type != JSONTokenType.RIGHT_BRACKET) {
            do {
                array.addChild(parseValue(null));
                if (currentToken.type != JSONTokenType.COMMA)
                    break;
                consume(JSONTokenType.COMMA);
            } while (true);
        }
        consume(JSONTokenType.RIGHT_BRACKET);
        return array;
    }

    private void consume(JSONTokenType type){
        if(currentToken.type != type) {
            throw new ParseException("Expected token: " + type + ", but found: " + currentToken.type);
        }

        currentToken = tokenizer.nextToken();
    }
}


