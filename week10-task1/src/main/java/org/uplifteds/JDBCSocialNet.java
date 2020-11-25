package org.uplifteds;

import org.uplifteds.DML_crud.CRUDInsertMethods;
import org.uplifteds.DML_crud.CRUDMethods;
import org.uplifteds.entity.Friendship;
import org.uplifteds.entity.Like;
import org.uplifteds.entity.Post;
import org.uplifteds.entity.User;
import org.uplifteds.entitygenerator.FriendshipGenerator;
import org.uplifteds.entitygenerator.LikeGenerator;
import org.uplifteds.entitygenerator.PostGenerator;
import org.uplifteds.entitygenerator.UserGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class JDBCSocialNet {
    public static List<String> listOfTables = new ArrayList<>();

    public static void main( String[] args ) throws IOException, SQLException {
        try (FileInputStream fis = new FileInputStream ("src/main/resources/prefDB.properties")){
            Properties props = new Properties ();
            props.load (fis); //connection parameters are stored in properties file
            String url      = (String) props.get("jdbc.url");
            String username = (String) props.get("jdbc.username");
            String password = (String) props.get("jdbc.password");

            listOfTables.add("Likes"); // has foreign key, therefore added 1st (cascade)
            listOfTables.add("Friendships");
            listOfTables.add("Posts");
            listOfTables.add("Users"); // doesn't have foreign key, therefore added last
            User.getFieldNameReflection();
            Post.getFieldNameReflection();
            Like.getFieldNameReflection();
            Friendship.getFieldNameReflection();

            List<User> listOfUsers = UserGenerator.setListOfStudents();
            System.out.println(" # Users wrote Posts");
            List<Post> listOfPosts = PostGenerator.setListOfPosts();
            System.out.println(" # Friendshiping began");
            List<Optional<Friendship>> listOfFriendships = FriendshipGenerator.setListOfFriendships();
            System.out.println(" # Wait for Generating 300 000+ Likes ...");
            List<Optional<Like>> listOfLikes = LikeGenerator.setListOfLikes();


            try (Connection conn = DriverManager.getConnection(url, username, password);
                Statement stmt = conn.createStatement()) {

                CRUDMethods.doDeleteValuesInAllTables(stmt); // clean values before inserting

//Populate tables with data which are make sense (> 1 000 users, > 70 000 friendships (375 Users made friends with all each other),
// > 300 000 likes in 2025) [800 likes per each of 375 Users]; 800 Posts (3 posts per User)

                // doesn't have foreign key, therefore filled 1st (cascade)
                CRUDInsertMethods.doInsertListOfUsers      (conn, listOfTables.get(3), listOfUsers);

                CRUDInsertMethods.doInsertListOfPosts      (conn, listOfTables.get(2), listOfPosts);
                CRUDInsertMethods.doInsertListOfFriendships(conn, listOfTables.get(1), listOfFriendships);
                CRUDInsertMethods.doInsertListOfLikes      (conn, listOfTables.get(0), listOfLikes);

            }

//Program should print out all names (only distinct) of users who has more than 100 friends and 100 likes in March 2025.
// Implement all actions (table creation, insert data and reading) with JDBC.

//*You could prepare dictionaries (maps) in memory (with usernames for example) or data in files to generate data for the populating process.
        }
    }
}
