package org.uplifteds.entity;

import java.lang.reflect.Field;
import java.sql.Timestamp;

public class Like {
    private int id;
    private int userid;
    private int postid;
    private Timestamp likeTs;

    public static String idFieldName;
    public static String useridFieldName;
    public static String postidFieldName;
    public static String likeTsFieldName;

    public int getId() {
        return id;
    }

    public void setId(int id2) {
        id = id2;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public Like(int id, int userid, int postid, Timestamp likeTs) {
        this.id = id;
        this.userid = userid;
        this.postid = postid;
        this.likeTs = likeTs;
    }

    public Timestamp getLikeTs() {
        return likeTs;
    }

    public void setLikeTs(Timestamp likeTs) {
        this.likeTs = likeTs;
    }

    public Like() {
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", userid=" + userid +
                ", postid=" + postid +
                ", likeTs=" + likeTs +
                '}';
    }

    public static void getFieldNameReflection()  {
        Field field = null;
        try {
            field = Like.class.getDeclaredField("id");
            idFieldName = field.getName();
            field = Like.class.getDeclaredField("userid");
            useridFieldName = field.getName();
            field = Like.class.getDeclaredField("postid");
            postidFieldName = field.getName();
            field = Like.class.getDeclaredField("likeTs");
            likeTsFieldName = field.getName();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
