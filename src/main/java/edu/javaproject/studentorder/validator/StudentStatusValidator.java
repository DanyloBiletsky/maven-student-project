package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.student.AnswerStudent;
import edu.javaproject.studentorder.domain.StudentOrder;

public class StudentStatusValidator {
    public AnswerStudent checkStudent(StudentOrder so){
        System.out.println("Students are checking.");
        return new AnswerStudent();
    }
}
