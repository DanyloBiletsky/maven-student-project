package edu.javaproject.studentorder;

import edu.javaproject.studentorder.dao.StudentDaoImpl;
import edu.javaproject.studentorder.dao.StudentOrderDao;
import edu.javaproject.studentorder.domain.*;
import edu.javaproject.studentorder.domain.children.AnswerChildren;
import edu.javaproject.studentorder.domain.register.AnswerCityRegister;
import edu.javaproject.studentorder.domain.student.AnswerStudent;
import edu.javaproject.studentorder.domain.wedding.AnswerMarriage;
import edu.javaproject.studentorder.exception.DaoException;
import edu.javaproject.studentorder.mail.MailSender;
import edu.javaproject.studentorder.validator.ChildrenValidator;
import edu.javaproject.studentorder.validator.CityRegisterValidator;
import edu.javaproject.studentorder.validator.MarriageStatusValidator;
import edu.javaproject.studentorder.validator.StudentStatusValidator;

import java.util.LinkedList;
import java.util.List;

public class StudentOrderValidator {

    private CityRegisterValidator cityRegisterVal;
    private MarriageStatusValidator marriageStatusVal;
    private ChildrenValidator childrenVal;
    private StudentStatusValidator studentStatusVal;
    private MailSender mailSender;

    //конструктор по умолчанию:
    private StudentOrderValidator() {
        //Тут також викликається конструктор по замовчуванню:
        cityRegisterVal = new CityRegisterValidator();
        marriageStatusVal = new MarriageStatusValidator();
        childrenVal = new ChildrenValidator();
        studentStatusVal = new StudentStatusValidator();
        mailSender = new MailSender();
    }

    public static void main(String[] args) {
        StudentOrderValidator sov = new StudentOrderValidator();
        sov.checkAll();
    }

    public void checkAll() {
        // Чтобы проверить все заявки, сначала их читаем:
        try {
            List<StudentOrder> soList = readStudentOrders();

            // Тут ми проганяємо метод checkOneOrder по масиву заявок
//        for (int i = 0; i < orderArray.length; i++) {
//            checkOneOrder(orderArray[i]);
//            System.out.println();
//        }
            // Той же самий варіант через фор іч
            for (StudentOrder so : soList) {
                checkOneOrder(so);
                System.out.println();
            }
        } catch (Exception exception){
            exception.printStackTrace();
        }
        //Мы должны проработать не идеальные варианты поведения системы,
        //а НЕидеальные варианты (продумать все лазейки, как система будет
    }

    public List<StudentOrder> readStudentOrders() throws DaoException {
        return new StudentDaoImpl().getStudentOrders();
    }

    //Метод, в котором проверяется одна заявка полностью
    public void checkOneOrder(StudentOrder order) {
        AnswerCityRegister cityAnswer = checkCityRegister(order);
//        AnswerChildren childrenAnswer = checkChildren(order);
//        AnswerStudent studentAnswer = checkStudent(order);
//        AnswerMarriage marriageAnswer = checkMarriage(order);
//        sendMail(order);
    }
    /*
    Коммент
     */

    public AnswerCityRegister checkCityRegister(StudentOrder so) {
        return cityRegisterVal.checkCityRegister(so);
    }

    public AnswerMarriage checkMarriage(StudentOrder so) {
        return marriageStatusVal.checkMarriage(so);
    }

    public AnswerChildren checkChildren(StudentOrder so) {
        return childrenVal.checkChildren(so);
    }

    public AnswerStudent checkStudent(StudentOrder so) {
        return studentStatusVal.checkStudent(so);
    }

    public void sendMail(StudentOrder so) {
        mailSender.sendMail(so);
    }
}
