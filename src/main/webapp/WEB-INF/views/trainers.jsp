<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Registered Trainers Repository</title>
</head>
<body>
<h1>List of Trainers</h1>
<c:forEach items="${trainerList}" var="trainer" >
  <li id="trainer_<c:out value="${trainer.firstName} ${trainer.lastName}"/>">
    <c:out value="${trainer.firstName} ${trainer.lastName}"/>
    [<c:out value="${trainer.age} years" />]
    <a href="trainers/${trainer.firstName}%20${trainer.lastName}">Details</a>
  </li>
</c:forEach>
<br/>
<form action="/">
  <input type="submit" value="Back To Menu">
</form>
</body>
</html>
