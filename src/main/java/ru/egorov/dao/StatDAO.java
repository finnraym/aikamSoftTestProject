package ru.egorov.dao;

import ru.egorov.config.DBConfig;
import ru.egorov.dto.CustomerStatDTO;
import ru.egorov.dto.Purchase;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatDAO {

    public List<CustomerStatDTO> getStatBetweenDate(LocalDate startDate, LocalDate endDate) throws SQLException {
        List<CustomerStatDTO> buyers = new ArrayList<>();

        String query = "SELECT b.id, b.first_name, b.second_name, pr.name, SUM(pr.price) as expenses " +
                "FROM buyer b " +
                "INNER JOIN purchase p ON b.id = p.buyer_id " +
                "INNER JOIN product pr ON p.product_id = pr.id " +
                "WHERE p.purchase_date BETWEEN ? AND ? " +
                "GROUP BY b.id, pr.id;";

        try(Connection connection = DBConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(startDate));
            preparedStatement.setDate(2, Date.valueOf(endDate));
            executeQuery(preparedStatement, buyers);
            preparedStatement.close();
        }
        return buyers;
    }

    private void executeQuery(PreparedStatement ps, List<CustomerStatDTO> buyers) throws SQLException {
        ResultSet rs = ps.executeQuery();
        Map<Long, CustomerStatDTO> buyerMap = new HashMap<>();
        while (rs.next()) {
            long buyerId = rs.getLong("id");
            CustomerStatDTO customer = buyerMap.get(buyerId);

            if (customer == null) {
                customer = new CustomerStatDTO();
                customer.setName(rs.getString("first_name") + " " + rs.getString("second_name"));
                buyerMap.put(buyerId, customer);
            }

            Purchase purchase = new Purchase();
            purchase.setName(rs.getString("name"));
            purchase.setExpenses(rs.getDouble("expenses"));
            customer.addExpenses(purchase.getExpenses());
            customer.addPurchase(purchase);

        }
        buyers.addAll(buyerMap.values());
        rs.close();
    }
}
