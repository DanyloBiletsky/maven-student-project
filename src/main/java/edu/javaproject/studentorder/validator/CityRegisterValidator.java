package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.Person;
import edu.javaproject.studentorder.domain.register.AnswerCityRegister;
import edu.javaproject.studentorder.domain.Child;
import edu.javaproject.studentorder.domain.register.AnswerCityRegisterItem;
import edu.javaproject.studentorder.domain.register.CityRegisterResponse;
import edu.javaproject.studentorder.domain.StudentOrder;
import edu.javaproject.studentorder.exception.CityRegisterException;
import edu.javaproject.studentorder.exception.TransportException;
import edu.javaproject.studentorder.validator.register.CityRegisterChecker;
import edu.javaproject.studentorder.validator.register.FakeCityRegisterChecker;

// Метод получения данных из ЄДДР
public class CityRegisterValidator {

    private static final String IN_CODE = "NO_EDDR";

    public final CityRegisterChecker personChecker;
    public String hostName;
    public String login;
    protected int port;
    String password;
    CityRegisterException exception;


    /* Дуже важливий конструктор по замовчуванню.
    Саме в ньому ми ініціалізуємо об'єкт personChecker.
    Зверни увагу, що цей об'єкт є типу CityRegisterChecker,
    тобто є об'єктом інтерфейса. Але конструктор ми викликаємо у
    FakeCityRegisterChecker (після слова new).

    Отже, саме завдяки цьому конструктору ми розуміємо,
    що використовуємо саме цей клас, що імплементує інтерфейс.
    */
    public CityRegisterValidator() {
        personChecker = new FakeCityRegisterChecker();
    }

    // Проверяем в реестре всю семью:
    public AnswerCityRegister checkCityRegister(StudentOrder so) {
        //Обобщенный ответ в виде отчета:
        AnswerCityRegister checkedCityAnswer = new AnswerCityRegister();
        //Проверяем мужа и жену:
        checkedCityAnswer.addItem(checkPerson(so.getHusband()));
        checkedCityAnswer.addItem(checkPerson(so.getWife()));
        //Проверяем детей:
        for (Child child : so.getChildren()) {
            checkedCityAnswer.addItem(checkPerson(child));
        }
        //При проверке для каждого члена семьи вызываем свой метод checkPerson:
        return checkedCityAnswer;
    }

    // Метод работает над получением данных из ЄДДР
    private AnswerCityRegisterItem checkPerson(Person person) {
        // Статус общего ответа и ошибка. Статус в любом случае будет иниц-н, а ошиба -- нет. см. null.
        AnswerCityRegisterItem.CityStatus status = null;
        AnswerCityRegisterItem.CityError error = null;
        /*
        Пробуем осуществить проверку. Если все ОК, устанавливаем статус через тернарный оператор.
        Иначе обрабатываем ошибку, статус также меняется, создаем ошибку.
         */
        try {
            CityRegisterResponse tmp = personChecker.checkPerson(person);
            status = tmp.isExisting() ?
                    AnswerCityRegisterItem.CityStatus.YES :
                    AnswerCityRegisterItem.CityStatus.NO;
        } catch (CityRegisterException exception) {
            exception.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            /*
            Ниже ошибка с возвращаемым из ЄДДР кодом.
             */
            error = new AnswerCityRegisterItem.CityError(exception.getCode(), exception.getMessage());
        } catch (TransportException exception) {
            exception.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            /*
            Ниже ошибка с внутренним возвращаемым кодом (константа). Т.е. ошибка связана с транспортом.
             */
            error = new AnswerCityRegisterItem.CityError(IN_CODE, exception.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError("",exception.getMessage());
        }

        AnswerCityRegisterItem ans = new AnswerCityRegisterItem(error, status, person);

        return null;
    }

}
