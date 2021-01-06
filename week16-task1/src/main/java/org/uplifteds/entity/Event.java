package org.uplifteds.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

public class Event {
    @Id
    private int id;

    private String eventName;
    private String place;
    private int capacity;
    private String date;
    private String create_date;
    private String update_date;

    //Add ticketPrice field to Event entity.
    private int ticketPrice;

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", place='" + place + '\'' +
                ", capacity=" + capacity +
                ", date='" + date + '\'' +
                ", create_date=" + create_date +
                ", update_date=" + update_date +
                ", ticketPrice=" + ticketPrice +
                '}';
    }

    public Event() {
    }

}
