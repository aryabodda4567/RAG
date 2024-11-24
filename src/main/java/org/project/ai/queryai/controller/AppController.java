package org.project.ai.queryai.controller;


import org.project.ai.queryai.dto.DataDTO;
import org.project.ai.queryai.model.EmbeddingModel;
import org.project.ai.queryai.service.*;
import org.project.ai.queryai.service.dbservice.DatabaseService;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class AppController {
    RAGGenerator ragGenerator;
    ChatClientService chatClientService;
    DatabaseService databaseService;

    InitializeVectorService initializeVectorService;


    @Value("${custom.query.ai.embed.path}")
    private String embedPath;

    @Value("${spring.ai.ollama.embedding.options.model}")
    private String model;


    AppController(ChatClientService chatClientService, RAGGenerator ragGenerator,
                  InitializeVectorService initializeVectorService
            , DatabaseService databaseService) {
        this.chatClientService = chatClientService;
        this.ragGenerator = ragGenerator;
        this.initializeVectorService = initializeVectorService;
        this.databaseService = databaseService;
    }


    //Api for creating embeddings
    @PostMapping("/init")
    public String init(@RequestParam(name = "test_prompt") String prompt) throws IOException {
        if (prompt.isEmpty()) {
            return "Prompt should be provided";
        }
        return chatClientService.chat(prompt,
                ragGenerator.generateRAG(embedPath, model));
    }

    //Api for load created embeddings
    @GetMapping("/load")
    public String load() throws IOException {
        SimpleVectorStore simpleVectorStore = new VectorService(model).
                getVectorStore(new EmbeddingModel().load());

        initializeVectorService.setVectorStore(simpleVectorStore);
        return "Started";

    }


    //Api for chat
    @PostMapping("/chat")
    public DataDTO chat(@RequestParam(name = "prompt") String prompt) throws IOException {


        if (prompt.isEmpty()) {
            return DTOWrapper.wrapDTO("Prompt should be provided");
        }


        if (initializeVectorService == null) {
            return DTOWrapper.wrapDTO("Initialize Vector Service");
        }

        if (!databaseService.isValidSafe(prompt)) {
            return DTOWrapper.wrapDTO("DQL statement not provided");
        }


        String DQLStatement = chatClientService.chat(prompt, initializeVectorService.getVectorStore());


        List<Map<String, Object>> result = databaseService.executeDQL(DQLStatement);

        return DTOWrapper.wrapDTO(result);

    }


}
