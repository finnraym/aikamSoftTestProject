package ru.egorov.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import ru.egorov.dao.SearchDAO;
import ru.egorov.exception.UnknownCriteriaException;
import ru.egorov.model.Buyer;
import ru.egorov.util.JSONReader;
import ru.egorov.util.JSONWriter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchCommandService implements CommandService {

    private final List<Criteria> criterias;
    private final SearchDAO searchDAO;

    public SearchCommandService() {
        this.criterias = new ArrayList<>();
        this.searchDAO = new SearchDAO();
    }

    @Override
    public void execute(String inputFilePath, String outputFilePath) throws IOException, ParseException, SQLException {
        parseJSON((JSONArray) new JSONReader().readFile(inputFilePath).get("criterias"));
        SearchResponse response = new SearchResponse();

        for (Criteria criteria : criterias) {
            if (criteria.containsCriteria("lastName")) {
                response.addResult(criteria, getBuyersByLastName(criteria.getValue("lastName")));
            } else if (criteria.containsCriteria("productName")) {
                String productName = criteria.getValue("productName");
                int minTimes = Integer.parseInt(criteria.getValue("minTimes"));
                response.addResult(criteria, getBuyersByProductNameAndPurchaseTimes(productName, minTimes));
            } else if (criteria.containsCriteria("minExpenses")) {
                int minExpenses = Integer.parseInt(criteria.getValue("minExpenses"));
                int maxExpenses = Integer.parseInt(criteria.getValue("maxExpenses"));
                response.addResult(criteria, getBuyersByTotalPurchaseCostBetween(minExpenses, maxExpenses));
            } else if (criteria.containsCriteria("badCustomers")) {
                int badCustomers = Integer.parseInt(criteria.getValue("badCustomers"));
                response.addResult(criteria, getBadBuyers(badCustomers));
            } else {
                throw new UnknownCriteriaException("Неизвестный критерий поиска: ");
            }
        }

        new JSONWriter().writeToFile(outputFilePath, response);
    }

    private List<Buyer> getBadBuyers(int limit) throws SQLException, IOException {
        return searchDAO.getBadCustomers(limit);
    }

    private List<Buyer> getBuyersByLastName(String lastName) throws SQLException, IOException {
        return searchDAO.getBuyersByLastName(lastName);
    }

    private List<Buyer> getBuyersByProductNameAndPurchaseTimes(String productName, int minTimes) throws SQLException, IOException {
        return searchDAO.getBuyersByProductNameAndPurchaseTimes(productName, minTimes);
    }

    private List<Buyer> getBuyersByTotalPurchaseCostBetween(int minExpenses, int maxExpenses) throws SQLException, IOException {
        return searchDAO.getBuyersByTotalPurchaseCostBetween(minExpenses, maxExpenses);
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
