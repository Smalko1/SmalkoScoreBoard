package com.smalko.scoreboard.player.model.repository;

import com.smalko.scoreboard.player.model.entity.Players;
import com.smalko.scoreboard.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;

import java.util.Optional;

public class PlayerRepository extends RepositoryUtil<Integer, Players> {

    public PlayerRepository(EntityManager entityManager) {
        super(Players.class, entityManager);
    }

    public Optional<Players> findByName(String name, Session session) {
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(Players.class);
        var from = criteria.from(Players.class);
        criteria.select(from).where(criteriaBuilder.equal(from.get("name"), name));
        return session.createQuery(criteria)
                .stream()
                .findAny();
    }
}
