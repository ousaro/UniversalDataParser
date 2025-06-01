package parser.core;


public interface IParser {
    Node parse(String input) throws ParseException;
}
