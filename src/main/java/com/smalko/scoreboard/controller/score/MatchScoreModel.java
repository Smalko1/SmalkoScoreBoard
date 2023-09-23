package com.smalko.scoreboard.controller.score;

import lombok.Data;

import java.util.List;

@Data
public class MatchScoreModel {
    private List<Points> point = List.of(Points.ZERO_0, Points.ZERO_0);
    private List<Integer> game = List.of(0, 0);
    private List<Integer> set = List.of(0, 0);
}
