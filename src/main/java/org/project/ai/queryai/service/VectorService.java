package org.project.ai.queryai.service;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.ai.vectorstore.SimpleVectorStore;

import java.util.ArrayList;
import java.util.List;


public class VectorService {


    private final OllamaApi ollamaApi;
    private final List<String> splitDocument;
    public String embedModel;


    public VectorService(String model, List<String> splitDocument) {
        this.ollamaApi = new OllamaApi();
        this.embedModel = model;
        this.splitDocument = splitDocument;
    }

    private OllamaEmbeddingModel getEmbeddingModel() {

        return new OllamaEmbeddingModel(
                ollamaApi,
                OllamaOptions.builder().withModel(embedModel).build(),
                ObservationRegistry.NOOP,
                ModelManagementOptions.builder().build()
        );
    }

    private EmbeddingResponse getEmbeddingResponse(OllamaEmbeddingModel embeddingModel,
                                                   List<String> splitDocument) {

        return embeddingModel.call(
                new EmbeddingRequest(splitDocument,
                        new EmbeddingOption(this.embedModel)));
    }

    public SimpleVectorStore getVectorStore() {
        SimpleVectorStore simpleVectorStore = new SimpleVectorStore(getEmbeddingModel());

        OllamaEmbeddingModel ollamaEmbeddingModel = getEmbeddingModel();

        EmbeddingResponse embeddingResponse = getEmbeddingResponse(ollamaEmbeddingModel, splitDocument);


        List<Document> documentList = new ArrayList<>();
        List<Embedding> embeddings = embeddingResponse.getResults();

        int i = 0;
        for (Embedding embedding : embeddings) {
            Document document = new Document(splitDocument.get(i));
            document.setEmbedding(embedding.getOutput());
            documentList.add(document);
            ++i;
        }
        simpleVectorStore.add(documentList);
        return simpleVectorStore;

    }


}
