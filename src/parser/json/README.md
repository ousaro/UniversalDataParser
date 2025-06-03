# JSONParser Grammar

## JSON Grammar

This parser implements a **recursive descent JSON parser** based on the following grammar, closely resembling the [ECMA-404](https://www.json.org/json-en.html) standard:

### Terminals

| Token        | Means                | Example            |
|--------------|----------------------|--------------------|
| `{`          | LEFT_BRACE           | `{`                |
| `}`          | RIGHT_BRACE          | `}`                |
| `[`          | LEFT_BRACKET         | `[`                |
| `]`          | RIGHT_BRACKET        | `]`                |
| `:`          | COLON                | `:`                |
| `,`          | COMMA                | `,`                |
| string       | STRING               | `"foo"`            |
| number       | NUMBER               | `123`, `1.23`      |
| `true`       | TRUE                 | `true`             |
| `false`      | FALSE                | `false`            |
| `null`       | NULL                 | `null`             |

### Non-terminals

#### JSON text:
```
JSON → Value
```

#### Value:
```
Value → STRING
       | NUMBER
       | Object
       | Array
       | true
       | false
       | null
```

#### Object:
```
Object → '{' '}'
        | '{' Members '}'
Members → Pair (',' Pair)*
Pair    → STRING ':' Value
```

#### Array:
```
Array → '[' ']'
       | '[' Elements ']'
Elements → Value (',' Value)*
```

### Summary in EBNF

```ebnf
JSON      ::= Value
Value     ::= STRING | NUMBER | Object | Array | 'true' | 'false' | 'null'
Object    ::= '{' '}' | '{' Members '}'
Members   ::= Pair (',' Pair)*
Pair      ::= STRING ':' Value
Array     ::= '[' ']' | '[' Elements ']'
Elements  ::= Value (',' Value)*
```

## Error Handling

- **Unexpected tokens** or illegal sequences result in a `ParseException`.
- Objects require string keys.
- Additional tokens after a complete JSON value are considered errors.

## Example

Valid JSON parsed by this grammar:
```json
{
  "name": "Alice",
  "age": 27,
  "admin": false,
  "tags": ["user", "editor"],
  "details": null
}
```
