package org.uplifteds.DML_DDL;

import org.uplifteds.entitycomposite.UserJoinTicket;
import org.uplifteds.service.EventService;
import org.uplifteds.service.TicketService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JDBCCRUDMethods {
    static String sumColumn = "sum";

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

    public static void createMVEventsJoinTickets(Statement stmt, String mvName) throws SQLException {

        String sql2 = "DROP MATERIALIZED VIEW IF EXISTS " + mvName + ";\n" +
                " create materialized view " + mvName + "  as\n" +
                " select DATE(events.date),\n" +
                " tickets.category, DATE(tickets.purchase_date) as purchase_date, tickets.eventid, " +
                    "sum(purchase_date - TO_TIMESTAMP(date, 'YYYY-MM-DD')) AS duration\n" +
                "from events\n" +
                "inner join tickets\n" +
                "on\n" +
                "events.id = tickets.eventid\n" +
                "group by tickets.purchase_date, events.date, tickets.eventid,tickets.category" +
                ";";
        stmt.executeUpdate(sql2);
    }

    public static void readFromMVUsersTableJoinTickets(Statement stmt, int monthNumber, String mvName) throws SQLException {
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

    public static void calcPercentOfTicketPerCategory(Statement stmt, String cat, String mvName) throws SQLException {
        Double numberOfTicketsPerCategory = null;
        Double totalTickets = Double.valueOf(EventService.listOfEvents.get(0).getCapacity());

        String sql2 = "select sum(count_ticket) \n" +
                " from " + mvName + "\n" +
                " where category = '" + cat + "';";
        ResultSet resultSet = stmt.executeQuery(sql2);

        while (resultSet.next()) {
            numberOfTicketsPerCategory = resultSet.getDouble(sumColumn);
        }

        if (totalTickets != null) {
            Double percentOfTicketsPerCategory = (numberOfTicketsPerCategory / totalTickets) * 100;
            System.out.println(" >>> Percentage Of Tickets for " + cat + " Category: " + percentOfTicketsPerCategory);
        } else {
            System.out.println("totalTickets is null. Can't divide by zero");
        }

    }

    public static void calcAvgDayDiffTicketPurchaseDateAndEventDate(Statement stmt, String mvName) throws SQLException {
        String strSumOfnumberOfDayDiff = null;
        Double totalTickets = Double.valueOf(EventService.listOfEvents.get(0).getCapacity());

        String sql2 = "select sum(duration) \n" +
                " from " + mvName + ";";
        ResultSet resultSet = stmt.executeQuery(sql2);

        while (resultSet.next()) {
            strSumOfnumberOfDayDiff = resultSet.getString(sumColumn);
        }

        Double sumOfnumberOfDayDiff = getNumberFromStringTrimBeforeSpace(strSumOfnumberOfDayDiff);

        if (totalTickets != null) {
            Double AvgDayDiff = (sumOfnumberOfDayDiff / totalTickets);
            System.out.println(" > Average day diff between ticket_purchase_date & event_date " + AvgDayDiff);
        } else {
            System.out.println("totalTickets is null. Can't divide by zero");
        }

    }

    private static Double getNumberFromStringTrimBeforeSpace(String strSumOfnumberOfDayDiff) {
        int charOccurence = 0;
        String space = " ";
        strSumOfnumberOfDayDiff = (strSumOfnumberOfDayDiff + space).split(space)[charOccurence];
        Double sumOfnumberOfDayDiff = Double.valueOf(strSumOfnumberOfDayDiff);
        return sumOfnumberOfDayDiff;
    }

}
