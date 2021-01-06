package org.uplifteds;

import org.uplifteds.dao.Event;
import org.uplifteds.serviceinterface.BookingFacade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventService implements BookingFacade {
    public static List<Event> listOfEvents = new ArrayList<>();
    private Event event;

    public EventService(Event event2) {
        this.event = event2;
    }

    public List<String> doService (){
        String eventServiceOutput = "Event is created: " + event.toString() + "\n";
        List<String> listOfServiceOutput = new CopyOnWriteArrayList<>();
        listOfServiceOutput.add(eventServiceOutput);

        System.out.println(eventServiceOutput);
        listOfEvents.add(event);
        sellTickets(event.getPlace(), event.getDate());
        return listOfServiceOutput;
    }

    private void sellTickets(String place, Date date) {
        TicketService.totalTickets = event.getCapacity();
        TicketService.place = place;
        TicketService.date = date;

        BookingFacade ticketService = (BookingFacade) BookingTicketLauncher.context.getBean("ticketService");
        List<String> listOfServiceOutput = ticketService.doService();
        BookingTicketLauncher.context.close();
    }

}
