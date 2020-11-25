package org.uplifteds.DML_crud;

import org.uplifteds.JDBCSocialNet;
import org.uplifteds.entity.Like;
import org.uplifteds.entity.User;

import java.sql.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CRUDSearchMethods {

    public static User getStudentByNameExact(Statement stmt, String name) throws SQLException {
        // case-sensitive exact search
        String sql = "select * from " + JDBCSocialNet.listOfTables.get(2) +
                " where " + User.nameFieldName + " = '" + name + "'";
        ResultSet resultSet = stmt.executeQuery(sql);

        User stud = findStudentInResultSet(resultSet);

        return stud;
    }

    public static User getStudentBySurnamePartial(Statement stmt, String surname) throws SQLException {
        // case-insensitive partial search
        String sql = "select * from " + JDBCSocialNet.listOfTables.get(2) +
                " where LOWER(" + User.surnameFieldName + ") LIKE LOWER('%" + surname + "%')";
        ResultSet resultSet = stmt.executeQuery(sql);

        User stud = findStudentInResultSet(resultSet);
        return stud;
    }

    public static User getStudentWithMarkBySurnamePartial(Statement stmt, String surname) throws SQLException {
        // case-insensitive partial search
        String sql = "select " + JDBCSocialNet.listOfTables.get(2) + ".* " +
                "from " + JDBCSocialNet.listOfTables.get(2) +
                " INNER JOIN " + JDBCSocialNet.listOfTables.get(0) + " ON " + JDBCSocialNet.listOfTables.get(2) + "." +
                User.idFieldName + " = " + JDBCSocialNet.listOfTables.get(0) + "." + Like.useridFieldName +
                " where LOWER(" + User.surnameFieldName + ") LIKE LOWER('%" + surname + "%') " +
                " and " + JDBCSocialNet.listOfTables.get(0) + "." + Like.likeTsFieldName + " > 0" +
                " LIMIT 1";
        ResultSet resultSet = stmt.executeQuery(sql);

        /*
        SELECT Students.*
        FROM students
        INNER JOIN ExamResults ON students.id = ExamResults.student_id
        where LOWER(students.surname) like LOWER('%" + surname + "%') and ExamResults.mark > 0
        LIMIT 1
         */

        User stud = findStudentInResultSet(resultSet);
        return stud;
    }

    public static User getStudentByIdExact(Statement stmt, int id) throws SQLException {
        // case-sensitive exact search
        String sql = "select * from " + JDBCSocialNet.listOfTables.get(2) +
                " where " + User.idFieldName + " = '" + id + "'";
        ResultSet resultSet = stmt.executeQuery(sql);

        System.out.println("Updated timestamp should be Now");
        User stud = findStudentInResultSet(resultSet);
        return stud;
    }

    public static Like getExamResultByMark(Statement stmt, String mark) throws SQLException {
        String sql = "SELECT * " +
                "FROM examresults " +
                "where ExamResults.mark = " + mark + ";";
        ResultSet resultSet = stmt.executeQuery(sql);

        Like er = findExamResInResultSet(resultSet);
        return er;
    }

    public static void doFoundCondition(int id, String s) {
        if (id > 0) {
            System.out.println("# Found: " + s);
        } else {
            System.out.println("...nothing is found");
        }
    }

    private static User findStudentInResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        System.out.println("\n# Searching ... ");
        List<String> l = new CopyOnWriteArrayList<>();
        User stud = new User();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                String columnValue = resultSet.getString(i);
                l.add(columnValue);
            }
            stud.setId(Integer.parseInt(l.get(0)));
            stud.setName(l.get(1));
            stud.setSurname(l.get(2));
            stud.setBirthdate(Date.valueOf(l.get(3)));
        }
        resultSet.close();
        doFoundCondition(stud.getId(), stud.toString());
        return stud;
    }

    private static Like findExamResInResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        System.out.println("\n# Searching ... ");
        List<Integer> l = new CopyOnWriteArrayList<>();
        Like er = new Like();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                Integer columnValue = resultSet.getInt(i);
                l.add(columnValue);
            }
            er.setId(l.get(0));
            er.setUserid(l.get(1));
            er.setPostid(l.get(2));
            er.setLikeTs(Timestamp.valueOf(String.valueOf(l.get(3))));
        }
        doFoundCondition(er.getId(), er.toString());
        resultSet.close();

        return er;
    }

}
