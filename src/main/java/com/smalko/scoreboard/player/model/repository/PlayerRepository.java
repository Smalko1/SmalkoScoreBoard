package com.smalko.scoreboard.player.model.repository;

import com.smalko.scoreboard.player.model.entity.Players;
import com.smalko.scoreboard.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class PlayerRepository extends RepositoryUtil<Integer, Players> {


    public PlayerRepository(EntityManager entityManager) {
        super(Players.class, entityManager);
    }

    public Optional<Players> findByName(String name, EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(Players.class);
        var from = criteria.from(Players.class);
        criteria.select(from).where(criteriaBuilder.equal(from.get("name"), name));
        return entityManager.createQuery(criteria)
                .getResultStream()
                .findFirst();
    }
}
