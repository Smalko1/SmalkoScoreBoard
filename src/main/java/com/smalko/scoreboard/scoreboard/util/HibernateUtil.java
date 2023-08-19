package com.smalko.scoreboard.scoreboard.util;

import com.smalko.scoreboard.scoreboard.entity.Matches;
import com.smalko.scoreboard.scoreboard.entity.Players;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory getSession(){
        var configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(Players.class);
        configuration.addAnnotatedClass(Matches.class);

        return configuration.buildSessionFactory();
    }
}
