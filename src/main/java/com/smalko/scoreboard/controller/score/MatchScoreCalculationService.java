package com.smalko.scoreboard.controller.score;

import com.smalko.scoreboard.controller.OngoingMatchesService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class MatchScoreCalculationService {

    private static MatchScoreModel matchScoreModel;

    private static boolean isWon;

    public static boolean isWonAddPoint(int playerScoredId, UUID uuid) {
        matchScoreModel = OngoingMatchesService.getInstance().getMatch(uuid).getMatchScoreModel();
        isWon = false;
        addPoint(playerScoredId);
        OngoingMatchesService.getInstance().getMatch(uuid).setMatchScoreModel(matchScoreModel);
        return isWon;
    }

    private static void addPoint(int playerScoredId) {
        var point = matchScoreModel.getPoint();
        boolean isFirst = false;
        Points opponentPoint = null;
        Points playerScored = null;
        if (playerScoredId == 0) {
            playerScored = point.get(0);
            opponentPoint = point.get(1);
            isFirst = true;
        } else {
            playerScored = point.get(1);
            opponentPoint = point.get(0);
        }

        switch (playerScored) {
            case ZERO_0 -> playerScored = Points.FIFTEEN_15;
            case FIFTEEN_15 -> playerScored = Points.THIRTY_30;
            case THIRTY_30 -> playerScored = Points.FORTY_40;
            case FORTY_40 -> {
                if (opponentPoint != Points.FORTY_40 && opponentPoint != Points.ADVANTAGE_AD) {
                    plusGame(isFirst);
                    return;
                } else if (opponentPoint == Points.ADVANTAGE_AD) {
                    opponentPoint = Points.FORTY_40;
                } else {
                    playerScored = Points.ADVANTAGE_AD;
                }
            }
            case ADVANTAGE_AD -> {
                plusGame(isFirst);
                return;
            }
        }

        newPointList(playerScored, opponentPoint, isFirst);
    }

    private static void plusGame(boolean isFirst) {
        matchScoreModel.setPoint(List.of(Points.ZERO_0, Points.ZERO_0));
        var gameList = matchScoreModel.getGame();
        int playerScored;
        int opponent = 0;
        if (isFirst) {
            playerScored = gameList.get(0);
            opponent = gameList.get(1);
        } else {
            playerScored = gameList.get(1);
            opponent = gameList.get(0);
        }
        playerScored++;
        if (playerScored - opponent > 1 && playerScored >= 6) {
            plusSet(isFirst);
            return;
        }

        newGameList(playerScored, opponent, isFirst);
    }

    private static void plusSet(boolean isFirst) {
        matchScoreModel.setGame(List.of(0, 0));
        var setList = matchScoreModel.getSet();
        int playerScored = 0;
        int opponent = 0;
        if (isFirst) {
            playerScored = setList.get(0);
            opponent = setList.get(1);
        } else {
            playerScored = setList.get(1);
            opponent = setList.get(0);
        }
        playerScored++;
        if (playerScored - opponent >= 2 && playerScored >= 2) {
            isWon = true;
            return;
        }
        newSetList(playerScored, opponent, isFirst);
    }

    private static void newSetList(int playerScored, int opponent, boolean isFirst) {
        matchScoreModel.setSet(isFirst
                ? List.of(playerScored, opponent)
                : List.of(opponent, playerScored));
    }

    private static void newGameList(int playerScored, int opponent, boolean isFirst) {
        matchScoreModel.setGame(isFirst
                ? List.of(playerScored, opponent)
                : List.of(opponent, playerScored));
    }


    private static void newPointList(Points playerScored, Points opponentPoint, boolean isFirst) {
        matchScoreModel.setPoint(isFirst
                ? List.of(playerScored, opponentPoint)
                : List.of(opponentPoint, playerScored));
    }
}
