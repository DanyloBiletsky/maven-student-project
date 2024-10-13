package edu.javaproject.city.dao;

import edu.javaproject.city.domain.PersonRequest;
import edu.javaproject.city.domain.PersonResponse;
import edu.javaproject.city.exception.PersonCheckException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static edu.javaproject.studentorder.dao.ConnectionBuilder.getConnection;

public class PersonCheckDao {
    private static final String SQL_REQUEST = "";

    public PersonResponse checkPerson(PersonRequest request) throws PersonCheckException {
        PersonResponse response = new PersonResponse();

        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_REQUEST)){

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                response.setRegistered(true);
                response.setTemporal(resultSet.getBoolean("temporal"));
            }
        }
        catch (SQLException exception) {
            throw new PersonCheckException(exception);
        }

        return response;
    }

    private Connection getConnection(){
        return null;
    }
}
