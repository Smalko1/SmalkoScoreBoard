package com.smalko.scoreboard;

public class main {

    public static void main(String[] args) {
        /*var players = new PlayersCreateDto("Petro");
        try (SessionFactory sessionFactory = HibernateUtil.sessionFactory()) {
            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));

            var integer = PlayerService.openPlayerService(session)
                    .getPlayersForName(players.name())
                    .map(PlayerReadDto::id)
                    .orElseGet(() -> PlayerService.openPlayerService(session).createPlayer(players));
            System.out.println(integer);
        }

         */

        System.out.println(" \uD83C\uDFC6 ");

    }
}
