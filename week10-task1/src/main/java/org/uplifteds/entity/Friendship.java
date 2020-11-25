package org.uplifteds.entity;

import java.lang.reflect.Field;
import java.sql.Timestamp;

public class Friendship {
    private int id;
    private int userid1;
    private int userid2;
    private Timestamp friendshipTs;

    public static String idFieldName;
    public static String userid1FieldName;
    public static String userid2FieldName;
    public static String friendshipTsFieldName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid1() {
        return userid1;
    }

    public void setUserid1(int userid1) {
        this.userid1 = userid1;
    }

    public int getUserid2() {
        return userid2;
    }

    public void setUserid2(int userid2) {
        this.userid2 = userid2;
    }

    public Timestamp getFriendshipTs() {
        return friendshipTs;
    }

    public void setFriendshipTs(Timestamp friendshipTs) {
        this.friendshipTs = friendshipTs;
    }

    public Friendship(int id, int userid1, int userid2, Timestamp friendshipTs) {
        this.id = id;
        this.userid1 = userid1;
        this.userid2 = userid2;
        this.friendshipTs = friendshipTs;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + id +
                ", userid1=" + userid1 +
                ", userid2=" + userid2 +
                ", friendshipTs=" + friendshipTs +
                '}';
    }

    public static void getFieldNameReflection()  {
        Field field = null;
        try {
            field = Friendship.class.getDeclaredField("id");
            idFieldName = field.getName();
            field = Friendship.class.getDeclaredField("userid1");
            userid1FieldName = field.getName();
            field = Friendship.class.getDeclaredField("userid2");
            userid2FieldName = field.getName();
            field = Friendship.class.getDeclaredField("friendshipTs");
            friendshipTsFieldName = field.getName();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
