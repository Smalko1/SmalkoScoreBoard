package com.smalko.scoreboard.controller.score;

import com.smalko.scoreboard.controller.OngoingMatchesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MatchScoreCalculationServiceTest {
    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();
    private UUID uuid;
    private MatchScoreModel match;

    @BeforeEach
    void createMatch() {
        uuid = UUID.randomUUID();
        ongoingMatchesService.createNewMatch("playerOne", "playerTwo", uuid);
        match = ongoingMatchesService.getMatch(uuid).getMatchScoreModel();
    }

    @Test
    void isWinnerFirstPlayer() {
        match.setPoint(List.of(Points.ADVANTAGE_AD, Points.FORTY_40));
        match.setGame(List.of(6, 5));
        match.setSet(List.of(1, 0));

        var winner = MatchScoreCalculationService.isWonAddPoint(0, uuid);

        assertThat(winner).isTrue();
    }

    @Test
    void isWinnerFSecondPlayer() {
        match.setPoint(List.of(Points.FIFTEEN_15, Points.FORTY_40));
        match.setGame(List.of(2, 5));
        match.setSet(List.of(1, 2));

        var winner = MatchScoreCalculationService.isWonAddPoint(1, uuid);

        assertThat(winner).isTrue();
    }

    @Test
    void addPoint() {
        MatchScoreCalculationService.isWonAddPoint(0, uuid);
        var actual = match.getPoint();

        var expected = List.of(Points.FIFTEEN_15, Points.ZERO_0);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void addGame() {
        match.setPoint(List.of(Points.FORTY_40, Points.FIFTEEN_15));
        MatchScoreCalculationService.isWonAddPoint(0, uuid);
        var actual = match.getGame();

        var expected = List.of(1, 0);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void addSet() {
        match.setGame(List.of(3, 5));
        match.setPoint(List.of(Points.FORTY_40, Points.ADVANTAGE_AD));
        MatchScoreCalculationService.isWonAddPoint(1, uuid);
        var actual = match.getSet();

        var expected = List.of(0, 1);

        assertThat(actual).isEqualTo(expected);
    }
    @Test
    void tiebreakOn(){
        match.setGame(List.of(6, 5));
        match.setPoint(List.of(Points.THIRTY_30, Points.ADVANTAGE_AD));
        MatchScoreCalculationService.isWonAddPoint(1, uuid);
        MatchScoreCalculationService.isWonAddPoint(1, uuid);

        var actualGame  = match.getGame();
        var actualPoint  = match.getPoint();

        var expectedGame = List.of(6, 6);
        var expectedPoint  = List.of(Points.ZERO_0, Points.ONE_1);

        assertThat(actualGame).isEqualTo(expectedGame);
        assertThat(actualPoint).isEqualTo(expectedPoint);
    }

    @Test
    void plusSetAfterTiebreak(){
        match.setGame(List.of(6, 5));
        match.setPoint(List.of(Points.THIRTY_30, Points.ADVANTAGE_AD));
        MatchScoreCalculationService.isWonAddPoint(1, uuid);
        MatchScoreCalculationService.isWonAddPoint(1, uuid);
        MatchScoreCalculationService.isWonAddPoint(1, uuid);

        var actualGame  = match.getSet();

        var expected = List.of(0,1);

        assertThat(actualGame).isEqualTo(expected);
    }
}