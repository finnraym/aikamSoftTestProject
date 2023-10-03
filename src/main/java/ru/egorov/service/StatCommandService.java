package ru.egorov.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import ru.egorov.dao.StatDAO;
import ru.egorov.model.Buyer;
import ru.egorov.util.JSONReader;
import ru.egorov.util.JSONWriter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class StatCommandService implements CommandService {

    private final StatDAO statDAO;

    public StatCommandService() {
        statDAO = new StatDAO();
    }

    @Override
    public void execute(String inputFilePath, String outputFilePath) throws IOException, ParseException, SQLException {
        JSONObject json = new JSONReader().readFile(inputFilePath);
        LocalDate startDate = LocalDate.parse(json.get("startDate").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(json.get("endDate").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<Buyer> stat = statDAO.getStatBetweenDate(startDate, endDate);

        StatResponse response = new StatResponse();

        response.setTotalDays((int) startDate.until(endDate, ChronoUnit.DAYS));
        for (Buyer buyer : stat) {
            response.addTotalExpenses(buyer.getTotalExpenses());
        }
        response.setAvgExpenses(response.getTotalExpenses() / stat.size());
        response.setBuyers(stat);
        new JSONWriter().writeToFile(outputFilePath, response);
    }
}
