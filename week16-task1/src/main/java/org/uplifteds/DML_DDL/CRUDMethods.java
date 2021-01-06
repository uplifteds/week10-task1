package org.uplifteds.DML_DDL;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.uplifteds.entity.Event;
import org.uplifteds.entity.Ticket;
import org.uplifteds.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CRUDMethods {


    public static void doDeleteValuesInAllTables(Session session){
        List<String> l = new ArrayList<>();
        String userclass = User.class.getSimpleName();
        String ticketclass = Ticket.class.getSimpleName();
        String eventclass = Event.class.getSimpleName();
        l.add(ticketclass);
        l.add(userclass);
        l.add(eventclass);

        for (String temp : l) {
          String hql = String.format("DELETE from " + temp);
          Query query = session.createQuery(hql);
          query.executeUpdate();
        }
    }

    public static void doUpdateJoinColumnInTicketTable(EntityManager em, CriteriaBuilder cb, String col) {
       CriteriaUpdate<Ticket> criteria = cb.createCriteriaUpdate(Ticket.class);

       Root<Ticket> obj = criteria.from(Ticket.class);
       execCriteria(em, col, criteria, obj);
    }

    private static void execCriteria(EntityManager em, String col, CriteriaUpdate<?> criteria, Root<?> obj) {
        int firstid = 1;
        criteria.set(obj.get(col), firstid);
        em.createQuery(criteria).executeUpdate();
    }
}
