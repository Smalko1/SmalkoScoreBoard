package com.smalko.scoreboard.controller;

import com.smalko.scoreboard.exception.AbsenceOfThisPlayer;
import com.smalko.scoreboard.match.model.dto.MatchesCreateDto;
import com.smalko.scoreboard.match.service.MatchesService;
import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import com.smalko.scoreboard.player.service.PlayerService;
import com.smalko.scoreboard.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.UUID;

public class FinishedMatchesPersistenceService {

    private static final OngoingMatchesService ongoingMatches = OngoingMatchesService.getInstance();
    private static final Logger log = LoggerFactory.getLogger(FinishedMatchesPersistenceService.class);

    private FinishedMatchesPersistenceService() {
    }

    public static void finishedMatches(UUID uuid) {

        var match = ongoingMatches.getMatch(uuid);
        log.info("get {}, in the {}, from its uuid", match, ongoingMatches);
        var playersOneId = saveInBDPlayers(match.getPlayersOne());
        var playersTwoId = saveInBDPlayers(match.getPlayersTwo());
        int winnerId = match.getPlayersOne() == match.getWinner() ? playersOneId : playersTwoId;
        log.info("Determining the winner's id {}", winnerId);

        saveMatch(playersOneId, playersTwoId, winnerId);
    }


    private static int saveInBDPlayers(PlayersCreateDto players) {
        int playersId;
        try (SessionFactory sessionFactory = HibernateUtil.sessionFactory()) {
            var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
            entityManager.getTransaction().begin();
            try {
                playersId = PlayerService.openPlayerService(entityManager)
                        .getPlayersForName(players.name(), entityManager)
                        .id();
            } catch (AbsenceOfThisPlayer a) {
                playersId = PlayerService.openPlayerService(entityManager).createPlayer(players);
            }
            log.info("Check if the player is in the database and return his id, " +
                     "if he is not, then save the player and return his id");

            entityManager.getTransaction().commit();
        }
        return playersId;
    }

    private static void saveMatch(int playersOneId, int playersTwoId, int winnerId) {
        try (SessionFactory sessionFactory = HibernateUtil.sessionFactory()) {
            var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
            entityManager.getTransaction().begin();
            MatchesService.openMatchesService(entityManager).createMatch(new MatchesCreateDto(playersOneId, playersTwoId, winnerId));
            entityManager.getTransaction().commit();
        }
        log.info("Save match and close session");
    }
}
