package com.smalko.scoreboard.controller.servlet;

import com.smalko.scoreboard.controller.FinishedMatchesPersistenceService;
import com.smalko.scoreboard.controller.score.Scores;
import com.smalko.scoreboard.controller.score.MatchScoreCalculationService;
import com.smalko.scoreboard.controller.OngoingMatchesService;
import com.smalko.scoreboard.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "matchScore", value = "/match-score")
public class MatchScoreServlet extends HttpServlet {
    private final OngoingMatchesService ongoingMatches = OngoingMatchesService.getInstance();
    private UUID uuid;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUuid(UUID.fromString(request.getParameter("uuid")));

        setAttributes(request);
        request.getRequestDispatcher(JspHelper.getPath("matchScore"))
                .forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int playerId = Integer.parseInt(request.getParameter("player"));

        if (MatchScoreCalculationService.isWonAddPoint(playerId, uuid)) {
            ongoingMatches.getMatch(uuid).setWinner(playerId);
            FinishedMatchesPersistenceService.finishedMatches(uuid);
            ongoingMatches.removeMatch(uuid);
            response.sendRedirect("/app/home");
        } else
            response.sendRedirect("/app/match-score?uuid=" + uuid);
    }

    private void setAttributes(HttpServletRequest request) {
        var match = ongoingMatches.getMatch(uuid);

        var PlayerOne = match.getPlayersOne().name();
        var PlayerTwo = match.getPlayersTwo().name();

        Scores scores = new Scores(uuid);
        request.setAttribute("setOnePlayer", scores.getSet(0));
        request.setAttribute("gameOnePlayer", scores.getGame(0));
        request.setAttribute("pointOnePlayer", scores.getPoint(0).getNumericValue());
        request.setAttribute("setTowPlayer", scores.getSet(1));
        request.setAttribute("gameTwoPlayer", scores.getGame(1));
        request.setAttribute("pointTwoPlayer", scores.getPoint(1).getNumericValue());
        request.setAttribute("playerOne", PlayerOne);
        request.setAttribute("playerTwo", PlayerTwo);
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
