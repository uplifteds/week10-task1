package org.uplifteds.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uplifteds.entity.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TicketService implements BookingFacade {
    Logger logger = LoggerFactory.getLogger(TicketService.class);

    public static int totalTickets;

    public static List<Ticket> listOfTickets = new ArrayList<>();

    public static String place;
    public static String date;
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
