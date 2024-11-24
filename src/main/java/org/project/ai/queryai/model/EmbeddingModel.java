package org.project.ai.queryai.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor

public class EmbeddingModel {

    List<float[]> embeddings = new ArrayList<>();
    List<String> embeddingLabels = new ArrayList<>();
    // @Value("${file.path}")
    private String FILE_PATH = "D:\\Vector-path\\embeddingModel.json";

    public void addEmbedding(float[] embedding) {
        embeddings.add(embedding);
    }

    public void addEmbeddingLabel(String label) {
        embeddingLabels.add(label);
    }

    public void save() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(FILE_PATH), this);
        System.out.println("Embedding model saved to: " + FILE_PATH);
    }

    public EmbeddingModel load() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(FILE_PATH), EmbeddingModel.class);
    }

}
