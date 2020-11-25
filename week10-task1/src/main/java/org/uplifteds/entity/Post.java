package org.uplifteds.entity;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class Post {
    Set<User> setOfUsersThatLikedThisPost = new HashSet<>();

    private int id;
    private int userid;
    private String text;
    private Timestamp ts;

    public static String idFieldName;
    public static String useridFieldName;
    public static String textFieldName;
    public static String tsFieldName;

    public Set<User> getSetOfUsersThatLikedThisPost() {
        return setOfUsersThatLikedThisPost;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getText() {
        return text;
    }

    public Post(int id2, int userid, String text, Timestamp ts) {
        this.id = id2;
        this.userid = userid;
        this.text = text;
        this.ts = ts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id2) {
        id = id2;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    public Post() {
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userid=" + userid +
                ", text='" + text + '\'' +
                ", ts=" + ts +
                '}';
    }

    public static void getFieldNameReflection()  {
        Field field = null;
        try {
            field = Post.class.getDeclaredField("id");
            idFieldName = field.getName();
            field = Post.class.getDeclaredField("userid");
            useridFieldName = field.getName();
            field = Post.class.getDeclaredField("text");
            textFieldName = field.getName();
            field = Post.class.getDeclaredField("ts");
            tsFieldName = field.getName();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void addToSetOfUsersThatLikedThisPost(User user) {
        setOfUsersThatLikedThisPost.add(user);
    }
}
