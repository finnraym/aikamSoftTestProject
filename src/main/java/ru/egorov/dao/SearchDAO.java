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
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Buyer buyer = new Buyer();
                buyer.setFirstName(rs.getString("first_name"));
                buyer.setLastName(rs.getString("second_name"));
                buyers.add(buyer);
            }
            rs.close();
            preparedStatement.close();
        }

        return buyers;
    }
}
