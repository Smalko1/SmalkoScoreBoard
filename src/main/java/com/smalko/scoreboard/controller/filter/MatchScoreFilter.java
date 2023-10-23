package com.smalko.scoreboard.controller.filter;

import com.smalko.scoreboard.controller.OngoingMatchesService;
import com.smalko.scoreboard.exception.NoSuchUUID;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

@WebFilter(filterName = "Filter", value = "/match-score", servletNames ="matchScore")
public class MatchScoreFilter implements Filter {
    private static final OngoingMatchesService matchesService = OngoingMatchesService.getInstance();
    private static final Logger log = LoggerFactory.getLogger(MatchScoreFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            var uuidParameter = request.getParameter("uuid");
            var uuid = UUID.fromString(uuidParameter);
            if (!matchesService.containsUUID(uuid)) {
                throw new NoSuchUUID();
            }
            log.info("The filter was successful");
            chain.doFilter(request, response);
        } catch (NoSuchUUID | NullPointerException e) {
            log.error(e.getMessage());
            ((HttpServletResponse) response).sendRedirect("/home");
        }
    }
}
