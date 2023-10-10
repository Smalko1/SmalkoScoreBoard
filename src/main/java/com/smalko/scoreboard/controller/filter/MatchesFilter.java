package com.smalko.scoreboard.controller.filter;

import com.smalko.scoreboard.controller.MatchesController;
import com.smalko.scoreboard.exception.WrongPage;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter(filterName = "MatchesFilter", value = "/matches", servletNames = "MatchesServlet")
public class MatchesFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(MatchScoreFilter.class);
    private static final MatchesController MATCHES_CONTROLLER = MatchesController.getInstance();

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            var uuidParameter = Integer.parseInt(request.getParameter("page"));
            if (uuidParameter < 0 || uuidParameter > MATCHES_CONTROLLER.countMatch()){
                throw new WrongPage();
            }
            chain.doFilter(request, response);
        }catch (WrongPage | NullPointerException e){
            log.error(e.getMessage());
            ((HttpServletResponse) response).sendRedirect("/app/matches");
        }
    }
}
