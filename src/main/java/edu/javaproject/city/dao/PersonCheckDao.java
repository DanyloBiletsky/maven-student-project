package edu.javaproject.city.dao;

import edu.javaproject.city.domain.PersonRequest;
import edu.javaproject.city.domain.PersonResponse;
import edu.javaproject.city.exception.PersonCheckException;

import java.sql.*;

import static edu.javaproject.studentorder.dao.ConnectionBuilder.getConnection;

public class PersonCheckDao {

    PersonCheckDao(){}
    private static final String SQL_REQUEST = "select temporal from city_register_address_person ap " +
            "INNER JOIN city_register_person p ON p.person_id = ap.person_id " +
            "INNER JOIN city_register_addresses a on a.address_id = ap.address_id " +
            "WHERE " +
            "" +
            "upper(p.surname) = upper(?) AND upper(p.name) = upper(?) " +
                    "AND upper(p.patronymic) = upper(?) " + " AND p.date_of_birth = ? " +
            "AND a.street_code = ? AND a.building = ? AND a.apartment = ? AND a.extension = ?";

    public PersonResponse checkPerson(PersonRequest request) throws PersonCheckException {
        PersonResponse response = new PersonResponse();

        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_REQUEST)){

            statement.setString(1, request.getSurname());
            statement.setString(2, request.getName());
            statement.setString(3, request.getPatronymic());
            statement.setDate(4, java.sql.Date.valueOf(request.getDateOfBirth()));
            statement.setString(5, request.getStreetCode());
            statement.setString(6, request.getBuilding());
            statement.setString(7, request.getExtension());
            statement.setString(8, request.getApartment());

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

    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/CITY_REGISTER", "postgres", "postgres28");

    }
}
