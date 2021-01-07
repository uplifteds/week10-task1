package org.uplifteds.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.uplifteds.DML_DDL.HibernateCRUDMethods;
import org.uplifteds.entity.Ticket;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TicketDAO implements DAO{
    private final SessionFactory factory;

    public TicketDAO(@NotNull final SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Ticket read(@NotNull final Integer primKey) {
        try (final Session session = factory.openSession()) {
            final Ticket result = session.get(Ticket.class, primKey);

            if (result != null){
                Hibernate.initialize(result.getEvent());
                Hibernate.initialize(result.getUser());
            }

            return result;
        }
    }

    @Override
    public void doUpdateJoinColumnInTable(String col) {
        try (final Session session = factory.openSession()) {

            EntityManager em = session.getEntityManagerFactory().createEntityManager();

            em.getTransaction().begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();

            HibernateCRUDMethods.doUpdateJoinColumnInTicketTable(em, cb, col);

            em.getTransaction().commit();
        }
    }

    public List<Ticket> findByCriteria(String category) {
        try (final Session session = factory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Ticket> cr = cb.createQuery(Ticket.class);
            Root<Ticket> root = cr.from(Ticket.class);

            cr.select(root).where(cb.equal(root.get("category"), category));
            Query<Ticket> query = session.createQuery(cr);
            List<Ticket> results = query.getResultList();

            return results;
        }
    }

}
