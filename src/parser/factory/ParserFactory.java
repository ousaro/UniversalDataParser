package parser.factory;

import parser.core.IParser;
import parser.json.JSONParser;
import parser.xml.XMLParser;
import parser.yaml.YAMLParser;

public class ParserFactory {

    public enum Format{
        JSON, XML, YAML
    }

    public static IParser getParser(String formatOrFileName){
        String fmt = formatOrFileName.trim().toLowerCase();

        if(fmt.endsWith(".json") || fmt.equals("json")){
            return new JSONParser();
        } else if(fmt.endsWith(".xml") || fmt.equals("xml")){
            throw new UnsupportedOperationException("XML parsing not implemented yet.");
            //return new XMLParser();
        } else if(fmt.endsWith(".yaml") || fmt.equals("yaml")){
            throw new UnsupportedOperationException("YAML parsing not implemented yet.");
            //return new YAMLParser();
        } else {
            throw new IllegalArgumentException("Unsupported format: " + formatOrFileName);
        }
    }
}
