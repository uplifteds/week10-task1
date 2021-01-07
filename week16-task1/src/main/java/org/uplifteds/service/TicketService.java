package org.uplifteds.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uplifteds.entity.Ticket;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class TicketService implements BookingFacade {
    Logger logger = LoggerFactory.getLogger(TicketService.class);

    public static int totalTickets;

    public static List<Ticket> listOfTickets = new ArrayList<>();
    public static List<String> categoryList = new ArrayList<>();

    public static String place;
    public static String create_date;
    private EventService es;

    public TicketService(EventService es) {
        this.es = es;
    }

    @Override
    public List<String> doService() {
        final int seatOffset = 10;
        final String phonePrefix = "+7";
        String phoneSuffix = "1234567";
        String cinema_name = "IMAX";
        String cinema_address = "New York";
        int price = 11;

        List<String> listOfServiceOutput = new CopyOnWriteArrayList<>();

        List<String> cinemaPhonesDedupList = getDedupCinemaPhoneList(phonePrefix, phoneSuffix);

        List<String> cinemaFacilityDedupList = getDedupCinemaFacilityList();


        categoryList.add("ULTRA");
        categoryList.add("PREMIUM");
        categoryList.add("STANDARD");

        for (int i = 1; i <= totalTickets; i+= cinemaPhonesDedupList.size()) {
            for (int j = 0; j < cinemaPhonesDedupList.size(); j++) {
                String category = categoryList.get(new Random().nextInt(categoryList.size()));
                String place = generateSeatName(seatOffset, i, j);

                //Please note that the purchase date field value cannot be less than event date field value
                Timestamp purchase_date = addDaysToCreateDay();

                String update_date = create_date;

                Ticket extraTicket = new Ticket((i + j), category, place, cinema_name, cinema_address, price,
                        cinemaPhonesDedupList.get(j), cinemaFacilityDedupList.get(j),
                        purchase_date, create_date, update_date);

                listOfTickets.add(extraTicket);
                logger.info(extraTicket.toString());
                listOfServiceOutput.add(extraTicket.toString());
            }
        }
        //Using prepared function in step 3, populate tables with 100_000 tickets,
        // measure population time and document it: 80 seconds

        System.out.println("Tickets are published: ");
        return listOfServiceOutput;
    }

    private Timestamp addDaysToCreateDay() {
        int numberOfDays = new Random()
                .ints(1, 5)
                .findFirst()
                .getAsInt();

        Timestamp purchase_date = Timestamp.valueOf(create_date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(purchase_date);
        cal.add(Calendar.DAY_OF_WEEK, numberOfDays);
        purchase_date.setTime(cal.getTime().getTime());
        return purchase_date;
    }

    private List<String> getDedupCinemaFacilityList() {
        List<String> cinemaFacilityList = new ArrayList<>();
        String place = EventService.listOfEvents.get(0).getPlace();
        cinemaFacilityList.add(place);
        cinemaFacilityList.add(place + "_building 1");
        cinemaFacilityList.add(place + "_building 2");
        List<String> cinemaFacilityDedupList = cinemaFacilityList
                .stream()
                .distinct()
                .collect(Collectors.toList());
        return cinemaFacilityDedupList;
    }

    private List<String> getDedupCinemaPhoneList(String phonePrefix, String phoneSuffix) {
        List<String> cinemaPhonesList = new ArrayList<>();
        cinemaPhonesList.add(phonePrefix + "(702)" + phoneSuffix);
        cinemaPhonesList.add(phonePrefix + "(705)" + phoneSuffix);
        cinemaPhonesList.add(phonePrefix + "(747)" + phoneSuffix);
        List<String> cinemaPhonesDedupList = cinemaPhonesList
                .stream()
                .distinct()
                .collect(Collectors.toList());
        return cinemaPhonesDedupList;
    }

    private String generateSeatName(int seatOffset, int i, int j) {
        String place;
        int seatSequenceInt = (i + j) + seatOffset;
        place = "seat" + seatSequenceInt;
        return place;
    }

}
