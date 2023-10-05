package ru.egorov.dto;

import java.util.ArrayList;
import java.util.List;

public class CriteriaResultResponse {
    private Criteria criteria;
    private List<CustomerSearchDTO> results;

    public CriteriaResultResponse(Criteria criteria, List<CustomerSearchDTO> results) {
        this.criteria = criteria;
        this.results = results;
    }

    public CriteriaResultResponse() {
        results = new ArrayList<>();
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public List<CustomerSearchDTO> getResults() {
        return results;
    }

    public void setResults(List<CustomerSearchDTO> results) {
        this.results = results;
    }

}
