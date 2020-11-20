package org.uplifteds.crud;

import org.uplifteds.CDPDBLauncher;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUDMethods {
    public static void doDeleteValuesInAllTables(Statement stmt) throws SQLException {
        for (int i = 0; i< CDPDBLauncher.listOfTables.size(); i++) {
            String tableToClear = "DELETE FROM " + CDPDBLauncher.listOfTables.get(i);
            stmt.executeUpdate(tableToClear);
        }
        System.out.println("Previous values were deleted in all tables ");
    }

    public static void doReadEntriesFromTable(Statement stmt, String table_name) throws SQLException {
        String sql = "SELECT * FROM " + table_name;
        ResultSet resultSet = stmt.executeQuery(sql);

        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        System.out.println("# Entries were read from table: " + table_name);
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println();
        }
        resultSet.close();
        System.out.println();
    }

//    public static void doDropLinkedTableIfExists(Statement stmt, String table_name) throws SQLException {
//        String delTable = "DROP TABLE IF EXISTS " + table_name;
//        stmt.executeUpdate(delTable);
//        System.out.println("Existing Table was DROPPED: " + table_name);
//    }
}
