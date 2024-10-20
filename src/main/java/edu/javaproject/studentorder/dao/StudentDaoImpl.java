package edu.javaproject.studentorder.dao;

import edu.javaproject.studentorder.config.Config;
import edu.javaproject.studentorder.domain.*;
import edu.javaproject.studentorder.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static edu.javaproject.studentorder.config.Config.*;

public class StudentDaoImpl implements StudentOrderDao{

    private static final Logger logger = LoggerFactory.getLogger(StudentDaoImpl.class);

    private final static String INSERT_CHILD = "INSERT INTO jc_student_child(" +
            "student_order_id, \"с_surname\", \"с_name\", \"с_patronymic\", \"с_date_of_birth\", c_birth_certificate_number," +
            "c_register_office_id, c_date_of_receiving_birth_certificate, " +
            "\"с_post_code\", c_street_code, \"с_city\", \"с_building\", \"с_extension\", \"с_apartment\")" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_CHILD = "SELECT soc.*, ro.r_office_area_id, ro.r_office_name " +
            "FROM jc_student_child soc " +
            "INNER JOIN jc_register_office ro ON ro.r_office_id = soc.c_register_office_id " +
            "WHERE student_order_id IN ";

    private static final String SELECT_ORDERS = "SELECT so.*, ro.r_office_area_id, ro.r_office_name, " +
            "po_h.p_office_area_id as h_p_office_area_id, po_h_.p_office_name as h_p_office_name, " +
            "po_w.p_office_area_id as w_p_office_area_id, po_w_.p_office_name as w_p_office_name " +
            "FROM jc_student_order so " +
            "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id " +
            "INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id " +
            "INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id " +
            "WHERE student_order_status = ? ORDER BY student_order_date LIMIT ? ;";

    private final static String INSERT_ORDER = "INSERT INTO jc_student_order(" +
            "student_order_status, student_order_date," +
            "h_surname, h_name, h_patronymic, h_date_of_birth, h_passport_number, " +
            "h_passport_date, h_passport_department, h_post_code, h_street_code, h_city, h_building, h_extension, h_apartment, " +
            "h_university_id, h_university_name, h_student_number, " +
            "w_surname, w_name, w_patronymic, w_date_of_birth, w_passport_number," +
            "w_passport_date, w_passport_department, w_post_code, w_street_code, w_city, w_building, w_extension, w_apartment, " +
            "w_university_id, w_university_name, w_student_number, " +
            "marriage_certificate_id, register_office_id, marriage_date)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_ORDERS_FULL = "SELECT so.*, ro.r_office_area_id, ro.r_office_name, " +
            "po_h.p_office_area_id as h_p_office_area_id, po_h_.p_office_name as h_p_office_name, " +
            "po_w.p_office_area_id as w_p_office_area_id, po_w_.p_office_name as w_p_office_name, " +
            "soc.*, ro.r_office_area_id, ro.r_office_name " +
            "FROM jc_student_order so " +
            "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id " +
            "INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id " +
            "INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id " +
            "INNER JOIN jc_register_office ro_child ON ro_child.r_office_id = soc.c_register_office_id " +
            "INNER JOIN jc_student_child soc ON soc.student_order_id = so.student_order_id " +
            "WHERE student_order_status = 0 ORDER BY student_order_id LIMIT ? OFFSET ?";

    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }

    @Override
    public List<StudentOrder> getStudentOrders() throws DaoException {
        //return getStudentOrdersTwoSelect();
        return getStudentOrdersOneSelect();
    }

    private List<StudentOrder> getStudentOrdersOneSelect() throws DaoException {
        List<StudentOrder> result = new LinkedList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDERS_FULL)) {

            Map<String, StudentOrder> maps = new HashMap<>();
            int limit = Integer.parseInt(Config.getProperty(DB_LIMIT));
            statement.setInt(1, limit);

            statement.setInt(2,StudentOrderStatus.START.ordinal());

            ResultSet rs = statement.executeQuery();
            int counter = 0;

            while(rs.next()){
                String soId = rs.getString("student_order_id");
                if (!(maps.containsKey(soId))) {

                    StudentOrder so = getFullStudentOrder(rs);

                    result.add(so);
                    maps.put(soId, so);
                }
                StudentOrder so = maps.get(soId);
               so.addChild(fillChild(rs));
               counter++;
            }
            if (counter >= limit){
                result.remove(result.size() - 1);
            }
            findChildren(connection, result);

            rs.close();
        } catch(SQLException ex){
            logger.error(ex.getMessage(), ex);
            throw new DaoException(ex);
        }
        return result;
    }

    private List<StudentOrder> getStudentOrdersTwoSelect() throws DaoException {
        List<StudentOrder> result = new LinkedList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDERS)) {

            statement.setInt(1, StudentOrderStatus.START.ordinal());
            statement.setInt(2, Integer.parseInt(DB_LIMIT));
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                StudentOrder so = new StudentOrder();
                fillStudentOrder(rs, so);
                fillMarriage(rs, so);

                Adult husband = fillAdult(rs, "h_");
                Adult wife = fillAdult(rs, "w_");
                so.setWife(wife);
                so.setHusband(husband);

                result.add(so);
            }

            findChildren(connection, result);

            rs.close();
        } catch(SQLException ex){
            logger.error(ex.getMessage(), ex);
            throw new DaoException(ex);
        }
        return result;
    }

    private StudentOrder getFullStudentOrder(ResultSet rs) throws SQLException {
        StudentOrder so = new StudentOrder();

        fillStudentOrder(rs, so);
        fillMarriage(rs, so);

        so.setWife(fillAdult(rs, "w_"));
        so.setHusband(fillAdult(rs, "h_"));
        return so;
    }

    private void findChildren(Connection connection, List<StudentOrder> result) {
        String cl = "(" + result.stream().map(so -> String.valueOf(so.getStudentOrderId()))
                .collect(Collectors.joining(",")) + ")";

        Map<String, StudentOrder> maps = result.stream().collect(Collectors
                .toMap(so -> so.getStudentOrderId(), so -> so));
        try (PreparedStatement statement = connection.prepareStatement(SELECT_CHILD + cl)){
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                System.out.println(rs.getLong(1) + ":" + rs.getString(3));
                Child child = fillChild(rs);
                StudentOrder so = maps.get(rs.getString("student_order_id"));
                so.addChild(child);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Child fillChild(ResultSet rs) throws SQLException {
        String surname = rs.getString("c_surname");
        String name = rs.getString("c_name");
        String patronymic = rs.getString("c_patronymic");
        LocalDate dateOfBirth = rs.getDate("c_date_of_birth").toLocalDate();

        Child child = new Child(surname, name, patronymic, dateOfBirth);

        child.setBirthCertificateNumber(rs.getString("c_birth_certificate_number"));
        child.setDateOfReceivingBirthCertificate(rs.getDate("c_date_of_receiving_birth_certificate").toLocalDate());

        String roId = rs.getString("c_register_office_id");
        String roArea = rs.getString("r_office_area_id");
        String roName = rs.getString("r_office_name");

        RegisterOffice ro = new RegisterOffice(roId,roName,roArea);
        child.setDepartmentOfVitalRecords(ro);

        Address address = new Address();
        Street street = new Street(rs.getString("c_street_code"), rs.getString("c_street_name"));
        address.setPostCode(rs.getString("c_post_code"));
        address.setBuilding(rs.getString("c_building"));
        address.setApartment(rs.getString("c_apartment"));
        address.setExtension(rs.getString("c_extension"));

        address.setStreet(street);
        child.setAddress(address);

        return child;
    }

    @Override
    public Long saveStudentOrder(StudentOrder so) throws DaoException {

        Long result = -1L;

        logger.debug("STUDENT ORDER: {} ", so);

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, new String[] {"student_order_id"}))
        {

            connection.setAutoCommit(false);
            try {
                // Start
                statement.setInt(1, StudentOrderStatus.START.ordinal());
                statement.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));

                // Husband
                setParamsForAdult(statement, 3, so.getHusband());
                setParamsForAddress(statement, 10, so.getHusband());
                setUniversityParams(statement, 16, so.getHusband());

                // Wife
                setParamsForAdult(statement, 19, so.getWife());
                setParamsForAddress(statement, 26, so.getWife());
                setUniversityParams(statement, 32, so.getWife());

                // Marriage
                statement.setString(35, so.getMarriageCertificateId());
                statement.setString(36, so.getMarriageOffice().getOfficeId());
                statement.setDate(37, java.sql.Date.valueOf(so.getMarriageDate()));

                statement.executeUpdate();

                ResultSet rsGk = statement.getGeneratedKeys();
                if (rsGk.next()) {
                    result = rsGk.getLong(1);
                }
                rsGk.close();

                //Children Table Save Data
                saveChildren(connection, so, result);
                connection.commit();
            } catch (SQLException exception) {
                connection.rollback();
                logger.error(exception.getMessage(), exception);
                throw exception;
            }
        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
            throw new DaoException(exception);
        }
        return result;
    }

    private Adult fillAdult(ResultSet rs, String pref) throws SQLException{
        Adult adult = new Adult();
        adult.setSurname(rs.getString(pref+"surname"));
        adult.setName(rs.getString(pref+"name"));
        adult.setPatronymic(rs.getString(pref+"patronymic"));
        adult.setDateOfBirth(rs.getDate(pref+"date_of_birth").toLocalDate());
        adult.setPassportNumber(rs.getString(pref+"passport_number"));
        adult.setPassportIssueDate(rs.getDate(pref+"passport_date").toLocalDate());


        String poId = rs.getString(pref + "passport_office_id");
        String poArea = rs.getString(pref+"p_office_area_id");
        String poName = rs.getString(pref+"p_office_name");
        PassportOffice po = new PassportOffice(poId, poArea, poName);
        adult.setPassportDepartment(po);

        Address address = new Address();
        address.setPostCode(rs.getString(pref+"post_code"));
        address.setBuilding(rs.getString(pref+"building"));
        address.setApartment(rs.getString(pref+"apartment"));
        address.setExtension(rs.getString(pref+"extension"));

        Street street = new Street(rs.getString(pref+"street_code"),"");
        address.setStreet(street);

        adult.setAddress(address);

        University university = new University(rs.getString(pref+""),"");
        adult.setUniversity(university);
        adult.setStudentId(rs.getString(pref+"student_number"));

        return null;
    }

    private void fillStudentOrder(ResultSet rs, StudentOrder so) throws SQLException {
        so.setStudentOrderId(rs.getString("student_order_id"));
        so.setStudentOrderDate(rs.getTimestamp("student_order_date").toLocalDateTime());
        so.setStudentOrderStatus(StudentOrderStatus.fromValue(rs.getInt("student_order_status")));


    }

    private void fillMarriage(ResultSet rs, StudentOrder so) throws SQLException{
        so.setMarriageCertificateId(rs.getString("marriage_certificate_id"));
        so.setMarriageDate(rs.getDate("marriage_date").toLocalDate());

        String roId = rs.getString("register_office_id");
        String areaId = rs.getString("r_office_area_id");
        String areaName = rs.getString("r_office_name");
        RegisterOffice ro = new RegisterOffice(roId, areaName, areaId);
        so.setMarriageOffice(ro);
    }

    private static void saveChildren(Connection connection, StudentOrder so, Long soId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_CHILD)) {
            for (Child child : so.getChildren()) {
                stmt.setLong(1, soId);
                setParamsForChild(stmt, child);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private static void setParamsForPerson(PreparedStatement statement, int index, Person person) throws SQLException{
        statement.setString(index, person.getSurname());
        statement.setString(index+1, person.getName());
        statement.setString(index+2, person.getPatronymic());
        statement.setDate(index+3, Date.valueOf(person.getDateOfBirth()));
    }

    public static void setUniversityParams(PreparedStatement statement, int index, Adult adult) throws SQLException{
        statement.setString(index, adult.getUniversity().getUniversityId());
        statement.setString(index+1, adult.getUniversity().getUniversityName());
        statement.setString(index+2, adult.getStudentId());
    }

    private static void setParamsForAdult(PreparedStatement statement, int index, Adult adult) throws SQLException {
        setParamsForPerson(statement, index, adult);

        statement.setString(index+4, adult.getPassportNumber());
        statement.setDate(index+5, Date.valueOf(adult.getPassportIssueDate()));
        statement.setString(index+6, adult.getPassportDepartment().getOfficeName());
    }

    private static void setParamsForAddress(PreparedStatement statement, int index, Person person) throws SQLException{
        statement.setString(index, person.getAddress().getPostCode());
        statement.setString(index+1, person.getAddress().getStreet().getStreetCode());
        statement.setString(index+2, person.getAddress().getCity());
        statement.setString(index+3, person.getAddress().getBuilding());
        statement.setString(index+4, person.getAddress().getExtension());
        statement.setString(index+5, person.getAddress().getApartment());

    }

    private static void setParamsForChild(PreparedStatement statement, Child child) throws SQLException{
        setParamsForPerson(statement, 2, child);
        statement.setString(6, child.getBirthCertificateNumber());
        statement.setString(7, child.getDepartmentOfVitalRecords().getOfficeId());
        statement.setDate(8,Date.valueOf(child.getDateOfReceivingBirthCertificate()));
        setParamsForAddress(statement, 9 ,child);
    }

}
