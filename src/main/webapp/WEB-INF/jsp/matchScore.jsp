<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/matchStyle.css">
</head>
<body>

<header class="center">
    <h1 class="center">Scoreboard</h1>
</header>

<main>
    <h2 class="center">Match</h2>
    <section class="center">
        <table>
            <tr>
                <td>Set</td>
                <td>Game</td>
                <td>Point</td>
                <td>Players</td>
                <td></td>
            </tr>
            <tr>
                <td>${requestScope.setOnePlayer}</td>
                <td>${requestScope.gameOnePlayer}</td>
                <td>${requestScope.pointOnePlayer}</td>
                <td>${requestScope.playerOne}</td>
                <td>
                    <form action="#" method="post">
                        <button type="submit" class="button" name="player" value="0">Goal</button>
                    </form>
                </td>
            </tr>
            <tr>
                <td>${requestScope.setTowPlayer}</td>
                <td>${requestScope.gameTwoPlayer}</td>
                <td>${requestScope.pointTwoPlayer}</td>
                <td>${requestScope.playerTwo}</td>
                <td>
                    <form action="#" method="post">
                        <button type="submit" class="button" name="player" value="1">Goal</button>
                    </form>
                </td>
            </tr>
        </table>
    </section>
</main>
</body>
</html>
