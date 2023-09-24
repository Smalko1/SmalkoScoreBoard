<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 19.08.2023
  Time: 11:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/newMatchStyle.css">
</head>
<body>
<header class="center">
    <h1>Create new Match</h1>
</header>
<main>
    <form align="center" action="" method="post">
        <label for="playerOneName">
            Firs player:<input type="text" placeholder="Firs player" name="playerOne" id="playerOneName">
        </label><br><br>

        <label for="playerTwoName">
            Second player :<input type="text" placeholder="Second player" name="playerTwo" id="playerTwoName">
        </label><br><br>

        <h6>${requestScope.exception}</h6>
        <button type="submit">Start Game</button>
        <br>

    </form>
</main>
</body>
</html>
