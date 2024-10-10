package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.wedding.AnswerMarriage;
import edu.javaproject.studentorder.domain.StudentOrder;

public class MarriageStatusValidator {
    public int marriageDocNumber;
    public AnswerMarriage checkMarriage(StudentOrder so){
        System.out.println("Marriage checking is running.");
        return new AnswerMarriage();
    }
}
