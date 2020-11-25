package org.uplifteds.entity;

import org.uplifteds.entitygenerator.RandomType;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class User {
    static int postCounter = 1;
    static int likeCounter = 1;
    static int friendshipCounter = 1;

    String himselfFriendReqStr = "Error: User can not befriend with himself";

    String defaultTsIn2025 = "2025-03-01 01:01:01";
    Timestamp postTimestamp = Timestamp.valueOf(defaultTsIn2025);
    Timestamp likeTimestamp = Timestamp.valueOf(defaultTsIn2025);

    List<Post> myPosts = new CopyOnWriteArrayList<>();



    Set<User> setOfFriendsForUser = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private int id;
    private String name;
    private String surname;
    private Date birthdate;

    public static String idFieldName;
    public static String nameFieldName;
    public static String surnameFieldName;
    public static String birthdateFieldName;

    private static String msg;

    public Set<User> getSetOfFriendsForUser() {
        return setOfFriendsForUser;
    }

    public static String getMsg() {
        return msg;
    }

    public static void setMsg(String msg2) {
        msg = msg2;
    }

    public  int getId() {
        return id;
    }

    public  void setId(int id2) {
        id = id2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }

    public static void getFieldNameReflection ()  {
        Field field = null;
        try {
            field = User.class.getDeclaredField("id");
            idFieldName = field.getName();
            field = User.class.getDeclaredField("name");
            nameFieldName = field.getName();
            field = User.class.getDeclaredField("surname");
            surnameFieldName = field.getName();
            field = User.class.getDeclaredField("birthdate");
            birthdateFieldName = field.getName();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public Post writePost(){
        String postText = "Body: a-" + RandomType.generRandString(8) + "-Z";
        Timestamp postTimestamp = RandomType.generRandTimestampWithin2025Year();
        this.postTimestamp = postTimestamp;

        int postid = postCounter;
        Post myPost = new Post(postid, getId(), postText, postTimestamp);
        postCounter++;

        myPosts.add(myPost);
        return myPost;
    }

    public Optional<Like> clickLike(Post postToBeLiked){
        int likeid = likeCounter;
        Like like = null;

        // if postTobeLiked is not member of myPosts List
        if (!myPosts.contains(postToBeLiked) ) {
            // if user is not already liked same post
            if (!postToBeLiked.getSetOfUsersThatLikedThisPost().contains(this)){
                Timestamp likeTimestamp = addOneDayToTimestamp(postTimestamp);
                this.likeTimestamp = likeTimestamp;

                like = new Like(likeid, getId(), postToBeLiked.getId(), likeTimestamp);
                likeCounter++;
            } else {
//                System.out.println("Error: Same user can't like same post 2 or more times");
            }
        } else {
//            System.out.println("Error: Can not like own post");
        }

        postToBeLiked.addToSetOfUsersThatLikedThisPost(this);
        Optional<Like> optionalLike = Optional.ofNullable(like);

        return optionalLike;
    }

    public void requestFrienship(User anotherUserTo) {
        if (!anotherUserTo.equals(this)){
            String req = "<Hi, let us befriend>";
            setMsg(req);
        } else {
//            System.out.println(himselfFriendReqStr);
        }
    }

    public Optional<Friendship> acceptFriendship (User anotherUserFrom){
        Friendship friendship = null;
        Integer friendshipid = null;
        String received = getMsg();
        if (!anotherUserFrom.equals(this)) {
            boolean isFriendReq = receiveMsg(anotherUserFrom, received);

            if (isFriendReq) {

                // add check that reverse-direction Friendship can not be duplicated
                setOfFriendsForUser.add(anotherUserFrom);

                boolean isAlreadyFriend = anotherUserFrom.getSetOfFriendsForUser().contains(this);

                if (!isAlreadyFriend) {
                    friendshipid = friendshipCounter;

                    Timestamp friendshipTimestamp = RandomType.generRandTimestampWithin2025Year();

                    friendship = new Friendship(friendshipid, this.getId(), anotherUserFrom.getId(), friendshipTimestamp);
                    friendshipCounter++;
                } else {
//                    System.out.println("Friendship is already established between Users");
                }
            } else {
                System.out.println("Info: Received message doesn't contain valid friend-request. Friendship is not established");
            }
        } else {
//            System.out.println(himselfFriendReqStr);
        }
        Optional<Friendship> optionalFriendship = Optional.ofNullable(friendship);
        return optionalFriendship;
    }

    private boolean receiveMsg(User anotherUserFrom, String received) {
        if (received.contains("let us befriend")) {
            return true;
        } else {
            return false;
        }
    }

    private Timestamp addOneDayToTimestamp(Timestamp ts) {
        final int ONE_DAY = 1;
        Calendar cal = Calendar.getInstance();
        cal.setTime(ts);
        // like is clicked on next day after creating a post
        cal.add(Calendar.DAY_OF_WEEK, ONE_DAY);
        Timestamp postTimestampPlusOneDay = new Timestamp(cal.getTime().getTime());
        return postTimestampPlusOneDay;
    }

}
