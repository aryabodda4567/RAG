package org.project.ai.queryai.controller;


import org.project.ai.queryai.service.ChatClientService;
import org.project.ai.queryai.service.RAGGenerator;
import org.project.ai.queryai.service.VectorService;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class AppController {
    RAGGenerator ragGenerator ;
    ChatClientService chatClientService;



    @Value("${custom.query.ai.embed.path}")
    private String embedPath;

    @Value("${spring.ai.ollama.embedding.options.model}")
    private String model;




    AppController(ChatClientService chatClientService,RAGGenerator ragGenerator) {
        this.chatClientService = chatClientService;
         this.ragGenerator = ragGenerator;
    }


    @PostMapping("/chat")
    public String chat(@RequestParam(name = "prompt") String prompt) throws IOException {
        if(prompt.isEmpty()){
            return "Prompt should be provided";
        }

         return chatClientService.chat(prompt, ragGenerator.generateRAG(embedPath,model));
    }



}
