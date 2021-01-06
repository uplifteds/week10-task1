package org.uplifteds.DML_crud;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Statement;


public class DDLAndDMLMethods {
    public static String doCopyDatabase(Statement stmt, String dbName, String dbOwner) throws SQLException {
        String copyDBPrefix = "copy";
        System.out.println("Terminating connections to DB... ");
        String sql = "SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity \n" +
                "WHERE pg_stat_activity.datname = '" + dbName + "' AND pid <> pg_backend_pid();";
        ResultSet resultSet = stmt.executeQuery(sql); // SELECT is DML; require executeQuery

        System.out.println("Copying DB with tables...");
        String sql2 = "DROP DATABASE IF EXISTS " + copyDBPrefix + dbName + ";" +
                "CREATE DATABASE " + copyDBPrefix + dbName + " WITH TEMPLATE " + dbName + " OWNER " + dbOwner + ";";
        stmt.executeUpdate(sql2); // DROP,CREATE are DDL; require executeUpdate or execute
        return copyDBPrefix + dbName;
    }

    public static void doCopyTablesUntilDbIs10GB(Statement stmt, String dbTable) throws SQLException {
        int numberOfTableCopiesUntilDbIs10GB = 234;
        String copyDBPrefix = "copy";

        for (int i = 1; i <= numberOfTableCopiesUntilDbIs10GB; i++) {
            System.out.println("Copying table.." + i);
            String sql = "CREATE TABLE IF NOT EXISTS " + copyDBPrefix + dbTable + i + " AS\n" +
                          " TABLE " + dbTable + "\n" +
                          " WITH DATA;";
            stmt.executeUpdate(sql); // drop,create require executeUpdate or execute
        }
    }

    // delete generated TablesUntilDbIs10GB:
    //DO
    //$do$
    //DECLARE
    //   _tbl text;
    //BEGIN
    //FOR _tbl  IN
    //    SELECT quote_ident(table_schema) || '.'
    //        || quote_ident(table_name)      -- escape identifier and schema-qualify!
    //    FROM   information_schema.tables
    //    WHERE  table_name LIKE 'copyexamresults' || '%'  -- your table name prefix
    //    AND    table_schema NOT LIKE 'pg\_%'    -- exclude system schemas
    //LOOP
    //EXECUTE
    //  'DROP TABLE ' || _tbl;  -- see below
    //END LOOP;
    //END
    //$do$;
}
