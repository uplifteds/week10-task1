package org.uplifteds.xmlutil;

import org.uplifteds.entity.Ticket;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement(name = "Tickets")
public class Tickets {

    @XmlElement(name = "Ticket")
    private List<Ticket> tickets = new ArrayList<>();

    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(tickets.toArray());
    }
}
