package com.smalko.scoreboard.util;

import com.smalko.scoreboard.match.model.entity.Matches;
import com.smalko.scoreboard.player.model.entity.Players;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {
    private SessionFactory sessionFactory;

    static {
        createSessionFactory();
    }

    private static void createSessionFactory() {
        sessionFactory = sessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static SessionFactory sessionFactory() {
        var configuration = new Configuration();
        configuration.configure();

        configuration.addAnnotatedClass(Players.class);
        configuration.addAnnotatedClass(Matches.class);

        return configuration.buildSessionFactory();
    }
}
