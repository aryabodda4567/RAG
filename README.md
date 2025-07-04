# RAG (Retrieval-Augmented Generation API)

This project implements a backend service for Retrieval-Augmented Generation (RAG), enabling natural language queries over custom data sources using Large Language Models (LLMs) and vector search. It is built with Java, Spring Boot, and integrates vector embedding, retrieval, and chat-based answer generation.

---

## Features

- **Embedding & Vector Store Initialization:**  
  - Index files from a directory, split data as needed, and create vector embeddings using a configurable model.
  - Endpoints to initialize (`/api/v1/init`) and load (`/api/v1/load`) the vector store.

- **Retrieval-Augmented Chat API:**  
  - `/api/v1/chat` endpoint accepts a natural language prompt.
  - Uses RAG: retrieves relevant chunks from vector DB, forms a DQL (Data Query Language) statement, executes it, and returns results.
  - Safe execution: Only allows DQL (read-only) statements.

- **Database Integration:**  
  - Integrates with a SQL database for running read-only queries generated by the LLM.

- **Web UI:**  
  - Simple web frontend for submitting queries and displaying structured results.

---

## How It Works

1. **Embed & Index Documents:**  
   Files (e.g., `.txt`) from a specified directory are split, embedded, and indexed into a vector store.

2. **Query Pipeline:**  
   - User submits a prompt to `/api/v1/chat`.
   - The system retrieves relevant document chunks using vector search.
   - LLM (via `ChatClientService`) generates a DQL statement.
   - The statement is validated for safety and executed on the database.
   - Results are returned as a structured response.

---

## Example API Usage

### Initialize Embeddings
```
GET /api/v1/init?test_prompt=Your+sample+prompt
```

### Load Vector Store
```
GET /api/v1/load
```

### Ask a Question
```
POST /api/v1/chat?prompt=Which+employees+joined+in+2023?
```

---

## Project Structure

- `src/main/java/org/project/ai/queryai/controller/AppController.java`  
  Main REST API controller for chat and initialization endpoints.

- `src/main/java/org/project/ai/queryai/service/RAGGenerator.java`  
  Handles document reading, splitting, and vector DB creation.

- `src/main/java/org/project/ai/queryai/service/ChatClientService.java`  
  Interfaces with LLM/AI chat model to generate queries and answers.

- `src/main/java/org/project/ai/queryai/service/dbservice/DatabaseService.java`  
  Executes DQL statements on the backend database, with safety checks.

- `src/main/java/org/project/ai/queryai/util/FileService.java`  
  Loads files from disk for embedding and retrieval.

- `src/main/resources/static/index.html`  
  Basic web UI for submitting natural language queries.

---

## Technologies Used

- Java, Spring Boot
- Vector embeddings and search (Spring AI, Ollama, or similar)
- SQL database (via Spring's JdbcTemplate)
- Web front-end (HTML/CSS/JS)

---

## Getting Started

1. **Clone the repository**
    ```sh
    git clone git@github.com:aryabodda4567/RAG.git
    cd RAG
    ```

2. **Configure embedding model and paths** in `application.properties` or via environment variables.

3. **Build and run the application**
    ```sh
    ./mvnw spring-boot:run
    ```

4. **Open the web UI**
    Visit [http://localhost:8080](http://localhost:8080) and enter your query.

---

## License

MIT

---

*For more information, see the source code.*
