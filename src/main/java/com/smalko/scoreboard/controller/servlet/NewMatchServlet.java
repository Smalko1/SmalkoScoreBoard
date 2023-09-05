package com.smalko.scoreboard.controller.servlet;

import com.smalko.scoreboard.controller.OngoingMatchesService;
import com.smalko.scoreboard.util.JspHelper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet(name = "newMatch", value = "/new-match")
public class NewMatchServlet extends HttpServlet {

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
        var playerOne = request.getParameter("playerOne");
        var playerTwo = request.getParameter("playerTwo");

        if (playerOne.replaceAll("[0-9]", "").length() == 0 || playerTwo.replaceAll("[0-9]", "").length() == 0) {
            request.getRequestDispatcher(JspHelper.getPath("newMatch")).include(request, response);
            return;
        }

        var uuid = UUID.randomUUID();
        var ongoingMatchesService = new OngoingMatchesService();
        ongoingMatchesService.createNewMatch(playerOne, playerTwo, uuid);

        response.sendRedirect("/app/match-score?uuid=" + uuid);
    }
}
