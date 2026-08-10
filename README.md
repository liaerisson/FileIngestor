# Java Document Search Engine

A Java document search engine with both a command-line interface and a Spring Boot REST API.

The application reads `.txt` files from a user-selected directory or accepts documents as JSON, builds an in-memory inverted index, and returns ranked results for single- and multi-word queries.

![ingestorimage.png](ingestorimage.png)

I built this project to strengthen my understanding of software design, Java collections, file processing, unit testing, and REST API usage while learning how keyword searches actually work.

## Features

* Loads `.txt` files recursively from a directory
* Converts files into `Document` objects with unique IDs
* Normalizes documents and queries through the same tokenizer
* Builds an in-memory inverted index
* Tracks term frequency by document
* Supports single- and multi-term queries
* Uses OR search semantics
* Ranks results by combined query-term frequency
* Provides an interactive command-line interface

Newest Updates:
* Exposes the same search logic through a Spring Boot REST API
* Supports `GET /search` and `POST /documents`
* Handles JSON request deserialization and response serialization
* Validates document requests with Bean Validation
* Uses constructor-based dependency injection
* Returns `201 Created`, `400 Bad Request`, and `409 Conflict` where appropriate
* Includes unit, integration, and MockMvc web-layer tests

## Architecture

```text
CLI / file ingestion                 REST / JSON input

.txt files                           HTTP + JSON
    ↓                                    ↓
DocumentLoader                       SearchController
    ↓                                    ↓
Document                         DocumentRequest → Document
    ↓                                    ↓
    └──────────────→ SearchEngine ←──────┘
                         ↓
                    Tokenizer
                         ↓
                   InvertedIndex
                         ↓
                  QueryProcessor
                         ↓
                   SearchResult
                    ↓         ↓
               CLI output   JSON response
```

### Core classes

* **`Document`** — stores a document ID, filename, path, and content
* **`DocumentLoader`** — reads `.txt` files and creates `Document` objects
* **`Tokenizer`** — lowercases text, replaces punctuation, and splits on whitespace
* **`InvertedIndex`** — maps each term to the documents containing it and their occurrence counts
* **`QueryProcessor`** — combines term matches, calculates scores, and sorts results
* **`SearchResult`** — stores a matching document and its score
* **`SearchEngine`** — provides a simple public interface for indexing and searching
* **`SearchCli`** — handles terminal input and output
* **`SearchApplication`** — starts the Spring Boot application
* **`SearchController`** — maps HTTP requests to the existing `SearchEngine`
* **`DocumentRequest`** — defines and validates the JSON body for document creation
* **`DocumentAlreadyExistsException`** — represents a duplicate document ID and produces `409 Conflict`

The central data structure is:

```java
Map<String, Map<Integer, Integer>>
```

which represents:

```text
term → document ID → occurrence count
```

## Search Behavior

For a query like:

```text
java search
```

the processor retrieves the occurrences for each term and combines their frequencies by document.

```text
java   → {1=3, 2=1}
search → {1=1, 3=2}

scores:
document 1 → 4
document 2 → 1
document 3 → 2
```

Results are returned in descending score order. The queries themselves use OR semantics, so a document is included if it contains at least one query term.

## REST API

### Search documents

```http
GET /search?query=java
```

Returns `200 OK` with a JSON array of ranked results. A query with no matches returns an empty array.

### Add a document

```http
POST /documents
Content-Type: application/json
```

Example request body:

```json
{
  "id": 1,
  "title": "Java Notes",
  "content": "Java indexing and search"
}
```

Possible responses:

* `201 Created` — the document was added to the index
* `400 Bad Request` — the JSON body failed validation
* `409 Conflict` — the document ID already exists

## Testing

The project includes tests for:

* tokenizer normalization and punctuation handling
* spaces, tabs, line breaks, and empty input
* term-frequency counts
* duplicate document IDs
* single- and multi-term queries
* missing query terms
* result scoring and ordering
* the full `SearchEngine` workflow
* valid document creation through `POST /documents`
* invalid or missing JSON fields returning `400 Bad Request`
* duplicate IDs returning `409 Conflict`
* successful searches returning the expected JSON
* searches with no matches returning an empty JSON array
Testing was a major focus of the project. I used the test suite both to verify individual classes and to catch edge cases in the interaction between tokenization, indexing, and query processing.


## Running the Project

### Requirements

* Java Development Kit 21 or newer
* Git

I included the Maven wrapper in the repository, so it does not need to be installed.

### 1. Clone the Repo

```bash
git clone https://github.com/liaerisson/JavaIngestor.git
cd JavaIngestor
```

### 2. Run the tests

On Windows PowerShell:

```powershell
.\mvnw.cmd clean test
```

On macOS/Linux:

```bash
./mvnw clean test
```

After these run, you see:

```text
BUILD SUCCESS
```

### 3. Build the executable JAR

On Windows PowerShell:

```powershell
.\mvnw.cmd clean package
```

On macOS or Linux:

```bash
./mvnw clean package
```

This creates:

```text
target/JavaIngestor-1.0-SNAPSHOT.jar
```

### 4. Start the search engine

```bash
java -jar target/JavaIngestor-1.0-SNAPSHOT.jar
```

The CLI prompts for a directory containing `.txt` files. After the files are indexed, enter a single- or multi-word query. Enter `exit` at the search prompt to close the application.

### 4. Run the REST API

Run `SearchApplication` from the IDE. This starts the Spring Boot application and exposes `GET /search` and `POST /documents`.

Documents added through the API are stored in the active in-memory index and are not persisted across restarts.

## Design Goals

This project was intentionally built without a search library so I could work through the underlying data structures and design decisions myself.

The main goals were to:

* separate responsibilities across small, focused classes
* use Java collection interfaces appropriately
* keep query-specific state local
* maintain consistent processing between documents and queries
* reuse the same search logic across CLI and REST interfaces
* separate HTTP concerns from indexing and query processing
* use dependency injection rather than constructing controller dependencies manually
* validate external input before it reaches the search layer
* build confidence writing unit, integration, and web-layer tests

## What I Learned

Through the project, I gained practical experience with:

* Java string immutability and `StringBuilder`
* regular expressions and text normalization
* `Path`, `Files.walk`, streams, and `BufferedReader`
* nested hash maps
* object references and shared mutable structures
* comparators and result ordering
* method decomposition and class responsibility
* unit and integration testing
* REST endpoints and HTTP status semantics
* Spring Boot dependency injection
* JSON serialization and deserialization
* Bean Validation
* MockMvc and Mockito web-layer testing

## Current Limitations

* only supports `.txt` files
* uses raw term frequency rather than a relative frequency or other metrics
* does not support phrase searches, stemming (cutting words to their basic form --> running to run), or stop-word filtering (removing words like 'the', 'and', etc...)
* does not store the documents and indexes in memory

## Next Steps

* add PostgreSQL persistence for documents and metadata
* integrate PostgreSQL with Spring while retaining the custom inverted index
* add repository and database integration tests
* improve relevance ranking with TF-IDF or BM25, which prioritize informative query terms and account for document length
* add result snippets (the phrases around searched words)
* add stop-word filtering (and look into how stemming is typically done)

## Status

The command-line MVP and Spring Boot REST API extension are complete. PostgreSQL persistence is the next planned extension.