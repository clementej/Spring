<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:out value="Details about ${player.firstName} ${player.lastName}" /></title>
</head>
<body>
<img src="../../img/playeravatar.png">
<div class="playerView">
  <div class="playerName"><h1><c:out value="${player.firstName} ${player.lastName}" /></h1></div>
  <div>
    <span class="playerInfo">
      <b>Country of Birth: </b><c:out value="${player.countryOfBirth}" /><br/>
      <b>Age: </b><c:out value="${player.age}" /><br/>
      <b>Salary: </b><c:out value="${player.annualSalary.formatedValue}" /><br/>
      <b>Position: </b><c:out value="${player.position}"/><br/>
      <b>Stats</b><br/>
      <b>Number of Goals: </b><c:out value="${player.statistics.numberOfGoals}" /><br/>
      <b>Number of Goals: </b><c:out value="${player.statistics.numberOfBookings}" /><br/>
    </span>
  </div>
  <br/>
  <form action="/players">
      <input type="submit" value="Back To the Players List">
  </form>
</div>
</body>
</html>
