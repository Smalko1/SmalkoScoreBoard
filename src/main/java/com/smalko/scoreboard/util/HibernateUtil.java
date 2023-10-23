package com.smalko.scoreboard.util;

import com.smalko.scoreboard.match.model.entity.Matches;
import com.smalko.scoreboard.player.model.entity.Players;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private static SessionFactory createSessionFactory() {
        try {
            var configuration = new Configuration();

            configuration.configure();

            configuration.addAnnotatedClass(Players.class);
            configuration.addAnnotatedClass(Matches.class);

            return sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {

            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }


    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }
        return sessionFactory;

    }
}
