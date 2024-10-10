package edu.javaproject.studentorder.dao;

import edu.javaproject.studentorder.domain.*;
import edu.javaproject.studentorder.exception.DaoException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class StudentDaoImplTest {

    @BeforeClass
    public static void startUp() throws URISyntaxException, IOException {
        DBInit.startUp();
    }

    @Test
    public void saveStudentOrderTest() throws DaoException {
        StudentOrder order = buildStudentOrder(5);
        Long id = new StudentDaoImpl().saveStudentOrder(order);
    }

    @Test(expected = DaoException.class)
    public void saveStudentOrderErrorTest() throws DaoException {
        StudentOrder order = buildStudentOrder(5);
        order.getHusband().setSurname(null);
        Long id = new StudentDaoImpl().saveStudentOrder(order);
    }

    @Test
    public void getStudentOrderTest() throws DaoException {
        List<StudentOrder> list = new StudentDaoImpl().getStudentOrders();

    }

    public StudentOrder buildStudentOrder(int orderID){
        StudentOrder order = new StudentOrder();
        //order.setStudentOrderId(Integer.toString(orderID));

        //Дані про шлюб:
        order.setMarriageCertificateId("certificate1");
        order.setMarriageDate(LocalDate.of(2018,8,28));
        RegisterOffice ro = new RegisterOffice("ID_RO_1", "marriageOfiiceName1", "OfficeAreaId111");
        order.setMarriageOffice(ro);

        Street street = new Street("01001", "First street");

        //Адреса, викликав конструктор
        Address address = new Address("03134","Sofiyvska Borshagivka", street,
                "10A", "2","28");

        //Чоловік
        Adult husband = new Adult("Biletskyi","Danylo","Vitalievich",
                LocalDate.of(2003,5,20));
        husband.setPassportNumber("" + (1000 + orderID));
        husband.setPassportIssueDate(LocalDate.of(2021,11,1));
        PassportOffice po1  = new PassportOffice("ID_PO_1", "dad", "dan");
        husband.setPassportDepartment(po1);
        husband.setStudentId("КВ12345620");
        husband.setAddress(address);
        husband.setUniversity(new University("0001","NTUU KPI"));

        //Дружина
        Adult wife = new Adult("Biletska","Diana","Serhiivna",
                LocalDate.of(2005,2,24));
        wife.setPassportNumber("" + (2000 + orderID));
        PassportOffice po2  = new PassportOffice("ID_PO_2", "11111", "name_name");
        wife.setPassportDepartment(po2);
        wife.setPassportIssueDate(LocalDate.of(2021,11,1));
        wife.setStudentId("KB12345987");
        wife.setAddress(address);
        wife.setUniversity(new University("0002","NTUU KPI"));

        //Дитина
        Child child1 = new Child("Biletska","Avrelia","Danylivna",
                LocalDate.of(2030,6,2));
        child1.setAddress(address);
        child1.setBirthCertificateNumber("AB12345" + orderID);
        child1.setDateOfReceivingBirthCertificate(LocalDate.of(2030,6,5));
        RegisterOffice ro2 = new RegisterOffice("ID_RO_3", "vitalOffice1", "OfficeAreaId222");
        child1.setDepartmentOfVitalRecords(ro2);

        //Дитина
        Child child2 = new Child("Biletska","Glikeria","Danylivna",
                LocalDate.of(2030,6,2));
        child2.setAddress(address);
        child2.setBirthCertificateNumber("AB12346" + orderID);
        child2.setDateOfReceivingBirthCertificate(LocalDate.of(2030,6,5));
        child2.setDepartmentOfVitalRecords(ro2);

        //Присвоєння об'єктів з полями в поля заявки
        order.setHusband(husband);
        order.setWife(wife);
        order.addChild(child1);
        order.addChild(child2);

        return order;
    }



}