package com.smalko.scoreboard;

import com.smalko.scoreboard.controller.MatchesController;
import com.smalko.scoreboard.match.model.dto.MatchesReadDto;

public class main {
    public static void main(String[] args) {

        /*try (SessionFactory sessionFactory = HibernateUtil.sessionFactory()) {
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
         */
        var i = MatchesController.countMatch();
        System.err.println(i);

        var matchesReadDtos = MatchesController.printMatch(1);
        for (MatchesReadDto matchesReadDto : matchesReadDtos) {
            System.out.println(matchesReadDto);
        }



        System.out.println("Hello");
    }
}
