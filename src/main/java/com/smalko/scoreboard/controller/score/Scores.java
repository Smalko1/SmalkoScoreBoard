package com.smalko.scoreboard.controller.score;

import com.smalko.scoreboard.controller.OngoingMatchesService;

import java.util.UUID;

public class Scores {
    private static final OngoingMatchesService ongoingMatches  = OngoingMatchesService.getInstance();
    private final UUID uuid;

    public Scores(UUID uuid) {
        this.uuid = uuid;
    }

    public Points getPoint(int player){
        return ongoingMatches.getMatch(uuid).getMatchScoreModel().getPoint().get(player);
    }

    public int getGame(int player){
        return ongoingMatches.getMatch(uuid).getMatchScoreModel().getGame().get(player);
    }

    public int getSet(int player){
        return ongoingMatches.getMatch(uuid).getMatchScoreModel().getSet().get(player);
    }
}
