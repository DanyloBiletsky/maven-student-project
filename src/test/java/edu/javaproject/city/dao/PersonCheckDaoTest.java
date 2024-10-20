package edu.javaproject.city.dao;

import edu.javaproject.city.domain.PersonRequest;
import edu.javaproject.city.domain.PersonResponse;
import edu.javaproject.city.exception.PersonCheckException;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

// TODO It should be fixed

public class PersonCheckDaoTest {

    @Test
    public void checkPerson() throws PersonCheckException {
        PersonRequest pr = new PersonRequest();
        pr.setSurname("Шевченко");
        pr.setName("Тарас");
        pr.setPatronymic("Григорович");
        pr.setDateOfBirth(LocalDate.of(1997 ,3,9));
        pr.setStreetCode("10084");
        pr.setBuilding("321");
        pr.setApartment("913");
        pr.setExtension("A");

        PersonCheckDao dao = new PersonCheckDao();
        PersonResponse ps = dao.checkPerson(pr);
        Assert.assertFalse(ps.isRegistered());
        Assert.assertFalse(ps.isTemporal());


    }
}