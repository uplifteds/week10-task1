package org.uplifteds.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uplifteds.entity.Event;
import org.uplifteds.entity.Ticket;
import org.uplifteds.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserService implements BookingFacade {
    Logger logger = LoggerFactory.getLogger(TicketService.class);
    public static List<User> listOfUsers = new ArrayList<>();
    private User user;

    public UserService(User user) {
        this.user = user;
    }

    public List<String> doService (){
        String userServiceOutput = "\nUser is created: " + user.toString();
        System.out.println(userServiceOutput);
        List<String> listOfServiceOutput = new CopyOnWriteArrayList<>();
        listOfServiceOutput.add(userServiceOutput);
        listOfUsers.add(user);

        return listOfServiceOutput;
    }

    public void bookOrCancelTicket() {
        List<Event> listOfEvents = checkAvailableEvents();
        Event selectedEvent = selectEvent(listOfEvents);
        if (TicketService.listOfTickets.size() > 0) {
            Ticket t = bookTicketForEvent(selectedEvent);
            Ticket t2 = bookTicketForEvent(selectedEvent);
            cancelBooking(t);
        }
    }

    private List<Event> checkAvailableEvents() {
        System.out.println("Available events for User: " + EventService.listOfEvents.toString());
        return EventService.listOfEvents;
    }

    private Event selectEvent(List<Event> listOfEvents) {
        return listOfEvents.get(0);
    }

    private Ticket bookTicketForEvent(Event selectedEvent) {
        Ticket t = null;
        try {

            t = TicketService.listOfTickets.get(0);
            if (t.getCreate_date().equals(selectedEvent.getDate()) && t.getCinema_facility().contains(selectedEvent.getPlace())) {

                    TicketService.listOfTickets.remove(t);

                    System.out.println("\n The User have bought the ticket: ");

                    logger.info(t.toString());

            } else {
                System.out.println("Error: Can not buy ticket. The date and place are not match between Event and Ticket");
            }

        } catch (Exception e) {
            System.out.println("There are no ticket to buy. Sold out");
        }
        return t;
    }

    private void cancelBooking(Ticket t) {
        if (t != null) {
            TicketService.listOfTickets.add(t);

            System.out.println("\n The User have cancelled the Booking-ticket: ");

            logger.info(t.toString());
        } else {
            System.out.println("Can not cancel non-existing Booking-Ticket");
        }
    }

}
