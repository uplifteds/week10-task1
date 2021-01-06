package org.uplifteds;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.uplifteds.dao.DAO;
import org.uplifteds.dao.EventDAO;
import org.uplifteds.daooperations.DAODBOperator;
import org.uplifteds.service.BookingFacade;
import org.uplifteds.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdvancedDBBookingTicketLauncher {

  public static ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/applicationContext.xml");
  public static SessionFactory sessionFactoryGlobal;

  public static List<String> listOfTables = new ArrayList<>();

  public static void main(String[] args) throws IOException, SQLException {
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
