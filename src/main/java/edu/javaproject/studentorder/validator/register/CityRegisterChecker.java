package edu.javaproject.studentorder.validator.register;

import edu.javaproject.studentorder.domain.register.CityRegisterResponse;
import edu.javaproject.studentorder.domain.Person;
import edu.javaproject.studentorder.exception.CityRegisterException;
import edu.javaproject.studentorder.exception.TransportException;

/*
Інтерфейс - це як функція для класів, які імплементують цей інтерфейс.

Інтерфейс створюється для класів, у яких назва методу (функціонала) однакова.
В інтерфейсі ми визначаємо певний функціонал, який потім будуть реалізовувати
інші класи.

Інші класи імплементують саме інтерфейс (а не метод).
Методи у кожного класа, який імплементує даний інтерфейс,
називаться так само (і мають такі самі вхідні параметри), як і
нижче описані методи (всередині інтерфейса - контракт)
*/
public interface CityRegisterChecker {
    /*
    Нижче описуються контракти (назви методів).
    Всі методи всередині інтерфейса є публічними.
    */
    CityRegisterResponse checkPerson(Person person)
            throws CityRegisterException, TransportException;
}
