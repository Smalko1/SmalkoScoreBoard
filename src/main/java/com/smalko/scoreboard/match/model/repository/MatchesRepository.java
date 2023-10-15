package com.smalko.scoreboard.match.model.repository;

import com.smalko.scoreboard.match.model.entity.Matches;
import com.smalko.scoreboard.player.model.entity.Players;
import com.smalko.scoreboard.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class MatchesRepository extends RepositoryUtil<Integer, Matches> {
    private final EntityManager entityManager;

    public MatchesRepository(Class<Matches> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
        this.entityManager = entityManager;
    }
    public List<Matches> findMatchesInPage(int offset, int limit) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Matches.class);
        var from = criteria.from(Matches.class);

        criteria.select(from);
        return entityManager.createQuery(criteria)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Matches> findMatchesForPlayersId(int playerId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Matches> criteriaQuery = cb.createQuery(Matches.class);
        Root<Matches> from = criteriaQuery.from(Matches.class);

        Players player = entityManager.find(Players.class, playerId);

        criteriaQuery.select(from)
                .where(cb.or(
                        cb.equal(from.get("playerOneId"), player),
                        cb.equal(from.get("playerTwoId"), player)
                ));

        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    public long getCountMatches() {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Long.class);
        var from = criteria.from(Matches.class);
        criteria.select(cb.count(from));


        return entityManager.createQuery(criteria)
                .getSingleResult();
    }
}
