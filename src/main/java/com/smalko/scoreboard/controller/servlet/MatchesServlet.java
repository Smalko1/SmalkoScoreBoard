package com.smalko.scoreboard.controller.servlet;

import com.smalko.scoreboard.controller.MatchesController;
import com.smalko.scoreboard.exception.*;
import com.smalko.scoreboard.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet(name = "MatchesServlet", value = "/matches")
public class MatchesServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MatchesServlet.class);
    private static boolean isSearchPlayer;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = givePage(request);
        isSearchPlayer = false;
        try {
            var searchPlayer = giveSearchName(request);
            request.setAttribute("matches", MatchesController.printMatchForPlayer(searchPlayer));
            isSearchPlayer = true;
            log.info("search players for name: {}", searchPlayer);
        } catch (NullPointerException e) {
            request.setAttribute("matches", MatchesController.printMatch(page));
            log.info("Demonstration the list of matches");
        } catch (AbsenceOfThisPlayer | NoPlayerName | IncorrectNameLength e) {
            var messageException = e.getMessage();
            request.setAttribute("exception", messageException);
            request.setAttribute("matches", MatchesController.printMatch(page));
            log.error("Exception {}, Demonstrating the error to the user, and displaying the list of matches", messageException);
        }
        request.setAttribute("isSearchPlayer", isSearchPlayer);
        request.getRequestDispatcher(JspHelper.getPath("matches"))
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        isSearchPlayer = false;
        int page = givePage(request);
        var navigation = Integer.parseInt(request.getParameter("navigation"));

        if (page + navigation >= 1 && page + navigation <= MatchesController.countMatch()) {
            page += navigation;
        }

        if (page == 1) {
            response.sendRedirect("/app/matches");
        } else
            response.sendRedirect("/app/matches?page=" + page);
    }

    private static String giveSearchName(HttpServletRequest request) throws NoPlayerName, IncorrectNameLength {
        var pageGetParameter = request.getParameter("searchPlayer");
        if (pageGetParameter.trim().length() == 0) {
            log.error("There's no player's name");
            throw new NoPlayerName();
        } else if (pageGetParameter.length() < 3
                   || pageGetParameter.length() > 20) {
            log.error("Incorrect name length");
            throw new IncorrectNameLength();
        }
        return pageGetParameter;
    }

    private static Integer givePage(HttpServletRequest request) {
        int page;
        try {
            var pageGetParameter = request.getParameter("page");
            if (pageGetParameter == null) {
                throw new NullPointerException();
            }
            var pageParameter = Integer.parseInt(pageGetParameter);

            if (pageParameter < 0 || pageParameter > MatchesController.countMatch() / 5 + 1) {
                throw new WrongPage();
            }
            page = pageParameter;
        } catch (WrongPage | NullPointerException e) {
            log.error(e.getMessage());
            page = 1;
        }
        return page;
    }
}
