package org.uplifteds.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.uplifteds.entity.User;

import javax.validation.constraints.NotNull;

public class UserDAO implements DAO{
    private final SessionFactory factory;

    public UserDAO(@NotNull final SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public User read(@NotNull final Integer primKey) {
        try (final Session session = factory.openSession()) {
            final User result = session.get(User.class, primKey);

            return result != null ? result : new User();
        }
    }

    @Override
    public void doUpdateJoinColumnInTable(String col) {

    }

}
