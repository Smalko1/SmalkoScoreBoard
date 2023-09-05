package com.smalko.scoreboard.match.model.repository;

import com.smalko.scoreboard.match.model.entity.Matches;
import com.smalko.scoreboard.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;

public class MatchesRepository extends RepositoryUtil<Integer, Matches> {
    public MatchesRepository(EntityManager entityManager) {
        super(Matches.class, entityManager);
    }
}
