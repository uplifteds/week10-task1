package org.uplifteds;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.uplifteds.DML_DDL.NativeCRUDMethods;
import org.uplifteds.dao.DAO;
import org.uplifteds.dao.EventDAO;
import org.uplifteds.daooperations.DAODBOperator;
import org.uplifteds.service.BookingFacade;
import org.uplifteds.service.TicketService;
import org.uplifteds.service.UserService;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AdvancedDBBookingTicketLauncher {

  public static ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/applicationContext.xml");
  public static SessionFactory sessionFactoryGlobal;

  public static List<String> listOfTables = new ArrayList<>();

  public static void main(String[] args) throws IOException, SQLException, ParseException {
  //What kind of index PostgreSQL creates when we use default CREATE INDEX command?
    //answer: btree

    prepareTablesAndValues();

    try (SessionFactory factory = new Configuration().configure().buildSessionFactory()){

      sessionFactoryGlobal = factory;

      DAO eventDAO = new EventDAO(factory);
      eventDAO.cleanAllTables();

      DAODBOperator.doEventDAO(factory);
      DAODBOperator.doUserDAO(factory);
      DAODBOperator.doTicketDAO(factory);

      //provide queries for the next cases:
      //Table “tickets”: Find record by category in (…)
      DAODBOperator.findTicketByCriteria(factory, "ULTRA");

      UserService userService = context.getBean("userService", UserService.class);
      userService.bookOrCancelTicket();

    }
    performNativeJDBC();
  }

  private static void performNativeJDBC() throws IOException, SQLException {
    try (FileInputStream fis = new FileInputStream ("src/main/resources/prefDB.properties")) {
      Properties props = new Properties();
      props.load(fis);
      String url = (String) props.get("jdbc.url");
      String username = (String) props.get("jdbc.username");
      String password = (String) props.get("jdbc.password");
      try (Connection conn = DriverManager.getConnection(url, username, password);
           Statement stmt = conn.createStatement()) {

        int monthValue = getMonthValueFromTicketPurchaseDate();
        String materializedViewName = "mv_users_tickets";

        //Provide 2 views with appropriate type for these requirements:
        //1.As a data analyst, I want to be able to get the next aggregated information:
        // month, category, age, tickets count

        NativeCRUDMethods.createMVUsersJoinTickets(stmt, monthValue, materializedViewName);
        NativeCRUDMethods.readFromMVUsersJoinTickets(stmt, monthValue, materializedViewName);

      }
    }
  }

  private static int getMonthValueFromTicketPurchaseDate() {
    Timestamp ticketTs = TicketService.listOfTickets.get(0).getPurchase_date();
    LocalDate ldt = ticketTs.toLocalDateTime().toLocalDate();
    int monthValue = ldt.getMonthValue();
    return monthValue;
  }

  private static void prepareTablesAndValues() {
    BookingFacade eventService = (BookingFacade) context.getBean("eventService");
    BookingFacade userService = (BookingFacade) context.getBean("userService");
    List<String> les = eventService.doService();
    List<String> lus = userService.doService();

    listOfTables.add("users");
    listOfTables.add("tickets");
    listOfTables.add("events");
  }

}
