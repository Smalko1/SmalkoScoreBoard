package com.smalko.scoreboard.util;

import com.smalko.scoreboard.match.model.dto.MatchesCreateDto;
import com.smalko.scoreboard.match.model.entity.Matches;
import com.smalko.scoreboard.match.model.mapper.MatchesCreateMapper;
import com.smalko.scoreboard.match.model.repository.MatchesRepository;
import com.smalko.scoreboard.match.service.MatchesService;
import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import com.smalko.scoreboard.player.model.entity.Players;
import com.smalko.scoreboard.player.model.repository.PlayerRepository;
import com.smalko.scoreboard.player.service.PlayerService;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.Proxy;

@UtilityClass
public class HibernateUtil {


    static {
        createMatchesPlayers();
    }

    private static void createMatchesPlayers() {
        try (SessionFactory sessionFactory = HibernateUtil.sessionFactory()) {
            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
             session.beginTransaction();


            var playerRepository = new PlayerRepository(session);

            var playerService = new PlayerService(playerRepository);

            var matchesRepository = new MatchesRepository(session);
            var matchesCreateMapper = new MatchesCreateMapper(playerRepository);

            var matchesService = new MatchesService(matchesRepository, matchesCreateMapper);

            var players1 = new PlayersCreateDto("B. Gojo");
            var players2 = new PlayersCreateDto("T. Machac");

            var player1 = playerService.createPlayer(players1);
            var player2 = playerService.createPlayer(players2);

            var matchesCreateDto1 = new MatchesCreateDto(player1, player2, player1);
            matchesService.createMatch(matchesCreateDto1);

            var players3 = new PlayersCreateDto("C. Alcaraz");
            var players4 = new PlayersCreateDto("N. Djokovic");

            var player3 = playerService.createPlayer(players3);
            var player4 = playerService.createPlayer(players4);

            var matchesCreateDto2 = new MatchesCreateDto(player3, player4, player4);

            matchesService.createMatch(matchesCreateDto2);

            var players5 = new PlayersCreateDto("A. Zverev");
            var players6 = new PlayersCreateDto("F. Cabral");

            var player5 = playerService.createPlayer(players5);
            var player6 = playerService.createPlayer(players6);

            var matchesCreateDto3 = new MatchesCreateDto(player5, player6, player5);

            matchesService.createMatch(matchesCreateDto3);

            var matchesCreateDto4 = new MatchesCreateDto(player3, player6, player6);
            matchesService.createMatch(matchesCreateDto4);

            var matchesCreateDto5 = new MatchesCreateDto(player2, player4, player4);
            matchesService.createMatch(matchesCreateDto5);

            var matchesCreateDto6 = new MatchesCreateDto(player5, player1, player5);
            matchesService.createMatch(matchesCreateDto6);

            var matchesCreateDto7 = new MatchesCreateDto(player6, player1, player6);
            matchesService.createMatch(matchesCreateDto7);

            var matchesCreateDto8 = new MatchesCreateDto(player2, player5, player2);
            matchesService.createMatch(matchesCreateDto8);

            session.getTransaction().commit();
        }
    }

    public static SessionFactory sessionFactory() {
        var configuration = new Configuration();
        configuration.configure();

        configuration.addAnnotatedClass(Players.class);
        configuration.addAnnotatedClass(Matches.class);

        return configuration.buildSessionFactory();
    }
}
