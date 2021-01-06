package org.uplifteds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.uplifteds.dao.Ticket;
import org.uplifteds.serviceinterface.BookingFacade;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TicketService implements BookingFacade {
    private static final Logger logger = LogManager.getLogger(TicketService.class);

    public static int totalTickets;

    public static List<Ticket> listOfTickets = new ArrayList<>();

    public static String place;
    public static Date date;
    private EventService es;

    public TicketService(EventService es) {
        this.es = es;
    }

    @Override
    public List<String> doService() {
        List<String> listOfServiceOutput = new CopyOnWriteArrayList<>();

        System.out.println("Tickets are published: ");
        for (int i = 1; i <= totalTickets; i++) {
            Ticket t = new Ticket(i, place, date, 11);
            listOfTickets.add(t);
            logger.info(t.toString());
            listOfServiceOutput.add(t.toString());
        }
        return listOfServiceOutput;
    }

}
