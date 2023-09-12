package com.smalko.scoreboard.controller;

import java.util.UUID;

public class FinishedMatchesPersistenceService {

    private static final OngoingMatchesService ongoingMatches = OngoingMatchesService.getInstance();
    private static UUID uuid = null;
    private static final CurrentMatch march = ongoingMatches.getMatch(uuid);

    public static void finishedMatches(UUID uuid){
        setUuid(uuid);
        savaInBDPlayers();

    }

    private static void savaInBDPlayers() {
        var playersOne = march.getPlayersOne();
    }

    private static void setUuid(UUID uuid) {
        FinishedMatchesPersistenceService.uuid = uuid;
    }
}
