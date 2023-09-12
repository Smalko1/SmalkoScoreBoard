package com.smalko.scoreboard.controller;

import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import lombok.Data;

import java.util.UUID;

@Data
public class CurrentMatch {

    private final UUID uuid;
    private final PlayersCreateDto playersOne;
    private final PlayersCreateDto playersTwo;
    private PlayersCreateDto winner;
    private MatchScoreModel matchScoreModel;


    public CurrentMatch(UUID uuid, PlayersCreateDto playersOne, PlayersCreateDto playersTwo) {
        this.uuid = uuid;
        this.playersOne = playersOne;
        this.playersTwo = playersTwo;
        this.matchScoreModel = new MatchScoreModel();
    }

    public void setWinner(int playerId){
        winner = playerId == 1 ? playersOne : playersTwo;
    }
}
