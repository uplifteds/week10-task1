package org.uplifteds.service;

import org.uplifteds.AdvancedDBBookingTicketLauncher;
import org.uplifteds.entity.Event;

import java.util.ArrayList;
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

    private void sellTickets(String place, String date) {
        TicketService.totalTickets = event.getCapacity();
        TicketService.place = place;
        TicketService.create_date = date;

        BookingFacade ticketService = (BookingFacade) AdvancedDBBookingTicketLauncher.context.getBean("ticketService");
        List<String> listOfServiceOutput = ticketService.doService();
    }

}
