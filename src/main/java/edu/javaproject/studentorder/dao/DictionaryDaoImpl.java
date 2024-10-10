package edu.javaproject.studentorder.dao;

import edu.javaproject.studentorder.config.Config;
import edu.javaproject.studentorder.domain.CountryArea;
import edu.javaproject.studentorder.domain.PassportOffice;
import edu.javaproject.studentorder.domain.RegisterOffice;
import edu.javaproject.studentorder.domain.Street;
import edu.javaproject.studentorder.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import static edu.javaproject.studentorder.config.Config.*;

public class DictionaryDaoImpl implements DictionaryDao {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryDaoImpl.class);
    private static final String GET_STREET = "SELECT street_code, street_name " +
            "FROM jc_streets WHERE UPPER(street_name) LIKE UPPER(?)";
    private static final String GET_PASSPORT = "SELECT * " +
            "FROM jc_passport_office WHERE p_office_area_id = ?";
    private static final String GET_REGISTER = "SELECT * " +
            "FROM jc_register_office WHERE r_office_area_id = ?";
    private static final String GET_AREA = "SELECT * " +
            "FROM jc_ukraine_structure WHERE area_id like ? and area_id <> ?";


    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }

    public List<Street> findStreets(String pattern) throws DaoException {
        List<Street> result = new LinkedList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_STREET))
        {
            statement.setString(1, "%" + pattern + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Street myStreet = new Street(
                        resultSet.getString("street_code"),
                        resultSet.getString("street_name"));
                result.add(myStreet);
            }

        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
            throw new DaoException(exception);
        }
        return result;
    }


    @Override
    public List<PassportOffice> findPassportOffices(String areaId) throws DaoException {
        List<PassportOffice> result = new LinkedList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PASSPORT))
        {
            statement.setString(1, areaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                PassportOffice myPassportOffice = new PassportOffice(
                        resultSet.getString("p_office_id"),
                        resultSet.getString("p_office_area_id"),
                        resultSet.getString("p_office_name"));
                result.add(myPassportOffice);
            }
        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public List<RegisterOffice> findRegisterOffices(String areaId) throws DaoException {
        List<RegisterOffice> result = new LinkedList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_REGISTER))
        {
            statement.setString(1, areaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                RegisterOffice myRegisterOffice = new RegisterOffice(
                        resultSet.getString("r_office_id"),
                        resultSet.getString("r_office_area_id"),
                        resultSet.getString("r_office_name"));
                result.add(myRegisterOffice);
            }
        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public List<CountryArea> findAreas(String areaId) throws DaoException {
        List<CountryArea> result = new LinkedList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_AREA))
        {
            String param1 = buildParam(areaId);
            String param2 = areaId;

            statement.setString(1, param1);
            statement.setString(2, param2);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CountryArea myCountryArea = new CountryArea(
                        resultSet.getString("area_id"),
                        resultSet.getString("area_name"));
                result.add(myCountryArea);
            }
        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
            throw new DaoException(exception);
        }
        return result;
    }

    private String buildParam(String areaId) throws SQLException {

        // Коренева вибірка всього, що є (м. Київ, АР Крим, області)

        if (areaId == null || areaId.trim().isEmpty()){
            return "UA__0000000000_____";

        // Вибірка суто по районам областей і АР Крим без м.Києва

        } else if (areaId.startsWith("0000000000", 4) && !areaId.startsWith("80", 2)) {
            return "UA" + areaId.substring(2, 4) + "__00000000_____";

        // Вибірка по районам м. Києва

        } else if (areaId.startsWith("80", 2)) {
            return "UA" + areaId.substring(2, 4) + "00000000_______";

        // Вибірка по ТРГ районів

        } else if (areaId.startsWith("00000000", 6)) {
            return "UA" + areaId.substring(2, 6) + "___00000_____";

        // Вибірка по містам, селам, селищам

        } else if (areaId.startsWith("00000", 9)) {
            return "UA" + areaId.substring(2, 9) + "___00_____";
        }

        throw new SQLException("Invalid parameter : " + areaId);
    }
}
