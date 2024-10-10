package edu.javaproject.studentorder.domain.register;

/*
*
* Назва класу може тлумачитися таким чином:
* відповідь в результаті перевірки місця реєстрації людей.
*
* Цей клас потрібен для
* спілкування з класом CityRegisterValidator.java
* В ньому є два поля. Одне з них відповідає за
* існування людини в базі даних (в Державному
* реєстрі населення).
* Інше поле типу Boolean (клас обгортка) відповідає
* за тимчасове/нетимчасове місце проживання людини, якщо
* ця людина існує в базі.
* Друге поле може бути true, false або null.
*
*
*/
public class CityRegisterResponse {
    private boolean existing;
    private Boolean temporal;

    // Гетери і сетери
    public boolean isExisting() {
        return existing;
    }

    public void setExisting(boolean existing) {
        this.existing = existing;
    }

    public Boolean getTemporal() {
        return temporal;
    }

    public void setTemporal(Boolean temporal) {
        this.temporal = temporal;
    }

    @Override
    public String toString() {
        return "CityRegisterCheckerResponse{" +
                "existing=" + existing +
                ", temporal=" + temporal +
                '}';
    }
}
