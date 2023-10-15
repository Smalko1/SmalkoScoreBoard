package com.smalko.scoreboard.controller.servlet;

import com.smalko.scoreboard.controller.MatchesController;
import com.smalko.scoreboard.exception.AbsenceOfThisPlayer;
import com.smalko.scoreboard.exception.WrongPage;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = givePage(request);
        try {
            var searchPlayer = request.getParameter("searchPlayer").trim();
            request.setAttribute("matches", MatchesController.printMatchForPlayer(searchPlayer));
            log.info("search players for name: {}", searchPlayer);
        } catch (NullPointerException e) {
            request.setAttribute("matches", MatchesController.printMatch(page));
            log.info("Demonstration the list of matches");
        } catch (AbsenceOfThisPlayer e) {
            var messageException = e.getMessage();
            request.setAttribute("exception", messageException);
            request.setAttribute("matches", MatchesController.printMatch(page));
            log.error("Exception {}, Demonstrating the error to the user, and displaying the list of matches",messageException);
        }

        request.getRequestDispatcher(JspHelper.getPath("matches"))
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = givePage(request);

        var navigation = Integer.parseInt(request.getParameter("navigation"));

        if (page + navigation < 0 || page + navigation > MatchesController.countMatch()){
        }else
            page += navigation;

        if(page == 0){
            response.sendRedirect("/app/matches");
        }else
            response.sendRedirect("/app/matches?page=" + page);
    }

    private static Integer givePage(HttpServletRequest request){
        int page;
        try {
            var pageGetParameter = request.getParameter("page");
            if (pageGetParameter == null){
                throw new NullPointerException();
            }
            var pageParameter = Integer.parseInt(pageGetParameter);
            if (pageParameter < 0 || pageParameter > MatchesController.countMatch()){
                throw new WrongPage();
            }
            page = pageParameter;
        }catch (WrongPage | NullPointerException e){
            log.error(e.getMessage());
            page = 1;
        }
        return page;
    }


}
