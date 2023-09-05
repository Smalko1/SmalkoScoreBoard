package com.smalko.scoreboard.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MatchScoreCalculationService {

    private final MatchScoreModel matchScoreModel;

    public void addPoint(int playerScoredId) {
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
                if (opponentPoint != Points.FORTY_40) {
                    plusGame(isFirst);
                    return;
                } else {
                    opponentPoint = Points.LESS;
                    playerScored = Points.ADVANTAGE_AD;
                }
            }
            case EVENLY -> {
                playerScored = Points.ADVANTAGE_AD;
                opponentPoint = Points.LESS;
            }
            case LESS -> {
                playerScored = Points.EVENLY;
                opponentPoint = Points.EVENLY;
            }
            case ADVANTAGE_AD -> {
                plusGame(isFirst);
                return;
            }
        }

        newPointList(playerScored, opponentPoint, isFirst);
    }

    private void plusGame(boolean isFirst) {
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
        if (playerScored - opponent > 2 && playerScored >= 7){
            plusSet(isFirst);
            return;
        }

        newGameList(playerScored ,opponent ,isFirst);
    }

    private void plusSet(boolean isFirst) {
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
        if (playerScored - opponent > 2 && playerScored >= 4){
            System.out.println("Перемога");
            return;
        }
        newSetList(playerScored, opponent, isFirst);
    }

    private void newSetList(int playerScored, int opponent, boolean isFirst) {
        matchScoreModel.setSet(isFirst
                ? List.of(playerScored, opponent)
                : List.of(opponent, playerScored));
    }

    private void newGameList(int playerScored, int opponent, boolean isFirst) {
        matchScoreModel.setGame(isFirst
                ? List.of(playerScored, opponent)
                : List.of(opponent, playerScored));
    }


    private void newPointList(Points playerScored, Points opponentPoint, boolean isFirst) {
        matchScoreModel.setPoint(isFirst
                ? List.of(playerScored, opponentPoint)
                : List.of(opponentPoint, playerScored));
    }

}
