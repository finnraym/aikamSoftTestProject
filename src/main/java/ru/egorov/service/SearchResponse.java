package ru.egorov.service;

import ru.egorov.model.Buyer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResponse extends Response {

    private final Map<Criteria, List<Buyer>> results;

    public SearchResponse() {
        super("search");
        this.results = new HashMap<>();
    }

    public void addResult(Criteria criteria, List<Buyer> buyers) {
        results.put(criteria, buyers);
    }

    public Map<Criteria, List<Buyer>> getResults() {
        return results;
    }
}
