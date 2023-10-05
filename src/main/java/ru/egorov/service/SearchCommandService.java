package ru.egorov.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ru.egorov.dao.SearchDAO;
import ru.egorov.dto.*;
import ru.egorov.dto.ErrorResponse;
import ru.egorov.dto.Criteria;
import ru.egorov.dto.SearchResponse;
import ru.egorov.exception.BadCriteriaException;
import ru.egorov.exception.UnknownCriteriaException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchCommandService implements CommandService {
    private final SearchDAO searchDAO;

    public SearchCommandService() {
        this.searchDAO = new SearchDAO();
    }

    @Override
    public ru.egorov.dto.Response execute(JsonObject json) {
        Response response;
        try {
            JsonElement jsonElement = json.get("criterias");
            if (jsonElement == null || !jsonElement.isJsonArray()) {
                throw new BadCriteriaException("Неизвестный критерий поиска: " + jsonElement);
            }
            List<Criteria> criterias = getCriterias(jsonElement.getAsJsonArray());

            SearchResponse searchResponse = new SearchResponse();

            for (Criteria criteria : criterias) {
                CriteriaResultResponse resultResponse = new CriteriaResultResponse();
                resultResponse.setCriteria(criteria);
                if (criteria instanceof LastNameCriteria) {
                    resultResponse.setResults(getBuyersByLastName(((LastNameCriteria) criteria).getLastName()));
                } else if (criteria instanceof ProductNameAndCountPurchasesCriteria) {
                    resultResponse.setResults(getBuyersByProductNameAndPurchaseTimes(
                            ((ProductNameAndCountPurchasesCriteria) criteria).getProductName(),
                            ((ProductNameAndCountPurchasesCriteria) criteria).getMinTimes()
                    ));
                } else if (criteria instanceof MinAndMaxExpensesCriteria) {
                    resultResponse.setResults(getBuyersByTotalPurchaseCostBetween(
                            ((MinAndMaxExpensesCriteria) criteria).getMinExpenses(),
                            ((MinAndMaxExpensesCriteria) criteria).getMaxExpenses()
                    ));
                } else if (criteria instanceof BadCustomersCriteria) {
                    resultResponse.setResults(getBadBuyers(((BadCustomersCriteria) criteria).getBadCustomers()));
                }
                searchResponse.addResult(resultResponse);
            }

            response = searchResponse;
        } catch (BadCriteriaException e) {
            response = new ErrorResponse();
            ((ErrorResponse) response).setMessage("Ошибка в критерии поиска: " + e.getMessage());
        } catch (SQLException e) {
            response = new ErrorResponse();
            ((ErrorResponse) response).setMessage("Ошибка при работе с базой данных. Подробнее: " + e.getMessage());
        } catch (RuntimeException e) {
            response = new ErrorResponse();
            ((ErrorResponse) response).setMessage(e.getMessage());
        }

        return response;
    }

    private List<CustomerSearchDTO> getBadBuyers(int limit) throws SQLException {
        if (limit < 0) {
            throw new BadCriteriaException("Количество пассивных покупателей не может быть ниже 0.");
        }
        return searchDAO.getBadCustomers(limit);
    }

    private List<CustomerSearchDTO> getBuyersByLastName(String lastName) throws SQLException {
        return searchDAO.getBuyersByLastName(lastName);
    }

    private List<CustomerSearchDTO> getBuyersByProductNameAndPurchaseTimes(String productName, int minTimes) throws SQLException {
        if (minTimes < 0) {
            throw new BadCriteriaException("Количество покупок не может быть ниже 0.");
        }
        return searchDAO.getBuyersByProductNameAndPurchaseTimes(productName, minTimes);
    }

    private List<CustomerSearchDTO> getBuyersByTotalPurchaseCostBetween(double minExpenses, double maxExpenses) throws SQLException {
        if (minExpenses < 0) {
            throw new BadCriteriaException("Минимальная стоимость всех покупок не может быть ниже 0.");
        } else if (minExpenses > maxExpenses) {
            throw new BadCriteriaException("Максимальная стоимость всех покупок не может быть ниже минимальной.");
        }
        return searchDAO.getBuyersByTotalPurchaseCostBetween(minExpenses, maxExpenses);
    }

    private List<ru.egorov.dto.Criteria> getCriterias(JsonArray array) {
        List<ru.egorov.dto.Criteria> criterias = new ArrayList<>();
        String[] criteriasNames = {"lastName", "productName", "minExpenses", "badCustomers"};
        for (JsonElement jsonElement : array) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            int i = 0;
            JsonElement elem = jsonObject.get(criteriasNames[i]);
            while (elem == null && i < criteriasNames.length) {
                i++;
                elem = jsonObject.get(criteriasNames[i]);
            }

            if (elem == null) {
                throw new UnknownCriteriaException("Неизвестный критерий поиска: " + jsonElement);
            }

            String criteriaName = criteriasNames[i];

            if (criteriaName.equals("lastName")) {
                LastNameCriteria criteria = new LastNameCriteria(elem.getAsString());
                criterias.add(criteria);
            } else if (criteriaName.equals("productName")) {
                JsonElement minTimes = jsonObject.get("minTimes");
                if (minTimes == null) throw new BadCriteriaException("Не хватает критерия количества покупок для конкретного продукта " + elem);
                ProductNameAndCountPurchasesCriteria criteria = new ProductNameAndCountPurchasesCriteria();
                criteria.setProductName(elem.getAsString());
                criteria.setMinTimes(minTimes.getAsInt());
                criterias.add(criteria);
            } else if (criteriaName.equals("minExpenses")) {
                JsonElement maxExpenses = jsonObject.get("maxExpenses");
                if (maxExpenses == null) throw new BadCriteriaException("Не хватает критерия максимальной стоимости всех покупок");
                MinAndMaxExpensesCriteria criteria = new MinAndMaxExpensesCriteria();
                criteria.setMinExpenses(elem.getAsDouble());
                criteria.setMaxExpenses(maxExpenses.getAsDouble());
                criterias.add(criteria);
            } else if (criteriaName.equals("badCustomers")) {
                BadCustomersCriteria criteria = new BadCustomersCriteria(elem.getAsInt());
                criterias.add(criteria);
            }
        }

        return criterias;
    }
}
