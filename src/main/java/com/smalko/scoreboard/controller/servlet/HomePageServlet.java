package com.smalko.scoreboard.controller.servlet;

import com.smalko.scoreboard.util.JspHelper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "homePageServlet", value = "/home")
public class HomePageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JspHelper.getPath("homePage"))
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var newMatch = request.getParameter("newMatch");
        var matches = request.getParameter("matches");

        if (newMatch != null){
            response.sendRedirect("/app/new-match");
        }else if (matches != null)
            response.sendRedirect("/app/matches");
    }
}
