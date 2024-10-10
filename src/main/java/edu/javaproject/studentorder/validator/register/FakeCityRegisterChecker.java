package edu.javaproject.studentorder.validator.register;

import edu.javaproject.studentorder.domain.Adult;
import edu.javaproject.studentorder.domain.Child;
import edu.javaproject.studentorder.domain.register.CityRegisterResponse;
import edu.javaproject.studentorder.domain.Person;
import edu.javaproject.studentorder.exception.CityRegisterException;
import edu.javaproject.studentorder.exception.TransportException;

public class FakeCityRegisterChecker implements CityRegisterChecker {

/*
Оскільки порівнювати об'єкти напряму і писати константи в методі equals
є поганою практикою за словами автора курсу, то ми створюємо константи
за допомогою вводу "psfs" (нижче) і записуємо результат, який буде
вважатися хорошим для існування такої особи під час перевірки номера
паспорта чоловіка та жінки
*/

    private static final String GOOD_1 = "1000";
    private static final String GOOD_2 = "2000";
    private static final String BAD_1 = "1001";
    private static final String BAD_2 = "2001";
    private static final String ERROR_1 = "1002";
    private static final String ERROR_2 = "2002";
    private static final String ERROR_TRANSPORT_1 = "1003";
    private static final String ERROR_TRANSPORT_2 = "2003";
    private static final String CHILD_GOOD = "AB123450";
    private static final String CHILD_BAD = "AB123451";
    private static final String CHILD_ERROR = "AB123451";

    public CityRegisterResponse checkPerson(Person person)
            throws CityRegisterException, TransportException {

        //Заздалегідь створюємо екземпляр класу
        CityRegisterResponse result = new CityRegisterResponse();

        // Входимо в тіло оператора, якщо person є екземпляром класса Adult:
        if (person instanceof Adult) {

            //Приводимо тип Person -> Adult
            /*
            * Якщо не приводити тип даних, тоді ми не зможемо
            * взаємодіяти з методами нащадків класу Person
            */
            Adult t = (Adult) person;

            /*
            *Порівнюємо об'єкти.
            *Номер паспорта дорівнює або GOOD_1 (для чоловіка), або GOOD_2 (для жінки)
            */

            if (t.getPassportNumber().equals(GOOD_1) || t.getPassportNumber().equals(GOOD_2)) {
                result.setExisting(true);
                result.setTemporal(false);
            }

            if (t.getPassportNumber().equals(BAD_1) || t.getPassportNumber().equals(BAD_2)) {
                result.setExisting(false);
            }

            if (t.getPassportNumber().equals(ERROR_1) || t.getPassportNumber().equals(ERROR_2)) {
                CityRegisterException exception = new CityRegisterException("code 1", "ЄДДР ERROR " + t.getPassportNumber());
                throw exception;
            }
            if (t.getPassportNumber().equals(ERROR_TRANSPORT_1) || t.getPassportNumber().equals(ERROR_TRANSPORT_2)){
                TransportException exception = new TransportException("Transport ERROR!");
                throw exception;
            }
        }

        if (person instanceof Child){
            Child myChild = (Child) person;
            result.setExisting(true);
            result.setTemporal(true);
//                if(myChild.getBirthCertificateNumber().equals(CHILD_GOOD)){
//                    result.setExisting(true);
//                }
//
//                if(myChild.getBirthCertificateNumber().equals(CHILD_BAD)){
//                    result.setExisting(false);
//                }
//
//                if(myChild.getBirthCertificateNumber().equals(CHILD_ERROR)){
//                    BirthCertificateException exception = new BirthCertificateException("FAKE Birth" +
//                            "Certificate Number!");
//                    throw exception;
//                }
        }

        System.out.println(result);

        return result;
    }
}
