package org.project.ai.queryai.service;

import lombok.Data;
import org.project.ai.queryai.util.DataSplitter;
import org.project.ai.queryai.util.FileService;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class RAGGenerator {


    private FileService fileService;

    public RAGGenerator() {
        this.fileService = new FileService();
    }

    public List<String> getDocuments(String path) throws IOException {
        List<String> data = new ArrayList<>();
        fileService.initializeFileIterator(path);

        while (fileService.fileIterator.hasNext())
            data.add(fileService.getNextFileContent());

        return data;
    }


    public SimpleVectorStore generateRAG(String path, String embedModel) throws IOException {
        List<String> documents = getDocuments(path);
        List<String> splitDocument = new ArrayList<>();
        for (String document : documents) {
            splitDocument.addAll(DataSplitter.splitData(document));
        }

        return new VectorService(embedModel, splitDocument).getVectorStore();
    }


}
