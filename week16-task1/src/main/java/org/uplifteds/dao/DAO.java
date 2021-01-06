package org.uplifteds.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.uplifteds.AdvancedDBBookingTicketLauncher;
import org.uplifteds.DML_DDL.CRUDMethods;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.sql.SQLException;

public interface DAO<Entity> {
    SessionFactory factory = AdvancedDBBookingTicketLauncher.sessionFactoryGlobal;

    //Design a new DML function, which is responsible for creation n of dummy records in all tables
    // from step 1, using the plpgsql language.

    default void create(@NotNull final Object obj) throws IOException, SQLException {
        try (final Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(obj);
            session.getTransaction().commit();
         }
    }

    Entity read(Integer key);

    default void update(@NotNull final Object obj) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(obj);
            session.getTransaction().commit();
        }
    }

    default void delete(@NotNull final Object obj) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(obj);
            session.getTransaction().commit();
        }
    }

    default void cleanAllTables(){
        try (final Session session = factory.openSession()){
            session.beginTransaction();
            CRUDMethods.doDeleteValuesInAllTables(session);
            session.getTransaction().commit();
        }
    }

    void doUpdateJoinColumnInTable(String col);

}