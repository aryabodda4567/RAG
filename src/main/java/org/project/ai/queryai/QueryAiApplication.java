package org.project.ai.queryai;

import org.project.ai.queryai.service.*;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
public class QueryAiApplication {

//
//     static RAGGenerator ragGenerator;
//
//    static ChatClientService chatClientService;
//
//   QueryAiApplication(RAGGenerator ragGenerator, ChatClientService chatClientService) {
//        QueryAiApplication.ragGenerator = ragGenerator;
//        QueryAiApplication.chatClientService = chatClientService;
//    }



    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(QueryAiApplication.class, args);


// VectorStore vectorDB = ragGenerator.generateRAG("D:\\verba-project\\data","mxbai-embed-large");
//
// while (true) {
//     System.out.println("Enter prompt:  ");
//     String query = new Scanner(System.in).nextLine();
//     System.out.println(chatClientService.chat(query, vectorDB));
//  }

    }

 }