package edu.javaproject.studentorder.domain.register;

import edu.javaproject.studentorder.domain.Person;

public class AnswerCityRegisterItem {
    private CityError error;
    private CityStatus status;
    private Person person;

    /*
    * С ЧЕМ БУДЕМ РАБОТАТЬ:
    * 1. СТАТУС ОТВЕТА: ДА, НЕТ, ПРОБЛЕМА (ТИП ПЕРЕЧИСЛЕНИЕ)
    * 2. ОПРЕДЕЛЕННАЯ ПЕРСОНА, КОТОРАЯ ПРОВЕРЯЛАСЬ
    * 3. КОД ОШИБКИ (СТРОКОЙ) И ОПИСАНИЕ
    *
    * */

    //Статус ответа:
    public enum CityStatus{
        YES, NO, ERROR;
    }

    // Создаем вспомогательный внутренний вложенный класс
    // Static, чтобы к нему можно было обратиться
    // Тут создаем конструктор и геттеры
    public static class CityError {
        private String code;
        private String text;

        public CityError(String code, String text) {
            this.code = code;
            this.text = text;
        }

        public String getCode() {
            return code;
        }
        public String getText() {
            return text;
        }

    }

    public AnswerCityRegisterItem(CityError error, CityStatus status, Person person) {
        this.error = error;
        this.status = status;
        this.person = person;
    }

    public AnswerCityRegisterItem(CityError error, Person person) {
        this.error = error;
        this.person = person;
    }

    public CityError getError() {
        return error;
    }

    public CityStatus getStatus() {
        return status;
    }

    public Person getPerson() {
        return person;
    }

}
