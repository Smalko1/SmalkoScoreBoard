package com.smalko.scoreboard.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JspHelperTest {
    private static final String path = "WEB-INF/jsp/%s.jsp";

    @Test
    void getPath() {
        var actualResult = path.formatted("test");

        var expectedResult = JspHelper.getPath("test");

        Assertions.assertThat(actualResult).isEqualTo(expectedResult);
    }
}