package org.uplifteds.entitygenerator;

import org.uplifteds.entity.Post;
import org.uplifteds.entity.User;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostGenerator {
    public final static int POSTS_PER_USER = 3;
    public static int postPrintStep = (POSTS_PER_USER * UserGenerator.userPrintStep);
    public static List<Post> listOfPosts = new CopyOnWriteArrayList<>();

    //> 300 000 likes in 2025) [800 likes per each of 375 Users]; 800 Posts (3 posts per User)


    public static List<Post> setListOfPosts (){
        for (User tempUser : UserGenerator.listOfUsers) {
            for (int i = 0; i < POSTS_PER_USER; i++) {
                Post postOftempUser = tempUser.writePost();
                listOfPosts.add(postOftempUser);
            }
        }
        printStep(listOfPosts);
        return listOfPosts;
    }

    private static void printStep (List<Post> listOfPosts){
        for (int i = 0; i < listOfPosts.size(); i+= postPrintStep) {
            System.out.println(listOfPosts.get(i).toString());
        }
    }

}
