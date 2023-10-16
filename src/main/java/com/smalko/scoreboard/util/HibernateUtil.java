package com.smalko.scoreboard.util;

import com.smalko.scoreboard.match.model.dto.MatchesCreateDto;
import com.smalko.scoreboard.match.model.entity.Matches;
import com.smalko.scoreboard.match.service.MatchesService;
import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import com.smalko.scoreboard.player.model.entity.Players;
import com.smalko.scoreboard.player.service.PlayerService;
import jakarta.persistence.EntityManager;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.Proxy;

@UtilityClass
public class HibernateUtil {
    private SessionFactory sessionFactory;

    static {
        createSessionFactory();
        databaseCompletion();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static void createSessionFactory() {
        var configuration = new Configuration();
        configuration.configure();

        configuration.addAnnotatedClass(Players.class);
        configuration.addAnnotatedClass(Matches.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    private static void databaseCompletion() {
        sessionFactory = HibernateUtil.getSessionFactory();
        var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        entityManager.getTransaction().begin();

        var Djokovic = PlayerService.openPlayerService(entityManager).createPlayer(new PlayersCreateDto("N. Djokovic"));
        var Alcaraz = PlayerService.openPlayerService(entityManager).createPlayer(new PlayersCreateDto("C. Alcaraz"));
        var Medvedev = PlayerService.openPlayerService(entityManager).createPlayer(new PlayersCreateDto("D. Medvedev"));
        var Sinner = PlayerService.openPlayerService(entityManager).createPlayer(new PlayersCreateDto("J. Sinner"));
        var Rune = PlayerService.openPlayerService(entityManager).createPlayer(new PlayersCreateDto("H. Rune"));
        var Rublev = PlayerService.openPlayerService(entityManager).createPlayer(new PlayersCreateDto("A. Rublev"));
        var Fritz = PlayerService.openPlayerService(entityManager).createPlayer(new PlayersCreateDto("T. Fritz"));
        var Ruud = PlayerService.openPlayerService(entityManager).createPlayer(new PlayersCreateDto("C. Ruud"));

        MatchesService.openMatchesService(entityManager).createMatch(new MatchesCreateDto(Djokovic, Alcaraz, Djokovic));
        MatchesService.openMatchesService(entityManager).createMatch(new MatchesCreateDto(Medvedev, Sinner, Sinner));
        MatchesService.openMatchesService(entityManager).createMatch(new MatchesCreateDto(Rune, Rublev, Rune));
        MatchesService.openMatchesService(entityManager).createMatch(new MatchesCreateDto(Fritz, Ruud, Ruud));

        MatchesService.openMatchesService(entityManager).createMatch(new MatchesCreateDto(Djokovic, Sinner, Djokovic));
        MatchesService.openMatchesService(entityManager).createMatch(new MatchesCreateDto(Rune, Ruud, Rune));
        MatchesService.openMatchesService(entityManager).createMatch(new MatchesCreateDto(Sinner, Ruud, Sinner));

        MatchesService.openMatchesService(entityManager).createMatch(new MatchesCreateDto(Rune, Djokovic, Djokovic));

        MatchesService.openMatchesService(entityManager).createMatch(new MatchesCreateDto(Medvedev, Rublev, Rublev));
        MatchesService.openMatchesService(entityManager).createMatch(new MatchesCreateDto(Fritz, Alcaraz, Alcaraz));
        entityManager.getTransaction().commit();
    }
}
