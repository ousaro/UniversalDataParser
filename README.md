# UniversalDataParser

**UniversalDataParser** is a modular, extensible Java framework for parsing, validating, and converting structured data formats—including **JSON, XML, and YAML**—*without* third-party libraries. It features pluggable, object-oriented parsers and a rich AST, built for robustness, readability, and easy extensibility.

---

## 🚀 Features

- **Supports JSON, XML, and YAML** (Manual parsing, no dependencies)
- **Modular design:** Each format in a separate, pluggable package
- **Manual tokenization & recursive descent parsing**
- **Extensible AST (Composite Pattern):** Nodes for objects, arrays, values
- **Design patterns:** Factory, Strategy, Composite, Visitor
- **Schema validation & error reporting**
- **AST Visitors for pretty-printing, validation, and format conversion**
- **Ideal for education, interviews, and robust data ingestion pipelines**

---

## 🌐 Architecture Overview

```plaintext
      +---------------------+
      |    ParserFactory    |  <-- Factory Pattern
      +----------+----------+
                 |
      +----------+----------+
      |     IParser         |  <-- Strategy Pattern
      +----------+----------+
       |        |        |
  +----v--+ +---v---+ +--v-----+
  |JSON   | | XML   | | YAML   |
  |Parser | |Parser | |Parser  |
  +---+---+ +---+---+ +---+----+
          \       |      /
            \     |    /
               +--v---+
               | AST   |  <-- Composite NodeTree
               +--+---+
                  |
            +-----v------+
            |  Visitor   |  <-- Visitor Pattern
            +------------+
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
5. **Visitors** operate on the AST for pretty printing, validation, or format conversion.

---

## 📄 Example Usage

```java
import parser.factory.ParserFactory;
import parser.core.IParser;
import parser.core.Node;
import parser.core.visitor.PrettyPrinterVisitor;

public class Main {
    public static void main(String[] args) {
        String input = "{ \"greeting\": \"Hello, world!\" }";
        IParser parser = ParserFactory.getParser("json");
        Node ast = parser.parse(input);

        PrettyPrinterVisitor printer = new PrettyPrinterVisitor();
        ast.accept(printer);

        // Extend: Add ValidatorVisitor, ConverterVisitor, etc.
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

## 🤩 Advanced Features & Extensibility

- **Add new format?**
    1. Add new package in `parser/`.
    2. Implement `IParser` and a custom tokenizer in that package.
    3. Register parser in `ParserFactory`.

- **AST Visitors:**  
  Add custom visitors (e.g., for metrics, transformation, validation).

- **Schema Validation:**  
  Build schema and type rules for custom business logic.

- **Streaming/Chunked Parsing:**  
  Upgrade tokenization and parsing for very large files.

---

## 🛠️ Development Tips

- **Commits:**  
  `git init` and commit early, commit often!
- **Refactor:**  
  Use IntelliJ's refactor tools for safe, automatic code changes.
- **Tests:**  
  Add input samples under `src/test` and use JUnit for validation.

---

## 📝 License

**MIT License**  
See [LICENSE](LICENSE) for details.

---

## ✨ Description

> **Universal Data Parser (Java, Manual Implementation):**  
> Architected and implemented a custom parsing framework in Java supporting JSON, XML, and YAML. Built recursive descent parsers, tokenizers, and a unified AST from scratch. Applied OO design patterns (Factory, Strategy, Composite, Visitor) for maximum maintainability and extensibility. Developed robust error handling, pretty-printing, format conversion, and schema validation features—demonstrating expert-level code architecture and parsing know-how.

---

**Ready to parse anything. Fork, build, extend, and impress! 🎉**