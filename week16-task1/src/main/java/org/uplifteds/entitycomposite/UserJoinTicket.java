package org.uplifteds.entitycomposite;

import java.sql.Date;

public class UserJoinTicket {
    private int age;
    private int userid;
    private String category;
    private Date month;
    private int count_ticket;

    public UserJoinTicket() {
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public void setCount_ticket(int count_ticket) {
        this.count_ticket = count_ticket;
    }

    @Override
    public String toString() {
        return "UserJoinTicket{" +
                "age=" + age +
                ", userid=" + userid +
                ", category='" + category + '\'' +
                ", month=" + month +
                ", count_ticket=" + count_ticket +
                '}';
    }
}
