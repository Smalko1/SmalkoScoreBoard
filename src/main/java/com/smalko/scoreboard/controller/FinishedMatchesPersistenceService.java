package com.smalko.scoreboard.controller;

import com.smalko.scoreboard.match.model.dto.MatchesCreateDto;
import com.smalko.scoreboard.match.service.MatchesService;
import com.smalko.scoreboard.player.model.dto.PlayerReadDto;
import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import com.smalko.scoreboard.player.service.PlayerService;
import com.smalko.scoreboard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;
import java.util.UUID;

public class FinishedMatchesPersistenceService {

    private static final OngoingMatchesService ongoingMatches = OngoingMatchesService.getInstance();
    private static UUID uuid = null;
    private static final CurrentMatch march = ongoingMatches.getMatch(uuid);

    public static void finishedMatches(UUID uuid) {
        setUuid(uuid);
        var playersOneId = saveInBDPlayers(march.getPlayersOne());
        var playersTwoId = saveInBDPlayers(march.getPlayersTwo());
        int winnerId = march.getPlayersOne() == march.getWinner() ? playersOneId : playersTwoId;

        saveMatch(playersOneId, playersTwoId, winnerId);
    }

    private static void saveMatch(int playersOneId, int playersTwoId, int winnerId) {
        try (SessionFactory sessionFactory = HibernateUtil.sessionFactory()) {
            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
            MatchesService.openMatchesService(session).createMatch(new MatchesCreateDto(playersOneId, playersTwoId, winnerId));
        }
    }


    private static int saveInBDPlayers(PlayersCreateDto players) {
        int playersId;
        try (SessionFactory sessionFactory = HibernateUtil.sessionFactory()) {
            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));

            playersId =  PlayerService.openPlayerService(session)
                    .getPlayersForName(players.name())
                    .map(PlayerReadDto::id)
                    .orElseGet(() -> PlayerService.openPlayerService(session).createPlayer(players));
        }
        return playersId;
    }


    private static void setUuid(UUID uuid) {
        FinishedMatchesPersistenceService.uuid = uuid;
    }
}
