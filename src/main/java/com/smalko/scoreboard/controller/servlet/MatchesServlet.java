package com.smalko.scoreboard.controller.servlet;

import com.smalko.scoreboard.controller.MatchesController;
import com.smalko.scoreboard.exception.AbsenceOfThisPlayer;
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
    private static final MatchesController MATCHES_CONTROLLER = MatchesController.getInstance();
    private static final Logger log = LoggerFactory.getLogger(MatchesServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
            log.info("user want to a page = {}", page);
        } catch (NullPointerException e) {
            page = 0;
            log.info("page = {} because: request.getParameter(\"page\") == null", page);
        }

        try {
            var searchPlayer = request.getParameter("searchPlayer").trim();
            request.setAttribute("matches", MATCHES_CONTROLLER
                    .printMatchForPlayer(searchPlayer));
            log.info("search players for name: {}", searchPlayer);
        } catch (NullPointerException e) {
            request.setAttribute("matches", MATCHES_CONTROLLER.printMatch(page));
            log.info("Demonstration the list of matches");
        } catch (AbsenceOfThisPlayer e) {
            var messageException = e.getMessage();
            request.setAttribute("exception", messageException);
            request.setAttribute("matches", MATCHES_CONTROLLER.printMatch(page));
            log.error("Exception {}, Demonstrating the error to the user, and displaying the list of matches",messageException);
        }

        request.getRequestDispatcher(JspHelper.getPath("matches"))
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NullPointerException e) {
            page = 0;
            log.info("page = {} because: request.getParameter(\"page\") == null", page);
        }
        var navigation = Integer.parseInt(request.getParameter("navigation"));

        if (page + navigation < 0 || page + navigation > MATCHES_CONTROLLER.countMatch()){
            return;
        }else
            page += navigation;

        response.sendRedirect("/app/match-score?page=" + page);

    }


}
