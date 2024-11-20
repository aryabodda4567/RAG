package org.project.ai.queryai.service;

import io.micrometer.observation.ObservationRegistry;
import org.project.ai.queryai.model.EmbeddingModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class VectorService implements Serializable {


    private final OllamaApi ollamaApi;
    private final List<String> splitDocument;
    public String embedModel;


    public VectorService(String embedModel) {
        ollamaApi = new OllamaApi();
        splitDocument = new ArrayList<>();
        this.embedModel= embedModel;

    }

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

    public SimpleVectorStore getVectorStore() throws IOException {
        SimpleVectorStore simpleVectorStore = new SimpleVectorStore(getEmbeddingModel());
        OllamaEmbeddingModel ollamaEmbeddingModel = getEmbeddingModel();

        System.out.println("Generating embeddings...");
        EmbeddingResponse embeddingResponse = getEmbeddingResponse(ollamaEmbeddingModel, splitDocument);
        System.out.println("Embeddings generated");

        //Add embedding and split text to EmbeddingModel
        EmbeddingModel embeddingModel = new EmbeddingModel();



        System.out.println("Adding embeddings to vectors");
        List<Document> documentList = new ArrayList<>();
        List<Embedding> embeddings = embeddingResponse.getResults();

        int i = 0;
        for (Embedding embedding : embeddings) {
            //Adding embeddings to embedding model
            embeddingModel.addEmbedding(embedding.getOutput());
            embeddingModel.addEmbeddingLabel(splitDocument.get(i));

            //Add embeddings to document
            Document document = new Document(splitDocument.get(i));
            document.setEmbedding(embedding.getOutput());
            documentList.add(document);
            ++i;
        }
        embeddingModel.save();


        simpleVectorStore.add(documentList);


         return simpleVectorStore;

    }

    public  SimpleVectorStore getVectorStore(EmbeddingModel embeddingModel) throws IOException {
        List<float[]> embeddings = embeddingModel.getEmbeddings();
        List<String> splitDocuments = embeddingModel.getEmbeddingLabels();
        SimpleVectorStore simpleVectorStore = new SimpleVectorStore(getEmbeddingModel());

        System.out.println("Adding embeddings to vectors");
        List<Document> documentList = new ArrayList<>();
        for(int i=0;i<embeddings.size();i++) {
            Document document = new Document(splitDocuments.get(i));
            document.setEmbedding(embeddings.get(i));
            documentList.add(document);
        }
        simpleVectorStore.add(documentList);
        return simpleVectorStore;

    }











}
