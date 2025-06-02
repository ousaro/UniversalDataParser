package parser.factory;

import parser.json.JSONPrettyPrintVisitor;
import parser.core.NodeVisitor;
import parser.xml.XMLPrettyPrintVisitor;
import parser.yaml.YAMLPrettyPrintVisitor;

public class PrettyPrintVisitorFactory {

    public enum Format{
        JSON, XML, YAML
    }

    public static NodeVisitor getPrettyPrintVisitor(String formatOrFileName){
        String fmt = formatOrFileName.trim().toLowerCase();

        if(fmt.endsWith(".json") || fmt.equals("json")){
            return new JSONPrettyPrintVisitor();
        } else if(fmt.endsWith(".xml") || fmt.equals("xml")){
            return new XMLPrettyPrintVisitor();
        } else if(fmt.endsWith(".yaml") || fmt.equals("yaml")){
            return new YAMLPrettyPrintVisitor();
        } else {
            throw new IllegalArgumentException("Unsupported format: " + formatOrFileName);
        }
    }
}
