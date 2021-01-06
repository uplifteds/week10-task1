package org.uplifteds.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.uplifteds.entity.Event;

import javax.validation.constraints.NotNull;

public class EventDAO implements DAO{
    private final SessionFactory factory;

    public EventDAO(@NotNull final SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Event read(@NotNull final Integer primKey) {
        try (final Session session = factory.openSession()) {
            final Event result = session.get(Event.class, primKey);
            return result != null ? result : new Event();
        }
    }

    @Override
    public void doUpdateJoinColumnInTable(String col) {

    }

}
