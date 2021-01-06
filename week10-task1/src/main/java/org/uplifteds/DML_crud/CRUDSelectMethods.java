package org.uplifteds.DML_crud;

import org.uplifteds.JDBCSocialNet;
import org.uplifteds.entity.User;

import java.sql.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CRUDMethods {
    static String renamedUserIdColumn = "usersid";
    public static void doDeleteValuesInAllTables(Statement stmt) throws SQLException {
        for (int i = 0; i < JDBCSocialNet.listOfTables.size(); i++) {
            String tableToClear = "DELETE FROM " + JDBCSocialNet.listOfTables.get(i);
            stmt.executeUpdate(tableToClear);
        }
        System.out.println("Previous values were deleted in all tables ");
    }

    public static List<String> findDistinctUsersWithMoreThanNItemsMarch2025(Statement stmt, int threshold,
                                                  String joinedTablePrefix, String joinedColumn) throws SQLException {

        String tempTableName = "usersjointable";
        String timeStampOfMarch2025 = "2025-03-";

        String sql = "drop table if exists " + tempTableName + ";\n" +
                "SELECT Users.id as " + renamedUserIdColumn + ", Users.name,Users.surname, Users.birthdate, " +
                joinedTablePrefix + ".*\n" +
                "into " + tempTableName + " \n" +
                "FROM users\n" +
                "INNER JOIN " + joinedTablePrefix + " ON users.id = " + joinedTablePrefix + "." + joinedColumn + "\n" +
                "where " + joinedTablePrefix + ".ts::text like '" + timeStampOfMarch2025 + "%'" +
                ";";
        stmt.executeUpdate(sql); // drop,create require executeUpdate or execute

        String sql2 = "select distinct " + renamedUserIdColumn + ", name, surname,birthdate, count(*) as " +
                joinedTablePrefix + "InMarch2025\n" +
                "from " + tempTableName + "\n" +
                "group by " + renamedUserIdColumn + ",name, surname,birthdate\n" +
                "having count(" + joinedColumn + ") > " + threshold + "\n" +
                "order by " + renamedUserIdColumn + " asc;";
        ResultSet resultSet2 = stmt.executeQuery(sql2); // select require executeQuery
        return findListOfUsersInResultSet(resultSet2, threshold, joinedTablePrefix);
    }

    private static List<String> findListOfUsersInResultSet(ResultSet resultSet, int thresholdFriends,
                                                         String joinedTablePrefix )
            throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        System.out.println("\n# Searching Distinct Users With > " + thresholdFriends + " " + joinedTablePrefix +
                " in March 2025... ");
        List<User> userList = new CopyOnWriteArrayList<>();
        List<String> surnameList = new CopyOnWriteArrayList<>();
        while (resultSet.next()) {
            User usrFromRS = new User();
            usrFromRS.setId(resultSet.getInt(renamedUserIdColumn));
            usrFromRS.setName(resultSet.getString(User.nameFieldName));
            usrFromRS.setSurname(resultSet.getString(User.surnameFieldName));
            usrFromRS.setBirthdate(resultSet.getDate(User.birthdateFieldName));
            userList.add(usrFromRS);
            surnameList.add (resultSet.getString(User.surnameFieldName));
        }
        resultSet.close();
        if (userList.size() > 0) {
            for (User tempUsr : userList) {
                System.out.println(tempUsr.toString());
            }
        }
        return surnameList;
    }

}
