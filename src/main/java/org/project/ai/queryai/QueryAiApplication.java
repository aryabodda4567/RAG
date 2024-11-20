package org.project.ai.queryai;

import org.project.ai.queryai.service.ChatClientService;
import org.project.ai.queryai.service.RAGGenerator;
import org.project.ai.queryai.service.VectorService;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
public class QueryAiApplication {








    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(QueryAiApplication.class, args);



    }

}
