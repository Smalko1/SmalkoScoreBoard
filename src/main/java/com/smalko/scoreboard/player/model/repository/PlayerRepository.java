package com.smalko.scoreboard.player.model.repository;

import com.smalko.scoreboard.exception.AbsenceOfThisPlayer;
import com.smalko.scoreboard.player.model.entity.Players;
import com.smalko.scoreboard.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class PlayerRepository extends RepositoryUtil<Integer, Players> {
    private final EntityManager entityManager;

    public PlayerRepository(EntityManager entityManager) {
        super(Players.class, entityManager);
        this.entityManager = entityManager;
    }

    public Players findByName(String name) throws AbsenceOfThisPlayer{
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(Players.class);
        var from = criteria.from(Players.class);
        criteria.select(from)
                .where(criteriaBuilder.equal(from.get("name"), name));
        try {
            return entityManager.createQuery(criteria).getSingleResult();
        }catch (NoResultException e){
            throw new AbsenceOfThisPlayer();
        }
    }
}
