package ru.egorov.dao;

import ru.egorov.config.DBConfig;
import ru.egorov.model.Buyer;
import ru.egorov.model.Purchase;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatDAO {

    public List<Buyer> getStatBetweenDate(LocalDate startDate, LocalDate endDate) throws SQLException, IOException {
        List<Buyer> buyers = new ArrayList<>();

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

    private void executeQuery(PreparedStatement ps, List<Buyer> buyers) throws SQLException {
        ResultSet rs = ps.executeQuery();
        Map<Long, Buyer> buyerMap = new HashMap<>();
        while (rs.next()) {
            long buyerId = rs.getLong("id");
            Buyer buyer = buyerMap.get(buyerId);

            if (buyer == null) {
                buyer = new Buyer();
                buyer.setId(buyerId);
                buyer.setFirstName(rs.getString("first_name"));
                buyer.setLastName(rs.getString("second_name"));
                buyerMap.put(buyerId, buyer);
            }

            Purchase purchase = new Purchase();
            purchase.setProductName(rs.getString("name"));
            purchase.setExpenses(rs.getDouble("expenses"));
            buyer.addExpenses(purchase.getExpenses());
            buyer.addPurchase(purchase);
        }
        buyers.addAll(buyerMap.values());
        rs.close();
    }
}
