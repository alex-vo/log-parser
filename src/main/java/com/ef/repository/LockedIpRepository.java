package com.ef.repository;

import com.ef.entity.LockedIp;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@Singleton
public class LockedIpRepository {

    @Inject
    private EntityManager em;

    public void persistLockedIps(List<LockedIp> lockedIps) {
        EntityTransaction tx = null;

        try {
            Session session = em.unwrap(Session.class);
            tx = session.beginTransaction();

            lockedIps.forEach(em::persist);

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
