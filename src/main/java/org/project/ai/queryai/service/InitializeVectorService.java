package org.project.ai.queryai.service;


import lombok.Getter;
import lombok.Setter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Service
public class InitializeVectorService {

    SimpleVectorStore vectorStore;

}
