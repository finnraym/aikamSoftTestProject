package ru.egorov.dto;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse implements Response {

    private final String type;

    private final List<CriteriaResultResponse> results;

    public SearchResponse() {
        type = "search";
        results = new ArrayList<>();
    }

    @Override
    public String getType() {
        return type;
    }

    public List<CriteriaResultResponse> getResults() {
        return results;
    }

    public void addResult(CriteriaResultResponse result) {
        results.add(result);
    }
}
