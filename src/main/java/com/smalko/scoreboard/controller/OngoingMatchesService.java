package com.smalko.scoreboard.controller;

import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class OngoingMatchesService {

    private static Map<UUID, CurrentMatch> ongoingMatches;
    private static final OngoingMatchesService INSTANCE = new OngoingMatchesService();

    public void createNewMatch(String playerOne, String playerTwo, UUID uuid){
        ongoingMatches = new HashMap<>();
        var playerOneDto = mapperDto(playerOne);
        var playerTwoDto = mapperDto(playerTwo);
        var currentMatch = new CurrentMatch(uuid, playerOneDto, playerTwoDto);

        ongoingMatches.put(uuid, currentMatch);
    }
    public CurrentMatch getMatch(UUID uuid){
        return ongoingMatches.get(uuid);
    }

    public void removeMatch(UUID uuid){
        ongoingMatches.remove(uuid);
    }

    private PlayersCreateDto mapperDto(String name){
        return new PlayersCreateDto(name);
    }

    public static OngoingMatchesService getInstance() {
        return INSTANCE;
    }
}