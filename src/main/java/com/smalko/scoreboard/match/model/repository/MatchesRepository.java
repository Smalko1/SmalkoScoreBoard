package com.smalko.scoreboard.match.model.repository;

import com.smalko.scoreboard.match.model.entity.Matches;
import com.smalko.scoreboard.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class MatchesRepository extends RepositoryUtil<Integer, Matches> {
    public MatchesRepository(EntityManager entityManager) {
        super(Matches.class, entityManager);
    }

    public List<Matches> findMatchesInPage(int offset, int limit, EntityManager entityManager) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Matches.class);
        var from = criteria.from(Matches.class);

        criteria.select(from);
        criteria.orderBy(cb.asc(from.get("id")));
        return entityManager.createQuery(criteria)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

    public List<Matches> findMatchesForPlayersId(int playerId, EntityManager entityManager) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Matches.class);
        var from = criteria.from(Matches.class);

        criteria.select(from)
                .where(cb.or(
                        cb.equal(from.get("playersOneId"), playerId),
                        cb.equal(from.get("playersTwoId"), playerId)
                ));

        return entityManager.createQuery(criteria)
                .getResultList();

    }

    public long getCountMatches(EntityManager entityManager) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Long.class);
        var from = criteria.from(Matches.class);
        criteria.select(cb.count(from));


        return entityManager.createQuery(criteria)
                .getSingleResult();
    }
}
