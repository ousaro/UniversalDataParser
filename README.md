# UniversalDataParser

**UniversalDataParser** is a modular, extensible Java framework for parsing, validating, and converting structured data formats—including **JSON, XML, and YAML**—*without* third-party libraries. It features pluggable, object-oriented parsers and a rich AST, built for robustness, readability, and easy extensibility.

---

## 🚀 Features

- **Supports JSON, and YAML** (Manual parsing, no dependencies)
- **Modular design:** Each format in a separate, pluggable package
- **Manual tokenization & recursive descent parsing**
- **Extensible AST (Composite Pattern):** Nodes for objects, arrays, values
- **Design patterns:** Factory, Strategy, Composite, Visitor
- **Schema validation & error reporting**
- **AST Visitors for pretty-printing**


## 🌐 Architecture Overview

```plaintext
    +--------------------+        
   |   ParserFactory    |  <-- Factory Pattern
   +---------+----------+        
            |                        
   +--------v---------+        
   |     IParser      |  <-- Strategy Pattern
   +--------+---------+        
     |      |      |        
 +---v--+ +--v---+ +--v---+    
 |JSON  | | XML  | | YAML |    
 |Parser| |Parser| |Parser|    
 +---+--+ +--+---+ +--+---+                        
        \    |    /                               
         \   |   /                                  
          +--v---+                               
         |  AST   |   <-- Composite Pattern                            
         +--+-----+                           
            |                               
     (accept(visitor)) 
         ^
         |
         |
   +-------------------------+
   |    VisitorFactory       |    <-- Factory Pattern
   +-----------+-------------+
               |
       +-------v--------+
       |    IVisitor    |  <-- Strategy Pattern
       +---+---+---+----+
           |   |   |
      +----v+ +v+ +v----+
      |Json |Xml|Yaml   |   (Visitors)
      |Pretty|Pretty|Pretty |
      |Vis  |Vis|Vis    |
      +-----+---+-------+
                 

```

---

## 📦 Project Structure

```
UniversalDataParser/
│
├─ src/
│   └─ parser/
│       ├─ core/       # AST nodes, interfaces, and visitors
│       ├─ factory/    # ParserFactory
│       ├─ json/       # JSON parser & tokenizer
│       ├─ xml/        # XML parser & tokenizer
│       └─ yaml/       # YAML parser & tokenizer
│
└─ README.md
```

---

## 🏗️ How It Works

1. **ParserFactory** instantiates the correct parser based on file type or input string.
2. **Tokenizer** splits input into tokens (lexemes) for each format.
3. **Parser** (e.g., JSONParser) builds an **AST** (Node tree) using manual, recursive descent parsing.
4. **AST Nodes** (Composite/LeafNode) represent data structure.
5. **VisitorFactory** creates visitors for different operations.
6**Visitors** operate on the AST for pretty printing, validation, or format conversion.

---

## 📄 Example Usage

```java
import parser.factory.ParserFactory;
import parser.core.IParser;
import parser.core.Node;
import parser.core.visitor.PrettyPrinterVisitor;

public class Main {
    public static void main(String[] args) throws IOException {//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
      System.out.println("Universal Parser Framwork stargin up!");


      // Example usage of the JSON parser
      String jsonFilePath = "json.json";
      String jsonInputFile = Files.readString(Path.of(jsonFilePath));

      // Example usage of the YAML parser
      String yamlFilePath = "yaml.yaml";
      String yamlInputFile = Files.readString(Path.of(yamlFilePath));

      try {
        // Parse JSON input from a string
        IParser parserFile = ParserFactory.getParser("json");
        Node rootNodeFile = parserFile.parse(jsonInputFile);

        // Parse YAML input from a string
        IParser parserYaml = ParserFactory.getParser("yaml");
        Node rootNodeYaml = parserYaml.parse(yamlInputFile);

        // Print the parsed JSON structure
        INodeVisitor JSONprinterFile = PrettyPrintVisitorFactory.getPrettyPrintVisitor("json");
        rootNodeFile.accept(JSONprinterFile);

        INodeVisitor YAMLprinterFile = PrettyPrintVisitorFactory.getPrettyPrintVisitor("yaml");
        rootNodeYaml.accept(YAMLprinterFile);

        // Print the parsed stucture
        //System.out.println("Parsed JSON from file successfully! \n" + JSONprinterFile.getResult());
        System.out.println("Parsed YAML from file successfully! \n" + YAMLprinterFile.getResult());


      } catch (Exception e) {
        System.err.println("Error parsing: " + e.getMessage());
      }


    }
}
```

---

## ⚙️ Getting Started

### **1. Requirements**

- **Java 8** or newer
- **IntelliJ IDEA** (or any modern Java IDE)

### **2. Run Locally**

```
git clone https://github.com/yourusername/UniversalDataParser.git
cd UniversalDataParser
```

- **Open in IntelliJ IDEA**
    - `File > Open...` and select the project folder

- **Build Project**
    - `Build > Build Project` or shortcut (`Ctrl+F9`)

- **Run**
    - Right-click `src/parser/Main.java` > **Run 'Main'**

---

---
## 🤩 Advanced Features & Extensibility

- **Add new format?**
    1. Add new package in `parser/`.
    2. Implement `IParser` and a custom tokenizer in that package.
    3. Register parser in `ParserFactory`.
    4. Exemple : add XML support by creating `parser/xml/XmlParser.java` and `parser/xml/XmlTokenizer.java`.


- **AST Visitors:**  
  Add custom visitors (e.g., for metrics, transformation, validation).
  1. Create a new visitor class implementing `INodeVisitor`.
  2. Register it in `VisitorFactory`.
  3. Use it with `Node.accept(visitor)`.
  

- **Schema Validation:**  
  Build schema and type rules for custom business logic.


- **Streaming/Chunked Parsing:**  
  Upgrade tokenization and parsing for very large files.


- **Handling complex structures:**  
  Support for advanced features like comments, anchors, and aliases in YAML.


- **Format Conversion:**  
  Implement format conversion between JSON, XML, and YAML.


## 📝 License

**MIT License**  
See [LICENSE](LICENSE) for details.

---

## ✨ Description

> **Universal Data Parser (Java, Manual Implementation):**  
> Architected and implemented a custom parsing framework in Java supporting JSON, XML, and YAML. Built recursive descent parsers, tokenizers, and a unified AST from scratch. Applied OO design patterns (Factory, Strategy, Composite, Visitor) for maximum maintainability and extensibility. Developed robust error handling, pretty-printing, format conversion, and schema validation features—demonstrating expert-level code architecture and parsing know-how.

---

**Ready to parse anything. Fork, build, extend, and impress! 🎉**