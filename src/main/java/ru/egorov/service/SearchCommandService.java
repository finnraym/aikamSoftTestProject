package ru.egorov.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import ru.egorov.dao.SearchDAO;
import ru.egorov.model.Buyer;
import ru.egorov.util.JSONReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchCommandService implements CommandService {

    private final List<Criteria> criterias;
    private final SearchAnswer answer;

    private final SearchDAO searchDAO;

    public SearchCommandService() {
        this.criterias = new ArrayList<>();
        this.answer = new SearchAnswer();
        this.searchDAO = new SearchDAO();
    }

    @Override
    public void execute(String inputFilePath, String outputFilePath) throws IOException, ParseException, SQLException {
        parseJSON((JSONArray) JSONReader.readFile(inputFilePath).get("criterias"));

        for (Criteria criteria : criterias) {
            if (criteria.containsCriteria("lastName")) {
                answer.addResult(criteria, getBuyersByLastName(criteria.getValue("lastName")));
            } else if (criteria.containsCriteria("productName")) {
                String productName = criteria.getValue("productName");
                int minTimes = Integer.parseInt(criteria.getValue("minTimes"));
                answer.addResult(criteria, getBuyersByProductNameAndPurchaseTimes(productName, minTimes));
            } else if (criteria.containsCriteria("minExpenses")) {
                int minExpenses = Integer.parseInt(criteria.getValue("minExpenses"));
                int maxExpenses = Integer.parseInt(criteria.getValue("maxExpenses"));
                answer.addResult(criteria, getBuyersByTotalPurchaseCostBetween(minExpenses, maxExpenses));
            } else if (criteria.containsCriteria("badCustomers")) {
                int badCustomers = Integer.parseInt(criteria.getValue("badCustomers"));
                answer.addResult(criteria, getBadBuyers(badCustomers));
            }
        }

        JSONReader.writeToFile(outputFilePath, answer);
    }

    private List<Buyer> getBadBuyers(int limit) {
        return new ArrayList<>();
    }

    private List<Buyer> getBuyersByLastName(String lastName) throws SQLException, IOException {
        return searchDAO.getBuyersByLastName(lastName);
    }

    private List<Buyer> getBuyersByProductNameAndPurchaseTimes(String productName, int minTimes) {
        return new ArrayList<>();
    }

    private List<Buyer> getBuyersByTotalPurchaseCostBetween(int minExpenses, int maxExpenses) {
        return new ArrayList<>();
    }
    private void parseJSON(JSONArray array) {
        for (Object o : array) {
            Criteria criteria = new Criteria();
            JSONObject obj = (JSONObject) o;
            obj.forEach((key, value) -> {
                criteria.addCriteria(key.toString(), value.toString());
            });
            criterias.add(criteria);
        }
    }
}
