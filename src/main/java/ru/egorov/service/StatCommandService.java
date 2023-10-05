package ru.egorov.service;

import com.google.gson.JsonObject;
import ru.egorov.dao.StatDAO;
import ru.egorov.dto.CustomerStatDTO;
import ru.egorov.exception.BadCriteriaException;
import ru.egorov.dto.*;
import ru.egorov.dto.ErrorResponse;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class StatCommandService implements CommandService {

    private final StatDAO statDAO;

    public StatCommandService() {
        statDAO = new StatDAO();
    }

    @Override
    public ru.egorov.dto.Response execute(JsonObject json) {
        Response response;
        try {
            StatResponse statResponse = new StatResponse();
            LocalDate startDate = LocalDate.parse(json.get("startDate").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate endDate = LocalDate.parse(json.get("endDate").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            List<CustomerStatDTO> stat = getStatBetweenDate(startDate, endDate);

            statResponse.setTotalDays((int) startDate.until(endDate, ChronoUnit.DAYS));
            for (CustomerStatDTO customer : stat) {
                statResponse.addCustomer(customer);
                statResponse.addExpenses(customer.getTotalExpenses());
            }
            statResponse.setAvgExpenses(statResponse.getTotalExpenses() / stat.size());
            response = statResponse;
        } catch (SQLException e) {
            response = new ErrorResponse();
            ((ErrorResponse) response).setMessage("Ошибка при работе с базой данных. Подробнее: " + e.getMessage());
        } catch (BadCriteriaException e) {
            response = new ErrorResponse();
            ((ErrorResponse) response).setMessage("Ошибка в критерии: " + e.getMessage());
        } catch (DateTimeParseException e) {
            response = new ErrorResponse();
            ((ErrorResponse) response).setMessage("Неверный формат даты.");
        } catch (RuntimeException e) {
            response = new ErrorResponse();
            ((ErrorResponse) response).setMessage(e.getMessage());
        }
        return response;
    }

    private List<CustomerStatDTO> getStatBetweenDate(LocalDate startDate, LocalDate endDate) throws SQLException, BadCriteriaException {
        if (startDate.isAfter(endDate)) {
            throw new BadCriteriaException("Начальная дата не может быть раньше конечной.");
        }

        return statDAO.getStatBetweenDate(startDate, endDate);
    }
}
