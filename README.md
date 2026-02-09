# HTML Analyzer

A Java command-line tool developed to retrieve the text located at the deepest level of an HTML structure.

## Features

- **Deepest Text Extraction:** Identifies and outputs the text within the deepest HTML tag.
- **Malformed HTML Detection:** Validates the HTML structure using a Stack-based algorithm (Bonus Requirement).
- **Zero Dependencies:** Built strictly using the Java Standard Library (JDK 17), with no external frameworks or libraries.
- **Error Handling:** robust handling for connection issues and HTTP errors.

## Prerequisites

- **Java Development Kit (JDK) 17** must be installed and configured in your system path.

## How to Compile

Open your terminal in the directory containing `HtmlAnalyzer.java` and run:

```bash
javac HtmlAnalyzer.java
java HtmlAnalyzer <URL>
