package org.uplifteds.entitygenerator;


import org.uplifteds.entity.Post;
import org.uplifteds.entity.User;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserGenerator {
//> 70 000 friendships (375 Users made friends with all each other),
    public static final int EXTRA_NUMBER_OF_STUDENTS = 372; // should be 372, total 375

    public static final int GLOBAL_FIRST_ID = 1;
    public static int maxNameLength = 4;
    public static int maxSurnameLength = 8;
    public static int userPrintStep = 50;

    public static List<User> listOfUsers = new CopyOnWriteArrayList<>();

    public static List<User> setListOfStudents(){

        List<String> nameList = fillStudentNameInList();
        List<String> surnameList = fillStudSurnameInList();

        for (int i = GLOBAL_FIRST_ID; (i - GLOBAL_FIRST_ID) < nameList.size(); i++){
            User userTemp = new User();
            userTemp.setId(i);
            userTemp.setName(nameList.get(i - GLOBAL_FIRST_ID));
            userTemp.setSurname(surnameList.get(i - GLOBAL_FIRST_ID));
            userTemp.setBirthdate(Date.valueOf(RandomType.generRandBirthdate()));
            listOfUsers.add(userTemp);
        }
        System.out.println("Users were generated");
        printStep(listOfUsers);
        return listOfUsers;
    }

    private static void printStep (List<User> listOfUsers){
        for (int i = 0; i < listOfUsers.size(); i+= userPrintStep) {
            System.out.println(listOfUsers.get(i).toString());
        }
    }

    private static List<String> fillStudentNameInList() {

        List<String> studentNameList = new CopyOnWriteArrayList<>();
        // this is also linked to PreparedStatement in doInsertListOfStudents() method
        studentNameList.add("John");
        studentNameList.add("Cay");
        studentNameList.add("Joshua");
        studentNameList.add("Alexey");

        for (int i = 1; i < EXTRA_NUMBER_OF_STUDENTS; i++){
            studentNameList.add(RandomType.generRandString(maxNameLength));
        }
        return studentNameList;
    }

    private static List<String> fillStudSurnameInList() {
        List<String> studSurnameList = new CopyOnWriteArrayList<>();
        studSurnameList.add("Doe");
        studSurnameList.add("Horstmann");
        studSurnameList.add("Bloch");
        studSurnameList.add("Shipilev");

        for (int i = 1; i < EXTRA_NUMBER_OF_STUDENTS; i++){
            studSurnameList.add("Mr." + RandomType.generRandString(maxSurnameLength));
        }
        return studSurnameList;
    }

}
