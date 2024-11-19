package org.project.ai.queryai.service;

import org.springframework.ai.embedding.EmbeddingOptions;

public class EmbeddingOption implements EmbeddingOptions {
    String model;
    int dimensions;

    EmbeddingOption(String model) {
        this.model = model;
    }

    EmbeddingOption(String model, int dimensions) {
        this.model = model;
        this.dimensions = dimensions;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public Integer getDimensions() {
        return this.dimensions;
    }
}
