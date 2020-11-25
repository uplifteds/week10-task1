package org.uplifteds.entitygenerator;

import org.uplifteds.entity.Friendship;
import org.uplifteds.entity.Like;
import org.uplifteds.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class FriendshipGenerator {
    public static List<Optional<Friendship>> listOfFriendships = new CopyOnWriteArrayList<>();

    //> 70 000 friendships (375 Users made friends with all each other)
    static int offset = 120;
    final static int FRIENDS_WITH_EACH_OTHER = (UserGenerator.listOfUsers.size() - offset);

    static int likePrintStep = UserGenerator.userPrintStep * offset;

    public static List<Optional<Friendship>> setListOfFriendships() {
        for (User userTemp1 : UserGenerator.listOfUsers) {
            for (int i = 0; i < FRIENDS_WITH_EACH_OTHER; i++) {

                User userTemp2 = getRandUser(UserGenerator.listOfUsers);

                userTemp1.requestFrienship(userTemp2);
                Optional<Friendship> tempFriendship = userTemp2.acceptFriendship(userTemp1);
                if (tempFriendship.isPresent()) {
                    listOfFriendships.add(tempFriendship);
                }
            }
        }
        printStep(listOfFriendships);
        return listOfFriendships;
    }

    private static void printStep (List<Optional<Friendship>> listOfFriendships){
        for (int i = 0; i < listOfFriendships.size(); i+= likePrintStep) {
            System.out.println(listOfFriendships.get(i).toString());
        }
    }

    private static User getRandUser(List<User> listOfUsers) {
        int index = new Random().nextInt(listOfUsers.size());
        User randomItem = listOfUsers.get(index);
        return randomItem;
    }
}
