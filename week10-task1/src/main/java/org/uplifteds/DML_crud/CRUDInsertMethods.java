package org.uplifteds.DML_crud;

import org.uplifteds.entity.Friendship;
import org.uplifteds.entity.Like;
import org.uplifteds.entity.User;
import org.uplifteds.entity.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CRUDInsertMethods {
    static String sameNumberOfColumnsToInsertInTable = "?, ?, ?, ?";
    public static void doInsertListOfUsers(Connection conn, String table_name, List<User> listOfUsers) throws SQLException {
        String sqlQuery = "insert into " + table_name + " values (" + sameNumberOfColumnsToInsertInTable + ")";

        PreparedStatement prepStmt = null;
        try{
            prepStmt = conn.prepareStatement(sqlQuery);

            for(User tempUsr : listOfUsers) {
                prepStmt.setInt(1, tempUsr.getId());
                prepStmt.setString(2, tempUsr.getName());
                prepStmt.setString(3, tempUsr.getSurname());
                prepStmt.setDate(4, tempUsr.getBirthdate());
                prepStmt.addBatch(); //BATCH 'INSERT INTO'
            }
            System.out.println("Writing Users to its Table ...");

            int[] affectedRecords = prepStmt.executeBatch(); // required for BATCH 'INSERT INTO'

        }finally {
                prepStmt.close();
        }
    }

    public static void doInsertListOfPosts(Connection conn, String table_name, List<Post> listOfSubj) throws SQLException {
        String sqlQuery = "insert into " + table_name + " values (" + sameNumberOfColumnsToInsertInTable + ")";

        PreparedStatement prepStmt = null;
        try{
            prepStmt = conn.prepareStatement(sqlQuery);

            for(Post tempPost : listOfSubj) {
                prepStmt.setInt(1, tempPost.getId());
                prepStmt.setInt(2, tempPost.getUserid());
                prepStmt.setString(3, tempPost.getText());
                prepStmt.setTimestamp(4, tempPost.getTs());
                prepStmt.addBatch(); //BATCH 'INSERT INTO'
            }
            System.out.println("Writing Posts to its Table ...");

            int[] affectedRecords = prepStmt.executeBatch(); // required for BATCH 'INSERT INTO'

        }finally {
            prepStmt.close();
        }
    }

    public static void doInsertListOfLikes(Connection conn, String table_name, List<Optional<Like>> listOfExamRes) throws SQLException {
        String sqlQuery = "insert into " + table_name + " values (" + sameNumberOfColumnsToInsertInTable + ")";

        PreparedStatement prepStmt = null;
        try{
            prepStmt = conn.prepareStatement(sqlQuery);

            for(Optional<Like> tempLike : listOfExamRes) {
                prepStmt.setInt(1, tempLike.get().getId());
                prepStmt.setInt(2, tempLike.get().getUserid());
                prepStmt.setInt(3, tempLike.get().getPostid());
                prepStmt.setTimestamp(4, tempLike.get().getLikeTs());
                prepStmt.addBatch(); //BATCH 'INSERT INTO'
            }
            System.out.println("Writing Likes to its Table ...\n");

            int[] affectedRecords = prepStmt.executeBatch(); // required for BATCH 'INSERT INTO'

        }finally {
            prepStmt.close();
        }
    }

    public static void doInsertListOfFriendships(Connection conn, String table_name,
                                                 List<Optional<Friendship>> listOfFriendships) throws SQLException {
        String sqlQuery = "insert into " + table_name + " values (" + sameNumberOfColumnsToInsertInTable + ")";

        PreparedStatement prepStmt = null;
        try{
            prepStmt = conn.prepareStatement(sqlQuery);

            for(Optional<Friendship> fr : listOfFriendships) {
                prepStmt.setInt(1, fr.get().getId());
                prepStmt.setInt(2, fr.get().getUserid1());
                prepStmt.setInt(3, fr.get().getUserid2());
                prepStmt.setTimestamp(4, fr.get().getFriendshipTs());
                prepStmt.addBatch(); //BATCH 'INSERT INTO'
            }
            System.out.println("Writing Friendships to its Table ...");

            int[] affectedRecords = prepStmt.executeBatch(); // required for BATCH 'INSERT INTO'

        }finally {
            prepStmt.close();
        }
    }
}
