package com.smalko.scoreboard.controller;

import com.smalko.scoreboard.exception.AbsenceOfThisPlayer;
import com.smalko.scoreboard.match.model.dto.MatchesReadDto;
import com.smalko.scoreboard.match.service.MatchesService;
import com.smalko.scoreboard.player.service.PlayerService;
import com.smalko.scoreboard.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchesController {
    private static final Logger log = LoggerFactory.getLogger(MatchesController.class);

    public static long countMatch() {
        long count;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        log.info("crate {} of get count matches", entityManager);
        entityManager.getTransaction().begin();

        count = MatchesService.openMatchesService(entityManager).getCountMatch();
        entityManager.getTransaction().commit();

        return count;
    }

    public static List<MatchesReadDto> printMatch(int page) {
        List<MatchesReadDto> matches;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        log.info("crate {} of prints matches", entityManager);

        entityManager.getTransaction().begin();
        matches = MatchesService.openMatchesService(entityManager).findMatchesInPage(page);
        log.info("Taking the {} list on this page", matches);
        entityManager.getTransaction().commit();

        return matches;
    }

    public static List<MatchesReadDto> printMatchForPlayer(String searchPlayer) throws AbsenceOfThisPlayer {

        List<MatchesReadDto> matches;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        try {
            entityManager.getTransaction().begin();

            var playersForName = PlayerService.openPlayerService(entityManager)
                    .getPlayersForName(searchPlayer);
            log.info("Taking players in the search name");

            matches = MatchesService.openMatchesService(entityManager)
                    .getMatchesForPlayersId(playersForName.id());
            log.info("Taking the {}, with this player {}", matches, playersForName.name());
            entityManager.getTransaction().commit();
            return matches;
        } catch (AbsenceOfThisPlayer e) {
            entityManager.getTransaction().commit();
            return List.of();
        }
    }
}
