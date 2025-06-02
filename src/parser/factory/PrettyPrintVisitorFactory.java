package parser.factory;

import parser.json.JSONPrettyPrintVisitorI;
import parser.core.INodeVisitor;
import parser.xml.XMLPrettyPrintVisitorI;
import parser.yaml.YAMLPrettyPrintVisitorI;

public class PrettyPrintVisitorFactory {

    public enum Format{
        JSON, XML, YAML
    }

    public static INodeVisitor getPrettyPrintVisitor(String formatOrFileName){
        String fmt = formatOrFileName.trim().toLowerCase();

        if(fmt.endsWith(".json") || fmt.equals("json")){
            return new JSONPrettyPrintVisitorI();
        } else if(fmt.endsWith(".xml") || fmt.equals("xml")){
            return new XMLPrettyPrintVisitorI();
        } else if(fmt.endsWith(".yaml") || fmt.equals("yaml")){
            return new YAMLPrettyPrintVisitorI();
        } else {
            throw new IllegalArgumentException("Unsupported format: " + formatOrFileName);
        }
    }
}
