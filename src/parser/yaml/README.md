# YAMLParser Grammar

## Supported YAML Subset

This parser supports a useful subset of YAML syntax, focusing on **keys/values, nested mappings, and sequences** (lists) with indentation-aware hierarchy. It is designed for readability and simple data-serialization.

### Tokens

| Token           | Example             | Meaning                            |
|-----------------|---------------------|------------------------------------|
| KEY             | `foo:`              | Mapping key (without the colon)    |
| SEQUENCE_ENTRY  | `- item`            | List entry                         |
| SCALAR          | `value`             | Plain scalar value                 |
| STREAM_END      | (N/A)               | End of document                    |

### Indentation

- **Indentation level** (number of spaces or tabs) is critical for defining nesting and hierarchy.
- Items at greater indentation are nested beneath the parent (mapping or sequence).

---

## Simplified EBNF (Extended Backus-Naur Form) Grammar

```ebnf
YAML            ::= Block
Block           ::= (Node)*
Node            ::= Mapping | Sequence | Scalar

Mapping         ::= KEY (InlineValue | NestedBlock)
InlineValue     ::= SCALAR         // `key: value`
NestedBlock     ::= Block          // `key:` followed by indented block

Sequence        ::= SequenceEntries
SequenceEntries ::= (SEQUENCE_ENTRY NestedBlock?)+

Scalar          ::= SCALAR

// Key tokens are always followed by a value on same or greater indentation
// Sequence entries can themselves contain mappings or sequences if indented
```

---

### Rule Details

- **Mapping (`key:`)**  
  A key followed by a value on the same line creates a key-value pair.  
  If no value is present, nested mappings/sequences are expected at the next indentation level.
- **Sequence (`- item`)**  
  Each sequence entry begins with `-` at the correct indentation.  
  Nested keys or sequences under an entry must be more indented.
- **Scalar**  
  Plain value without a key or dash.

---

## Parsing Examples

YAML:
```yaml
name: John Doe
age: 30
skills:
  - Python
  - Java
  - C++
projects:
  - title: Parser
    year: 2024
  - title: Compiler
    year: 2023
```

---

## Errors

- **Indentation errors** or unexpected token sequences can yield parsing errors or malformed trees.
- Only simple mappings and lists are supported—flows, anchors, and complex YAML features are not.

## Tip

- All whitespace rules follow common YAML conventions: each level must be consistently indented.

---
