<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 19.09.2023
  Time: 10:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Matches</title>
  <link rel="stylesheet" href="css/matchesStyle.css">
</head>
<body>
<header class="center">
  <h1>Matches</h1>

</header>
<main>
  <nav class="center">
    <form action="#" method="post">
      <button type="submit" class="button" name="home">Home page</button>
    </form>
  </nav>
  <section>

    <nav><h3><form  action="" method="get">
      <label for="searchPlayer">
        Player search:  <input type="text" placeholder="player" name="searchPlayer" id="searchPlayer">
        <c:if test="${requestScope.isSearchPlayer}">
        <button class="clean-button">Clean player</button>
        </c:if>
        <button type="submit">Search</button>
        <h6>
          ${requestScope.exception}
        </h6>
    </form>

      </h3>
    </nav>
    <h2>Match tabel:</h2>
    <table class="center">
      <c:forEach var="matches" items="${requestScope.matches}">
      <tr >
        <td>${matches.id()}</td>
        <td>${matches.playersOne()}</td>
        <td>${matches.playersTwo()}</td>
      </tr>
      </c:forEach>
    </table>
  </section>
  <form action="" method="post" class="center">
    <button class="arrow-button left" type="submit" name="navigation" value="-1"></button>
    <button class="arrow-button right" type="submit" name="navigation" value="1"></button>
  </form>
</main>
</body>
</html>
