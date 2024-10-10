package edu.javaproject.studentorder.dao;

import com.sun.xml.internal.bind.v2.TODO;
import edu.javaproject.studentorder.domain.CountryArea;
import edu.javaproject.studentorder.domain.PassportOffice;
import edu.javaproject.studentorder.domain.RegisterOffice;
import edu.javaproject.studentorder.domain.Street;
import edu.javaproject.studentorder.exception.DaoException;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DictionaryDaoImplTest {


    private static final Logger logger  = LoggerFactory.getLogger(DictionaryDaoImplTest.class);

    @BeforeClass
    public static void startUp() throws URISyntaxException, IOException {
        DBInit.startUp();
    }


    @Test
    public void testStreet() throws DaoException {
        LocalDateTime dt1 = LocalDateTime.now();
        LocalDateTime dt2 = LocalDateTime.now();
        logger.info("TEST {} {}", dt1, dt2);
        List<Street> streets = new DictionaryDaoImpl().findStreets("");
        Assert.assertTrue(streets.size() == 9);
    }

    @Test
    public void testPassport() throws DaoException {
        List<PassportOffice> poList = new DictionaryDaoImpl().findPassportOffices("UA80000000000093317");
        Assert.assertTrue(poList.size() == 1);
    }

    @Test
    public void testRegisterOffice() throws DaoException {
        List<RegisterOffice> roList = new DictionaryDaoImpl().findRegisterOffices("UA80000000000093317");
        Assert.assertTrue(roList.size() == 1);
    }

    @Test
    public void testAreas() throws DaoException {
        // Тестуємо Київ та райони Києва (м. Київ також виводиться):
        List<CountryArea> caList1 = new DictionaryDaoImpl().findAreas("UA80");
        Assert.assertTrue(caList1.size() == 11);

       /* for (CountryArea s : caList1){
            System.out.println(s.getAreaId() + " : " + s.getAreaName());
        }
       */

        // Тестуємо області та м. Київ без деталізації (коренева вибірка):
        List<CountryArea> caList2 = new DictionaryDaoImpl().findAreas("");
        Assert.assertTrue(caList2.size() == 4);

        // Тестуємо райони АР Крим (сама АР Крим не виводиться)
        List<CountryArea> caList3 = new DictionaryDaoImpl().findAreas("UA01000000000013043");
        Assert.assertTrue(caList3.size() == 2);

        // Тестуємо далі...
        //TODO tests
    }




}