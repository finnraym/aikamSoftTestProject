package ru.egorov.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.egorov.model.Buyer;
import ru.egorov.model.Purchase;
import ru.egorov.service.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JSONWriter {
    public void writeToFile(String filePath, Response response) throws IOException {
        JSONObject json = new JSONObject();
        String type = response.getType();
        json.put("type", type);

        switch (type) {
            case "search":
                writeSearchResult(json, (SearchResponse) response);
                break;
            case "stat":
                writeStatResult(json, (StatResponse) response);
                break;
            case "error":
                writeErrorResult(json, (ErrorResponse) response);
                break;
        }

        try(FileWriter writer = new FileWriter(filePath)) {
            writer.write(json.toJSONString());
            writer.flush();
        }
    }

    private void writeSearchResult(JSONObject json, SearchResponse response) {
        JSONArray array = new JSONArray();
        Map<Criteria, List<Buyer>> results = response.getResults();
        for (Map.Entry<Criteria, List<Buyer>> entry : results.entrySet()) {
            JSONObject current = new JSONObject();
            Criteria criteria = entry.getKey();
            Map<String, String> container = criteria.getContainer();
            JSONObject criteriaJson = new JSONObject();
            criteriaJson.putAll(container);
            current.put("criteria", criteriaJson);
            JSONArray array1 = new JSONArray();

            List<Buyer> value = entry.getValue();
            for (Buyer buyer : value) {
                JSONObject jsonBuyer = new JSONObject();
                jsonBuyer.put("lastName", buyer.getLastName());
                jsonBuyer.put("firstName", buyer.getFirstName());
                array1.add(jsonBuyer);
            }

            current.put("results", array1);
            array.add(current);
        }
        json.put("results", array);
    }

    private void writeStatResult(JSONObject json, StatResponse response) {
        JSONArray customers = new JSONArray();

        List<Buyer> buyers = response.getBuyers();
        for (Buyer buyer : buyers) {
            JSONObject customer = new JSONObject();
            JSONArray purchases = new JSONArray();

            List<Purchase> purchaseList = buyer.getPurchases();
            for (Purchase purchase : purchaseList) {
                JSONObject pur = new JSONObject();
                pur.put("name", purchase.getProductName());
                pur.put("expenses", purchase.getExpenses());
                purchases.add(pur);
            }

            customer.put("name", buyer.getLastName() + " " + buyer.getFirstName());
            customer.put("purchases", purchases);
            customer.put("totalExpense", buyer.getTotalExpenses());
            customers.add(customer);
        }

        json.put("totalDays", response.getTotalDays());
        json.put("customers", customers);
        json.put("totalExpenses", response.getTotalExpenses());
        json.put("avgExpenses", response.getAvgExpenses());
    }

    private void writeErrorResult(JSONObject json, ErrorResponse response) {
        json.put("message", response.getMessage());
    }
}
