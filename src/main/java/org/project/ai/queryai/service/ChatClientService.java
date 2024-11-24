package org.project.ai.queryai.service;

import org.project.ai.queryai.util.FileService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class ChatClientService {

    ChatClient chatClient;

    @Value("${custom.query.ai.embed.system_prompt_path}")
    String SYSTEM_PROMPT_PATH;

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

    public String chat(String message, VectorStore vectorStore) throws IOException {
        String systemPrompt = getSystemPrompt();
        return chatClient
                .prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
                .system(systemPrompt)
                .user(message)
                .call()
                .content();
    }

    private String getSystemPrompt() throws IOException {
        FileService fileService = new FileService();
        fileService.initializeFileIterator(SYSTEM_PROMPT_PATH);
        return fileService.getNextFileContent();
    }

}
