package org.uplifteds.daooperations;

import org.hibernate.SessionFactory;
import org.uplifteds.dao.*;
import org.uplifteds.entity.Event;
import org.uplifteds.entity.Ticket;
import org.uplifteds.entity.User;
import org.uplifteds.service.EventService;
import org.uplifteds.service.TicketService;
import org.uplifteds.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DAODBOperator {
    public static int firstId = 1;
    static int firstListElement = 0;

    public static void doEventDAO(SessionFactory factory) throws IOException, SQLException {

        final Event event = EventService.listOfEvents.get(firstListElement);

        DAO<Event> eventDAO = new EventDAO(factory);

        eventDAO.create(event);

        final Event readEvent = eventDAO.read(firstId);
        System.out.println("Created : " + readEvent);
        System.out.println();
    }

    public static void doUserDAO(SessionFactory factory) throws IOException, SQLException {

      final User user = UserService.listOfUsers.get(firstListElement);

      DAO<User> userDAO = new UserDAO(factory);

      userDAO.create(user);

      final User readUser = userDAO.read(firstId);
      System.out.println("Created : " + readUser);
      System.out.println();
    }

    public static void doTicketDAO(SessionFactory factory) throws IOException, SQLException {
      DAO<Ticket> ticketDAO = new TicketDAO(factory);

      List<Ticket> listOfTickets = TicketService.listOfTickets;
      for (Ticket tempTicket: listOfTickets){
        ticketDAO.create(tempTicket);
      }

      ticketDAO.doUpdateJoinColumnInTable("event");
      ticketDAO.doUpdateJoinColumnInTable("user");
      for (Ticket tempTicket: listOfTickets){
            tempTicket.setEvent(EventService.listOfEvents.get(firstListElement));
            tempTicket.setUser(UserService.listOfUsers.get(firstListElement));
      }

      for (int i = firstId; i <= listOfTickets.size(); i++){
        final Ticket iterTicket = ticketDAO.read(i);
//        System.out.println("Created : " + iterTicket);
        System.out.println();
      }

    }

    public static void findTicketByCriteria(SessionFactory factory, String category) {
        TicketDAO ticketDAO2 = new TicketDAO(factory);
        List<Ticket> foundList =  ticketDAO2.findByCriteria (category);
        for (Ticket temp : foundList){
            System.out.println(" ** ### FOUND : " + temp.toString());
        }
    }

}
