package ru.egorov.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Criteria {

//    private final String criteriaId;
    private final Map<String, String> container;

    public Criteria() {
        this.container = new HashMap<>();
    }

    public void addCriteria(String key, String value) {
        container.put(key, value);
    }

    public boolean containsCriteria(String key) {
        return container.containsKey(key);
    }
    public String getValue(String key) {
        return container.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Criteria criteria = (Criteria) o;

        if (this.container.size() != criteria.container.size()) return false;

        for(Map.Entry<String, String> entry : this.container.entrySet()) {
            String value = criteria.getValue(entry.getKey());
            if (value == null) return false;

            if (!value.equals(entry.getValue())) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(container);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Criteria:\n");

        container.forEach((key, value) -> {
            builder.append("Key: ").append(key).append(" ").append("Value: ").append(value).append("\n");
        });

        return builder.toString();
    }
}
