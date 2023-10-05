package ru.egorov.dao;

import ru.egorov.config.DBConfig;
import ru.egorov.dto.CustomerSearchDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class SearchDAO {

    public List<CustomerSearchDTO> getBuyersByLastName(String lastName) throws SQLException {
        List<CustomerSearchDTO> buyers = new ArrayList<>();
        final String FIND_BY_LASTNAME_QUERY = "SELECT first_name, second_name FROM buyer WHERE second_name = ?";
        try (Connection connection = DBConfig.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LASTNAME_QUERY);
            preparedStatement.setString(1, lastName);
            executeQuery(preparedStatement, buyers);
            preparedStatement.close();
        }

        return buyers;
    }

    public List<CustomerSearchDTO> getBuyersByProductNameAndPurchaseTimes(String productName, int minTimes) throws SQLException {
        List<CustomerSearchDTO> buyers = new ArrayList<>();

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

    public List<CustomerSearchDTO> getBuyersByTotalPurchaseCostBetween(double minExpenses, double maxExpenses) throws SQLException {
        List<CustomerSearchDTO> buyers = new ArrayList<>();

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
    public List<CustomerSearchDTO> getBadCustomers(int limit) throws SQLException {
        List<CustomerSearchDTO> buyers = new ArrayList<>();

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

    private void executeQuery(PreparedStatement ps, List<CustomerSearchDTO> buyers) throws SQLException {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            CustomerSearchDTO customer = new CustomerSearchDTO();
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("second_name"));
            buyers.add(customer);
        }
        rs.close();
    }
}
