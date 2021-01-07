package org.uplifteds.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket {
    private int id;
    private String place;
    private String create_date;
    private int price; // $
    private List<String> cinema_phones;
    private String cinema_phone;
    private List<String> cinema_facilities;
    private String cinema_facility;

    private String category;
    private String cinema_name;
    private String cinema_address;
    private Timestamp purchase_date;
    private String update_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="eventid", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userid", nullable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCinema_name() {
        return cinema_name;
    }

    public void setCinema_name(String cinema_name) {
        this.cinema_name = cinema_name;
    }

    public String getCinema_address() {
        return cinema_address;
    }

    public void setCinema_address(String cinema_address) {
        this.cinema_address = cinema_address;
    }

    public Timestamp getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Timestamp purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public List<String> getCinema_facilities() {
        return cinema_facilities;
    }

    public void setCinema_facilities(List<String> cinema_facilities) {
        this.cinema_facilities = cinema_facilities;
    }

    public String getCinema_facility() {
        return cinema_facility;
    }

    public void setCinema_facility(String cinema_facility) {
        this.cinema_facility = cinema_facility;
    }

    public String getCinema_phone() {
        return cinema_phone;
    }

    public void setCinema_phone(String cinema_phone) {
        this.cinema_phone = cinema_phone;
    }

    public List<String> getCinema_phones() {
        return cinema_phones;
    }

    public void setCinema_phones(List<String> cinema_phones) {
        this.cinema_phones = cinema_phones;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ticket(int id, String category, String place, String cinema_name, String cinema_address, int price,
                  String cinema_phone, String cinema_facility, Timestamp purchase_date, String create_date,
                  String update_date) {
        this.id = id;
        this.category = category;
        this.place = place;
        this.cinema_name = cinema_name;
        this.cinema_address = cinema_address;
        this.price = price;
        this.cinema_phone = cinema_phone;
        this.cinema_facility = cinema_facility;
        this.purchase_date = purchase_date;
        this.create_date = create_date;
        this.update_date = update_date;
    }

    public Ticket() {
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", place='" + place + '\'' +
                ", create_date='" + create_date + '\'' +
                ", price=" + price +
                ", cinema_phone='" + cinema_phone + '\'' +
                ", cinema_facility='" + cinema_facility + '\'' +
                ", category='" + category + '\'' +
                ", cinema_name='" + cinema_name + '\'' +
                ", cinema_address='" + cinema_address + '\'' +
                ", purchase_date=" + purchase_date +
                ", update_date='" + update_date + '\'' +
                ", event=" + event +
                ", user=" + user +
                '}';
    }
}
