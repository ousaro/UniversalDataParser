# UniversalDataParser

**UniversalDataParser** is a modular Java framework for parsing structured data formats—including **JSON**, **XML**, and **YAML**—with support for extensible AST nodes and pluggable parsers.

---

## 🚀 Quick Start

### **1. Requirements**

- **Java** 8 or newer (JDK installed)
- **IntelliJ IDEA** (or any modern Java IDE)

---

### **2. Project Structure**

```
UniversalDataParser/
│
├─ src/
│   └─ parser/
│       ├─ core/       # AST nodes, interfaces
│       ├─ factory/    # ParserFactory
│       ├─ json/       # JSON parser/tokenizer
│       ├─ xml/        # XML parser/tokenizer
│       └─ yaml/       # YAML parser/tokenizer
│
└─ README.md
```

---

### **3. Running the Project**

1. **Clone or Download:**
   ```sh
   git clone https://github.com/yourusername/UniversalDataParser.git
   cd UniversalDataParser
   ```

2. **Open in IntelliJ IDEA:**
    - `File > Open...` and select the project directory.

3. **Build Project:**
    - `Build > Build Project` (or `Ctrl+F9`)

4. **Run Demo/Main:**
    - Right-click `src/parser/Main.java` and choose **Run 'Main'**.

---

### **4. Main Entry Point Example**

```java
package parser;

public class Main {
    public static void main(String[] args) {
        System.out.println("Universal Parser Framework starting up!");
        // Add demo/test code here
    }
}
```

---

## 📁 Key Components

- **`parser/core/`**  
  Core interfaces & AST node classes (e.g., `IParser`, `Node`, `CompositeNode`)
- **`parser/factory/`**  
  Factory classes for parser instantiation
- **`parser/json/`, `parser/xml/`, `parser/yaml/`**  
  Implementations for each data format

---

## ⚙️ Adding a New Parser

1. Create a new package in `parser/` (e.g., `parser/toml`)
2. Implement `IParser` in your parser class
3. Register it in `ParserFactory`

---

## 🛠️ Development Tips

- **Package Organization:**  
  Structure by file type or responsibility for maintainability.
- **Error Checking:**  
  Use `Build > Build Project` to catch issues before running.
- **Refactoring:**  
  Use IntelliJ's `Refactor` tools to move/rename classes safely.

---

## 📖 Version Control (Recommended)

- Enable Git version control:
    - In IntelliJ:  
      `VCS > Enable Version Control Integration...` or  
      Right-click root > **Git > Enable Version Control Integration**
- Commit early, commit often!

---

## 📝 License

MIT License (MIT)
See [LICENSE](LICENSE) for details.
---