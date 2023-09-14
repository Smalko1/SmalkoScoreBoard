package com.smalko.scoreboard.controller;

import com.smalko.scoreboard.match.model.dto.MatchesCreateDto;
import com.smalko.scoreboard.match.service.MatchesService;
import com.smalko.scoreboard.player.model.dto.PlayerReadDto;
import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import com.smalko.scoreboard.player.service.PlayerService;

import java.util.UUID;

public class FinishedMatchesPersistenceService {

    private static final OngoingMatchesService ongoingMatches = OngoingMatchesService.getInstance();
    private static UUID uuid = null;
    private static final CurrentMatch march = ongoingMatches.getMatch(uuid);

    public static void finishedMatches(UUID uuid) {
        setUuid(uuid);
        var playersOneId = saveInBDPlayers(march.getPlayersOne());
        var playersTwoId = saveInBDPlayers(march.getPlayersTwo());
        int winnerId = march.getPlayersOne() == march.getWinner() ? playersOneId : playersTwoId;

        saveMatch(playersOneId, playersTwoId, winnerId);
    }

    private static void saveMatch(int playersOneId, int playersTwoId, int winnerId) {
        MatchesService.openMatchesService().createMatch(new MatchesCreateDto(playersOneId, playersTwoId, winnerId));
    }


    private static int saveInBDPlayers(PlayersCreateDto players) {
        return PlayerService.openPlayerService()
                .getPlayersForName(players.name())
                .map(PlayerReadDto::id)
                .orElseGet(() -> PlayerService.openPlayerService().createPlayer(players));
    }


    private static void setUuid(UUID uuid) {
        FinishedMatchesPersistenceService.uuid = uuid;
    }
}
