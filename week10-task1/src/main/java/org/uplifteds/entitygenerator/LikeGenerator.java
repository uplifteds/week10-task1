package org.uplifteds.entitygenerator;

import org.uplifteds.entity.Like;
import org.uplifteds.entity.Post;
import org.uplifteds.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class LikeGenerator {
    public static List<Optional<Like>> listOfLikes = new CopyOnWriteArrayList<>();

    // > 300 000 likes in 2025) [800+ likes per each of 375 Users];
    final static int LIKES_PER_USER = 1450;

    static int likePrintStep = UserGenerator.userPrintStep * LIKES_PER_USER * PostGenerator.POSTS_PER_USER;

    public static List<Optional<Like>> setListOfLikes() {
        for (User tempUser : UserGenerator.listOfUsers) {
            for (int i = 0; i < LIKES_PER_USER; i++) {

                Post postToBeLiked = getRandPost(PostGenerator.listOfPosts);

                Optional<Like> likeOftempUser = tempUser.clickLike(postToBeLiked);
                if (likeOftempUser.isPresent()) {
                    listOfLikes.add(likeOftempUser);
                }
            }
        }
        printStep(listOfLikes);
        return listOfLikes;
    }

    private static void printStep (List<Optional<Like>> listOfLikes){
        for (int i = 0; i < listOfLikes.size(); i+= likePrintStep) {
            System.out.println(listOfLikes.get(i).toString());
        }
    }

    private static Post getRandPost(List<Post> listOfPosts) {
        int index = ThreadLocalRandom.current().nextInt(listOfPosts.size());
        Post randomItem = listOfPosts.get(index);
        return randomItem;
    }
}
