package com.smalko.scoreboard;

import com.smalko.scoreboard.match.model.dto.MatchesCreateDto;
import com.smalko.scoreboard.match.model.mapper.MatchesCreateMapper;
import com.smalko.scoreboard.match.model.mapper.MatchesReadMapper;
import com.smalko.scoreboard.match.model.repository.MatchesRepository;
import com.smalko.scoreboard.match.service.MatchesService;
import com.smalko.scoreboard.player.model.mapper.PlayerCreateMapper;
import com.smalko.scoreboard.player.model.mapper.PlayerReadMapper;
import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import com.smalko.scoreboard.player.model.repository.PlayerRepository;
import com.smalko.scoreboard.player.service.PlayerService;
import com.smalko.scoreboard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;

public class main {

    public static void main(String[] args) {
        try (var sessionFactory = HibernateUtil.getSession()) {
            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
                    new Class[]{Session.class},
                    (o, method, objects) -> method.invoke(sessionFactory.getCurrentSession(), objects));

            session.beginTransaction();
            var playerReadMapper = new PlayerReadMapper();
            var playerCreateMapper = new PlayerCreateMapper();
            var playerRepository = new PlayerRepository(session);

            var playerService = new PlayerService(playerRepository, playerCreateMapper, playerReadMapper);

            var matchesRepository = new MatchesRepository(session);
            var matchesCreateMapper = new MatchesCreateMapper(playerRepository);
            var matchesReadMapper = new MatchesReadMapper(playerReadMapper);

            var matchesService = new MatchesService(matchesRepository, matchesCreateMapper, matchesReadMapper);
            var players1 = new PlayersCreateDto("B. Gojo");
            var players2 = new PlayersCreateDto("T. Machac");

            var player1 = playerService.createPlayer(players1);
            var player2 = playerService.createPlayer(players2);

            var matchesCreateDto1 = new MatchesCreateDto(player1, player2, player1);
            var match1 = matchesService.createMatch(matchesCreateDto1);

            var players3 = new PlayersCreateDto("C. Alcaraz");
            var players4 = new PlayersCreateDto("N. Djokovic");

            var player3 = playerService.createPlayer(players3);
            var player4 = playerService.createPlayer(players4);

            var matchesCreateDto2 = new MatchesCreateDto(player3, player4, player4);
            playerService.getPlayersForId(2).ifPresent(System.out::println);


            matchesService.findById(1).ifPresent(System.out::println);

            int s = 1;
            session.getTransaction().commit();
        }
    }
}
