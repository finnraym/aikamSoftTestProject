package ru.egorov.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.egorov.model.Buyer;
import ru.egorov.service.Answer;
import ru.egorov.service.Criteria;
import ru.egorov.service.SearchAnswer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JSONReader {
    public static JSONObject readFile(String filePath) throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(filePath));
        return (JSONObject) obj;
    }

    public static void writeToFile(String filePath, Answer answer) throws IOException {
        JSONObject json = new JSONObject();
        json.put("type", answer.getType());

        if (answer.getType().equals("search")) {
            SearchAnswer sa = (SearchAnswer) answer;
            JSONArray array = new JSONArray();
            Map<Criteria, List<Buyer>> results = sa.getResults();
            for(Map.Entry<Criteria, List<Buyer>> entry : results.entrySet()) {
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


        try(FileWriter writer = new FileWriter(filePath)) {
            writer.write(json.toJSONString());
            writer.flush();
        }
    }


}
