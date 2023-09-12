package com.smalko.scoreboard.controller.servlet;

import com.smalko.scoreboard.controller.Helper;
import com.smalko.scoreboard.controller.MatchScoreCalculationService;
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

        if (MatchScoreCalculationService.isWonAddPoint(playerId)) {
            ongoingMatches.getMatch(uuid).setWinner(playerId);

            ongoingMatches.removeMatch(uuid);
        } else
            response.sendRedirect("/app/match-score?uuid=" + uuid);
    }

    private void setAttributes(HttpServletRequest request) {
        var match = ongoingMatches.getMatch(uuid);

        var PlayerOne = match.getPlayersOne().name();
        var PlayerTwo = match.getPlayersTwo().name();

        Helper helper = new Helper(uuid);
        request.setAttribute("setOnePlayer", helper.getSet(0));
        request.setAttribute("gameOnePlayer", helper.getGame(0));
        request.setAttribute("pointOnePlayer", helper.getPoint(0).getNumericValue());
        request.setAttribute("setTowPlayer", helper.getSet(1));
        request.setAttribute("gameTwoPlayer", helper.getGame(1));
        request.setAttribute("pointTwoPlayer", helper.getPoint(1).getNumericValue());
        request.setAttribute("playerOne", PlayerOne);
        request.setAttribute("playerTwo", PlayerTwo);
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
