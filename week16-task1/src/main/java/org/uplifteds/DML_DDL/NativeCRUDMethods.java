package org.uplifteds.DML_DDL;

import org.uplifteds.entity.User;
import org.uplifteds.entitycomposite.UserJoinTicket;
import org.uplifteds.service.TicketService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NativeCRUDMethods {

    public static void createMVUsersJoinTickets(Statement stmt, int monthNumber, String mvName) throws SQLException {
        Timestamp ticketTs = TicketService.listOfTickets.get(0).getPurchase_date();
        LocalDate ldt = ticketTs.toLocalDateTime().toLocalDate();
        int yearValue = ldt.getYear();

        String sql2 = "DROP MATERIALIZED VIEW IF EXISTS " + mvName + ";\n" +
                " create materialized view " + mvName + "  as\n" +
                " select users.age,\n" +
                " tickets.userid, tickets.category, DATE(tickets.purchase_date) as month,  count(*) as count_ticket\n" +
                "from users\n" +
                "inner join tickets\n" +
                "on\n" +
                "users.id = tickets.userid\n" +
                "group by tickets.purchase_date, users.age, tickets.userid,tickets.category\n" +
                "having tickets.purchase_date > '" + yearValue + "-" + monthNumber + "-01'::date\n" +
                "and tickets.purchase_date < '" + yearValue + "-" + monthNumber + "-28'::date" +
                ";";
        stmt.executeUpdate(sql2);
    }

    public static void readFromMVUsersJoinTickets(Statement stmt, int monthNumber, String mvName) throws SQLException {
        String sql2 = "select * from " + mvName + ";";
        ResultSet resultSet = stmt.executeQuery(sql2);

        List<UserJoinTicket> userJoinTicketList = new CopyOnWriteArrayList<>();
        while (resultSet.next()) {
            UserJoinTicket userJoinTicket = new UserJoinTicket();
            userJoinTicket.setAge(resultSet.getInt("age"));
            userJoinTicket.setUserid(resultSet.getInt("userid"));
            userJoinTicket.setCategory(resultSet.getString("category"));
            userJoinTicket.setMonth(resultSet.getDate("month"));
            userJoinTicket.setCount_ticket(resultSet.getInt("count_ticket"));
            userJoinTicketList.add(userJoinTicket);
        }
        System.out.println(" >>> MATERIALIZED VIEW for MONTH # " + monthNumber);
        for (UserJoinTicket temp : userJoinTicketList){
            System.out.println(temp.toString());
        }
    }

}
