package com.ef;

import com.google.inject.AbstractModule;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.Properties;

public class AccessLogParserModule extends AbstractModule {

    public static final String HIBERNATE_PROPERTIES_FILE = "hibernate.properties";
    public static final String PERSISTENCE_UNIT_NAME = "persistence-unit";

    @Override
    protected void configure() {
//        bind(LogEntryService.class).to(LogEntryService.class);
//        bind(LogEntryRepository.class).to(LogEntryRepository.class);
//        bind(LockedIpService.class).to(LockedIpService.class);
//        bind(LockedIpRepository.class).to(LockedIpRepository.class);

        EntityManager em;
        try {
            em = createEntityManager();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize entity manager needed for database access", e);
        }

        bind(EntityManager.class).toInstance(em);
    }

    EntityManager createEntityManager() throws IOException {
        Properties hibernateProperties = new Properties();
        hibernateProperties.load(Parser.class.getClassLoader().getResourceAsStream(HIBERNATE_PROPERTIES_FILE));
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, hibernateProperties);
        return emf.createEntityManager();
    }
}
