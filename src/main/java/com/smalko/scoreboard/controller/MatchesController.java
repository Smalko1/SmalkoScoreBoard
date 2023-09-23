package com.smalko.scoreboard.controller;

import com.smalko.scoreboard.exception.AbsenceOfThisPlayer;
import com.smalko.scoreboard.match.model.dto.MatchesReadDto;
import com.smalko.scoreboard.match.service.MatchesService;
import com.smalko.scoreboard.player.service.PlayerService;
import com.smalko.scoreboard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.List;

public class MatchesController {
    private static final MatchesController INSTANCE = new MatchesController();
    private static final Logger log = LoggerFactory.getLogger(MatchesController.class);
    private MatchesController() {
    }

    public List<MatchesReadDto> printMatch(int page) {
        List<MatchesReadDto> matches;
        try (SessionFactory sessionFactory = HibernateUtil.sessionFactory()) {
            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
            log.info("crate {} of prints matches", session);
            session.beginTransaction();
            matches = MatchesService.openMatchesService(session).findMatchesInPage(page, session);
            log.info("Taking the {} list on this page", matches);
            session.getTransaction().commit();
        }
        return matches;
    }

    public List<MatchesReadDto> printMatchForPlayer(String searchPlayer) throws AbsenceOfThisPlayer {
        List<MatchesReadDto> matches;
        try (SessionFactory sessionFactory = HibernateUtil.sessionFactory()) {
            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
            session.beginTransaction();
            var playersForName = PlayerService.openPlayerService(session)
                    .getPlayersForName(searchPlayer, session);
            log.info("Taking players in the search name");
            if (playersForName.isEmpty()){
                throw new AbsenceOfThisPlayer("This player is missing from the database");
            }else {
                matches = MatchesService.openMatchesService(session)
                        .getMatchesForPlayersId(playersForName.get().id(), session);
                log.info("Taking the {}, with this player {}", matches, playersForName.get().name());
            }
            session.getTransaction().commit();
        }
        return matches;
    }

    public static MatchesController getInstance() {
        return INSTANCE;
    }
}
