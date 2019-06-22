package com.ef.repository;

import com.ef.entity.LogEntry;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.ZonedDateTime;
import java.util.List;

@Singleton
public class LogEntryRepository {

    @Inject
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public List<String> listIpsExceedingRequestThreshold(ZonedDateTime startDate, ZonedDateTime endDate, Integer threshold) {
        return em.createQuery("SELECT l.ip " +
                "FROM LogEntry l " +
                "WHERE l.entryDate >= :startDate AND l.entryDate <= :endDate " +
                "GROUP BY l.ip " +
                "HAVING COUNT(l) > :threshold")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("threshold", Long.valueOf(threshold))
                .getResultList();
    }

    public void persistLogEntries(List<LogEntry> logEntries) {
        EntityTransaction tx = null;

        try {
            Session session = em.unwrap(Session.class);
            tx = session.beginTransaction();

            logEntries.forEach(em::persist);

            em.flush();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }

    }

}
