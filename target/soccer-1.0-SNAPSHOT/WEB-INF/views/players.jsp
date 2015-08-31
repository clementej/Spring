<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: R_RISCOS
  Date: 8/4/2015
  Time: 9:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Registered Players Repository</title>
</head>
<body>
<h1>List of Players</h1>
<c:forEach items="${playerList}" var="player" >
  <li id="player_<c:out value="${player.firstName} ${player.lastName}"/>">
    <c:out value="${player.firstName} ${player.lastName}"/>
    [<c:out value="${player.position}, ${player.age} years, from ${player.countryOfBirth}" />]
    <a href="players/${player.firstName}%20${player.lastName}">Details</a>
  </li>
</c:forEach>
<br/>
<form action="/">
    <input type="submit" value="Back To Menu">
</form>
</body>
</html>
