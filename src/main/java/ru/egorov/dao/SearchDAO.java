package ru.egorov.dao;

import ru.egorov.config.DBConfig;
import ru.egorov.model.Buyer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class SearchDAO {

    public List<Buyer> getBuyersByLastName(String lastName) throws SQLException, IOException{
        List<Buyer> buyers = new ArrayList<>();
        final String FIND_BY_LASTNAME_QUERY = "SELECT first_name, second_name FROM buyer WHERE second_name = ?";
        try (Connection connection = DBConfig.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LASTNAME_QUERY);
            preparedStatement.setString(1, lastName);
            executeQuery(preparedStatement, buyers);
            preparedStatement.close();
        }

        return buyers;
    }

    public List<Buyer> getBuyersByProductNameAndPurchaseTimes(String productName, int minTimes) throws SQLException, IOException {
        List<Buyer> buyers = new ArrayList<>();

        String query = "SELECT b.first_name, b.second_name " +
                "FROM buyer b " +
                "INNER JOIN purchase p ON b.id = p.buyer_id " +
                "INNER JOIN product pr ON p.product_id = pr.id " +
                "WHERE pr.name = ? " +
                "GROUP BY b.id " +
                "HAVING count(b.id) >= ?";

        try(Connection connection = DBConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, productName);
            preparedStatement.setInt(2, minTimes);
            executeQuery(preparedStatement, buyers);
            preparedStatement.close();
        }
        return buyers;
    }

    public List<Buyer> getBuyersByTotalPurchaseCostBetween(int minExpenses, int maxExpenses) throws SQLException, IOException {
        List<Buyer> buyers = new ArrayList<>();

        String query = "SELECT b.first_name, b.second_name " +
                "FROM buyer b " +
                "INNER JOIN purchase p ON b.id = p.buyer_id " +
                "INNER JOIN product pr ON p.product_id = pr.id " +
                "GROUP BY b.id " +
                "HAVING SUM(pr.price) BETWEEN ? AND ?;";

        try(Connection connection = DBConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, minExpenses);
            preparedStatement.setDouble(2, maxExpenses);
            executeQuery(preparedStatement, buyers);
            preparedStatement.close();
        }
        return buyers;
    }
    public List<Buyer> getBadCustomers(int limit) throws SQLException, IOException {
        List<Buyer> buyers = new ArrayList<>();

        String query = "SELECT b.first_name, b.second_name " +
                "FROM buyer b " +
                "INNER JOIN purchase p ON b.id = p.buyer_id " +
                "INNER JOIN product pr ON p.product_id = pr.id " +
                "GROUP BY b.id " +
                "ORDER BY COUNT(p.id) LIMIT ?";

        try(Connection connection = DBConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, limit);
            executeQuery(preparedStatement, buyers);
            preparedStatement.close();
        }
        return buyers;
    }

    private void executeQuery(PreparedStatement ps, List<Buyer> buyers) throws SQLException {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Buyer buyer = new Buyer();
            buyer.setFirstName(rs.getString("first_name"));
            buyer.setLastName(rs.getString("second_name"));
            buyers.add(buyer);
        }
        rs.close();
    }
}
