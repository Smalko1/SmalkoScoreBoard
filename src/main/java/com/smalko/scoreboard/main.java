package com.smalko.scoreboard;

import com.smalko.scoreboard.player.model.dto.PlayerReadDto;
import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import com.smalko.scoreboard.player.service.PlayerService;
import com.smalko.scoreboard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;

public class main {

    public static void main(String[] args) {
        var players = new PlayersCreateDto("Petro");
        try (SessionFactory sessionFactory = HibernateUtil.sessionFactory()) {
            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));

            var integer = PlayerService.openPlayerService(session)
                    .getPlayersForName(players.name())
                    .map(PlayerReadDto::id)
                    .orElseGet(() -> PlayerService.openPlayerService(session).createPlayer(players));
            System.out.println(integer);
        }


    }
}
