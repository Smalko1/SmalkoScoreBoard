package com.smalko.scoreboard.util;

import com.smalko.scoreboard.interceptor.TransactionInterceptor;
import com.smalko.scoreboard.match.model.dto.MatchesCreateDto;
import com.smalko.scoreboard.match.model.entity.Matches;
import com.smalko.scoreboard.match.model.mapper.MatchesCreateMapper;
import com.smalko.scoreboard.match.model.mapper.MatchesReadMapper;
import com.smalko.scoreboard.match.model.repository.MatchesRepository;
import com.smalko.scoreboard.match.service.MatchesService;
import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import com.smalko.scoreboard.player.model.entity.Players;
import com.smalko.scoreboard.player.model.mapper.PlayerCreateMapper;
import com.smalko.scoreboard.player.model.mapper.PlayerReadMapper;
import com.smalko.scoreboard.player.model.repository.PlayerRepository;
import com.smalko.scoreboard.player.service.PlayerService;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.Proxy;

@UtilityClass
public class HibernateUtil {


    //static {
        //createMatchesPlayers();
    //}

    public static Session getSession() {
        Session session = sessionFactory().openSession();
        return (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(),
                new Class[]{Session.class},
                (proxy, method, args) -> method.invoke(session, args)
        );
    }

    @SneakyThrows
    private static void createMatchesPlayers() {
        /*try (var sessionFactory = HibernateUtil.getSession()) {
            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
                    new Class[]{Session.class},
                    (o, method, objects) -> method.invoke(sessionFactory.getCurrentSession(), objects));

         */

        //session.beginTransaction();

        var session = getSession();

        var playerReadMapper = new PlayerReadMapper();
        var playerCreateMapper = new PlayerCreateMapper();
        var playerRepository = new PlayerRepository(session);

        var transactionInterceptor = new TransactionInterceptor(sessionFactory());

        PlayerService playerService = new ByteBuddy()
                .subclass(PlayerService.class)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(transactionInterceptor))
                .make()
                .load(PlayerService.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor(PlayerRepository.class, PlayerCreateMapper.class, PlayerReadMapper.class)
                .newInstance(playerRepository, playerCreateMapper, playerReadMapper);

        //var playerService = new PlayerService(playerRepository, playerCreateMapper, playerReadMapper);

        var matchesRepository = new MatchesRepository(session);
        var matchesCreateMapper = new MatchesCreateMapper(playerRepository);
        var matchesReadMapper = new MatchesReadMapper(playerReadMapper);

        MatchesService matchesService = new ByteBuddy()
                .subclass(MatchesService.class)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(transactionInterceptor))
                .make()
                .load(MatchesService.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor(MatchesRepository.class, MatchesCreateMapper.class, MatchesReadMapper.class)
                .newInstance(matchesRepository, matchesCreateMapper, matchesReadMapper);

        //var matchesService = new MatchesService(matchesRepository, matchesCreateMapper, matchesReadMapper);
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

        var match2 = matchesService.createMatch(matchesCreateDto2);
        //session.getTransaction().commit();

    }

    public static SessionFactory sessionFactory() {
        var configuration = new Configuration();
        configuration.configure();

        configuration.addAnnotatedClass(Players.class);
        configuration.addAnnotatedClass(Matches.class);

        return configuration.buildSessionFactory();
    }
}
