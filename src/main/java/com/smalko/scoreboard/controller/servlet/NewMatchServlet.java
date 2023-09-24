package com.smalko.scoreboard.controller.servlet;

import com.smalko.scoreboard.controller.OngoingMatchesService;
import com.smalko.scoreboard.exception.IncorrectNameLength;
import com.smalko.scoreboard.exception.NamesPlayersSame;
import com.smalko.scoreboard.exception.NoPlayerName;
import com.smalko.scoreboard.util.JspHelper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "newMatch", value = "/new-match")
public class NewMatchServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(NewMatchServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*
          - request.getRequestDispatcher("/s").forward(request,response);
          можно перенаправить запрос на другую страницю которая будет дальше обрабатовать запрос
          - request.getRequestDispatcher("/s").include(request,response);
          можно перенаправить запрос на другую страницю которая
          будет дальше обрабатовать запрос и вернетця на изначальную эту страницу
          -response.sendRedirect("адрес") отправляет на другую станицу
         */
        request.getRequestDispatcher(JspHelper.getPath("newMatch")).include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var playerOne = request.getParameter("playerOne").replaceAll("[0-9]", "");
        var playerTwo = request.getParameter("playerTwo").replaceAll("[0-9]", "");

        try {
            if (playerOne.length() == 0 || playerTwo.length() == 0) {
                log.error("There's no player's name");
                throw new NoPlayerName();
            } else if (playerOne.equals(playerTwo)) {
                log.error("The names of the players are the same");
                throw new NamesPlayersSame();
            } else if (playerOne.length() < 3
                       || playerOne.length() > 20
                       || playerTwo.length() < 3
                       || playerTwo.length() > 20) {
                log.error("Incorrect name length");
                throw new IncorrectNameLength();
            }
            var uuid = UUID.randomUUID();
            var ongoingMatchesService = OngoingMatchesService.getInstance();
            ongoingMatchesService.createNewMatch(playerOne, playerTwo, uuid);

            response.sendRedirect("/app/match-score?uuid=" + uuid);
        } catch (NoPlayerName | NamesPlayersSame | IncorrectNameLength e) {
            request.setAttribute("exception", e.getMessage());
            request.getRequestDispatcher(JspHelper.getPath("newMatch")).include(request, response);
        }
    }
}
