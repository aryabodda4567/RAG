package org.project.ai.queryai.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class DataDTO {

    String message;

    int status;

    List<String> headings;

    List<Collection<Object>> results;

    public void addHeading(String heading) {
        if (headings == null) {
            headings = new ArrayList<>();
        }
        headings.add(heading);
    }

    public void addResult(Collection<Object> result) {
        if (results == null) {
            results = new ArrayList<>();
        }
        results.add(result);
    }

}
