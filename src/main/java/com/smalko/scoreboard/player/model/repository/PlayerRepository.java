package com.smalko.scoreboard.player.model.repository;

import com.smalko.scoreboard.player.model.entity.Players;
import com.smalko.scoreboard.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class PlayerRepository extends RepositoryUtil<Integer, Players> {


    public PlayerRepository(EntityManager entityManager) {
        super(Players.class, entityManager);
    }

    public Optional<Players> findByName(String name) {
        return null;
    }
}
