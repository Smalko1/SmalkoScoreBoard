package com.smalko.scoreboard.controller.score;

import com.smalko.scoreboard.controller.OngoingMatchesService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class MatchScoreCalculationService {

    private static MatchScoreModel matchScoreModel;

    private static boolean isWon;
    private static boolean isTiebreak;
    private static boolean isFirstPlayer;

    public static boolean isWonAddPoint(int playerScoredId, UUID uuid) {
        matchScoreModel = OngoingMatchesService.getInstance().getMatch(uuid).getMatchScoreModel();
        isWon = false;
        isFirstPlayer = playerScoredId == 0;
        if (isTiebreak)
            tiebreak();
        else
            addPoint();
        OngoingMatchesService.getInstance().getMatch(uuid).setMatchScoreModel(matchScoreModel);
        return isWon;
    }

    private static void addPoint() {
        var point = matchScoreModel.getPoint();
        Points opponentPoint = null;
        Points playerScored = null;
        if (isFirstPlayer) {
            playerScored = point.get(0);
            opponentPoint = point.get(1);
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
                    plusGame();
                    return;
                } else if (opponentPoint == Points.ADVANTAGE_AD) {
                    opponentPoint = Points.FORTY_40;
                } else {
                    playerScored = Points.ADVANTAGE_AD;
                }
            }
            case ADVANTAGE_AD -> {
                plusGame();
                return;
            }
        }

        newPointList(playerScored, opponentPoint);
    }

    private static void tiebreak() {
        var point = matchScoreModel.getPoint();
        Points opponentPoint = null;
        Points playerScored = null;
        if (isFirstPlayer) {
            playerScored = point.get(0);
            opponentPoint = point.get(1);
        } else {
            playerScored = point.get(1);
            opponentPoint = point.get(0);
        }
        switch (playerScored) {
            case ZERO_0 -> playerScored = Points.ONE_1;
            case ONE_1 -> playerScored = Points.TWO_2;
            case TWO_2-> playerScored = Points.TREE_3;
            case TREE_3 -> playerScored = Points.FORE_4;
            case FORE_4 -> playerScored = Points.FIVE_5;
            case FIVE_5 -> playerScored = Points.SIX_6;
            case SIX_6 -> playerScored = Points.SEVEN_7;
            case SEVEN_7 -> playerScored = Points.EIGHT_8;
            case EIGHT_8 -> playerScored = Points.NINE_9;
        }
        if (Integer.parseInt(playerScored.getNumericValue()) - Integer.parseInt(opponentPoint.getNumericValue()) >= 2){
            isTiebreak = false;
            plusSet();
            return;
        }
        newPointList(playerScored, opponentPoint);
    }

    private static void plusGame() {
        matchScoreModel.setPoint(List.of(Points.ZERO_0, Points.ZERO_0));
        var gameList = matchScoreModel.getGame();
        int playerScored;
        int opponent = 0;
        if (isFirstPlayer) {
            playerScored = gameList.get(0);
            opponent = gameList.get(1);
        } else {
            playerScored = gameList.get(1);
            opponent = gameList.get(0);
        }
        playerScored++;

        if (playerScored - opponent == 0 && playerScored == 6) {
            isTiebreak = true;
            return;
        }
        if (playerScored - opponent > 1 && playerScored >= 6) {
            plusSet();
            return;
        }

        newGameList(playerScored, opponent);
    }

    private static void plusSet() {
        matchScoreModel.setGame(List.of(0, 0));
        var setList = matchScoreModel.getSet();
        int playerScored = 0;
        int opponent = 0;
        if (isFirstPlayer) {
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
        newSetList(playerScored, opponent);
    }

    private static void newSetList(int playerScored, int opponent) {
        matchScoreModel.setSet(isFirstPlayer
                ? List.of(playerScored, opponent)
                : List.of(opponent, playerScored));
    }

    private static void newGameList(int playerScored, int opponent) {
        matchScoreModel.setGame(isFirstPlayer
                ? List.of(playerScored, opponent)
                : List.of(opponent, playerScored));
    }


    private static void newPointList(Points playerScored, Points opponentPoint) {
        matchScoreModel.setPoint(isFirstPlayer
                ? List.of(playerScored, opponentPoint)
                : List.of(opponentPoint, playerScored));
    }
}
