package com.smalko.scoreboard.match.model.repository;

import com.smalko.scoreboard.match.model.entity.Matches;
import com.smalko.scoreboard.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;

import java.util.List;

public class MatchesRepository extends RepositoryUtil<Integer, Matches> {
    public MatchesRepository(EntityManager entityManager) {
        super(Matches.class, entityManager);
    }

    public List<Matches> findMatchesInPage(int offset, int limit, Session session) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Matches.class);
        var from = criteria.from(Matches.class);

        criteria.select(from)
                .offset(offset)
                .where(cb.equal(from.get("id"), limit));

        return session.createQuery(criteria)
                .list();
    }

    public List<Matches> findMatchesForPlayersId(int playerId, Session session) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Matches.class);
        var from = criteria.from(Matches.class);

        criteria.select(from)
                .where(cb.or(
                        cb.equal(from.get("playersOneId"), playerId),
                        cb.equal(from.get("playersTwoId"), playerId)
                ));

        return session.createQuery(criteria)
                .list();
    }

    public long getCountMatches(Session session) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Long.class);
        var from = criteria.from(Matches.class);
        criteria.select(cb.count(from));


        return session.createQuery(criteria).getSingleResult();
    }
}
