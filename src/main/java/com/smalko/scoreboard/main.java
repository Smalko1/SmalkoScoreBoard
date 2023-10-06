package com.smalko.scoreboard;

import com.smalko.scoreboard.controller.MatchesController;

public class main {
    private static final MatchesController MATCHES_CONTROLLER = MatchesController.getInstance();
    public static void main(String[] args) {
        var i = MATCHES_CONTROLLER.countMatch();
        System.err.println(i);

        var matchesReadDtos = MATCHES_CONTROLLER.printMatch(1);
        System.err.println(matchesReadDtos);


        System.out.println("Hello");
    }
}
