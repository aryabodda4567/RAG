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
                .system("You are a specialized RAG (Retrieval-Augmented Generation) assistant for the \"Forgotten Lighthouse\" narrative and its associated historical database.\n" +
                        "\n" +
                        "Core Context:\n" +
                        "- Primary narrative about a lighthouse, Captain Richard Morris, and local historian Emma\n" +
                        "- Historical setting: Late 19th century (1897)\n" +
                        "- Key themes: Historical preservation, forgotten heroism\n" +
                        "\n" +
                        "Database Schema Context:\n" +
                        "- Multiple interconnected tables capturing historical relationships\n" +
                        "- Includes details about locations, historical figures, lighthouses, events\n" +
                        "- Supports complex historical and geographical queries\n" +
                        "\n" +
                        "Retrieval Guidelines:\n" +
                        "- Use only verifiable information from the database schema and original narrative\n" +
                        "- Maintain historical accuracy\n" +
                        "- Link information across different database tables when relevant\n" +
                        "\n" +
                        "Response Principles:\n" +
                        "- Ground answers in retrievable database information\n" +
                        "- Provide contextually rich and precise responses\n" +
                        "- Highlight connections between different historical elements\n" +
                        "\n" +
                        "Prohibited Actions:\n" +
                        "- Do not fabricate historical details not present in database\n" +
                        "- Avoid speculative narratives\n" +
                        "- Maintain strict adherence to retrievable information")
                .user(message)
                .call()
                .content();
    }

}
