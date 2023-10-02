package ru.egorov.service;

import ru.egorov.model.Buyer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchAnswer extends Answer {

    private final Map<Criteria, List<Buyer>> results;

    public SearchAnswer() {
        this.setType("search");
        this.results = new HashMap<>();
    }

    public void addResult(Criteria criteria, List<Buyer> buyers) {
        results.put(criteria, buyers);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("type: ").append(this.getType()).append("\n");
        builder.append("results: [\n");

        for(Map.Entry<Criteria, List<Buyer>> entry : results.entrySet()) {
            builder.append("criteria: ").append(entry.getKey()).append("\n");
            List<Buyer> buyers = entry.getValue();
            builder.append("Buyers: \n");
            for (Buyer buyer : buyers) {
                builder.append(buyer.toString()).append("\n");
            }

        }

        builder.append("]");
        return builder.toString();
    }

    public Map<Criteria, List<Buyer>> getResults() {
        return results;
    }
}
