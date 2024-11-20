package org.project.ai.queryai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ChatClientService {

    ChatClient chatClient;

    @Autowired
    public ChatClientService(ChatClient.Builder builder) {

        this.chatClient = builder.defaultAdvisors().build();
    }

    public String chat(String message) {

        return chatClient
                .prompt()
                .user(message)
                .call()
                .content();
    }

    public String chat(String message, VectorStore vectorStore) {

        return chatClient
                .prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
                .system( " Node.js Project Documentation Template\n" +
                        "\n" +
                        "## 1. Project Overview\n" +
                        "- Provide a brief summary of the project's purpose and its main functionalities.\n" +
                        "- What is the project trying to achieve, and what problem does it solve?\n" +
                        "- Identify the core features of the application.\n" +
                        "\n" +
                        "## 2. Technologies Used\n" +
                        "- List all the technologies and frameworks used in the project, including:\n" +
                        "  - Node.js version\n" +
                        "  - Any external libraries or dependencies (e.g., Express, Mongoose, Sequelize, etc.)\n" +
                        "  - Database (e.g., MySQL, PostgreSQL, MongoDB) and its version\n" +
                        "  - Other tools and utilities (e.g., Nodemon, JWT for authentication, etc.)\n" +
                        "\n" +
                        "## 3. Project Structure\n" +
                        "- Provide a high-level view of the directory structure.\n" +
                        "  - Example:\n" +
                        "    ```\n" +
                        "    /src\n" +
                        "      /controllers\n" +
                        "      /models\n" +
                        "      /routes\n" +
                        "      /middlewares\n" +
                        "    /config\n" +
                        "    /public\n" +
                        "    /tests\n" +
                        "    ```\n" +
                        "\n" +
                        "- Describe each directory and its role in the project:\n" +
                        "  - Controllers\n" +
                        "  - Models\n" +
                        "  - Routes\n" +
                        "  - Middlewares\n" +
                        "  - etc.\n" +
                        "\n" +
                        "## 4. Database Schema Documentation\n" +
                        "- Review the provided SQL schema.\n" +
                        "- List and explain all tables, columns, and their relationships.\n" +
                        "- Describe any foreign keys or constraints.\n" +
                        "- Mention how data is being manipulated (CRUD operations) in the application.\n" +
                        "\n" +
                        "## 5. API Endpoints\n" +
                        "- List all the REST API endpoints exposed by the application.\n" +
                        "- For each endpoint, provide the following:\n" +
                        "  - **HTTP Method (GET, POST, PUT, DELETE, etc.)**\n" +
                        "  - **Endpoint URL** (e.g., `/api/users`)\n" +
                        "  - **Request parameters** (e.g., query params, body, or URL params)\n" +
                        "  - **Response format** (e.g., JSON, HTTP status codes)\n" +
                        "  - **Example Request** (if applicable)\n" +
                        "  - **Example Response**\n" +
                        "  - **Error Handling** (what kind of errors or validations to expect)\n" +
                        "\n" +
                        "## 6. Authentication and Authorization\n" +
                        "- Describe the authentication mechanism used (e.g., JWT, session-based).\n" +
                        "- Explain how users log in, register, and how authentication tokens are handled.\n" +
                        "- Outline any role-based access control (RBAC) if applicable.\n" +
                        "\n" +
                        "## 7. Data Flow and Business Logic\n" +
                        "- Describe the overall data flow in the application (e.g., how a request is processed, how data flows from the API to the database, and how responses are sent back).\n" +
                        "- Break down the key business logic involved in the application.\n" +
                        "\n" +
                        "## 8. Error Handling and Logging\n" +
                        "- Explain the error handling strategy used throughout the project.\n" +
                        "- Mention any custom error classes, error codes, or middleware used to catch errors.\n" +
                        "- Describe any logging or monitoring tools integrated into the project.\n" +
                        "\n" +
                        "## 9. Testing\n" +
                        "- List the testing strategy used (e.g., unit tests, integration tests).\n" +
                        "- Mention any testing libraries or frameworks used (e.g., Mocha, Chai, Jest).\n" +
                        "- Provide an overview of the test suite, including the types of tests and what they cover.\n" +
                        "\n" +
                        "## 10. Environment Variables and Configuration\n" +
                        "- Describe how environment variables are used in the project (e.g., `.env` files).\n" +
                        "- List all required environment variables and their purpose (e.g., `DB_HOST`, `JWT_SECRET`, etc.).\n" +
                        "  \n" +
                        "## 11. How to Run the Project Locally\n" +
                        "- Provide step-by-step instructions on how to set up the project locally:\n" +
                        "  1. Clone the repository\n" +
                        "  2. Install dependencies\n" +
                        "  3. Set up environment variables\n" +
                        "  4. Run the project\n" +
                        "  5. Access the application in the browser\n" +
                        "  \n" +
                        "## 12. Deployment\n" +
                        "- If applicable, describe the deployment process (e.g., hosting on Heroku, AWS, or other platforms).\n" +
                        "- Mention any CI/CD pipelines or automated deployment setups if integrated.\n" +
                        "\n" +
                        "## 13. Future Improvements/To-Do List\n" +
                        "- Suggest any improvements, features to be added, or areas for further development.\n" +
                        "\n" +
                        "---\n" +
                        "\n" +
                        "# Additional Notes:\n" +
                        "- Ensure all database relations and dependencies are clearly explained.\n" +
                        "- Provide clear code examples where needed for complex logic or API interactions.\n" +
                        "- Include diagrams if necessary, such as database ER diagrams, data flow diagrams, etc.")
                .user(message)
                .call()
                .content();
    }

}
