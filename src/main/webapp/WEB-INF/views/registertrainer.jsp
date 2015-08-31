<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h1>Register Trainer</h1>
<table>
  <form method="POST">
    <tr>
      <td>First Name: </td><td><input type="text" name="firstName" /></td>
    </tr><tr>
    <td>Last Name: </td><td><input type="text" name="lastName" /></td>
  </tr><tr>
    <td>Age: </td><td><input type="text" name="age" /></td>
  </tr><tr>
    <td>Salary: </td><td><input type="text" name="salary" /></td>
  </tr><tr>
    <td ><input type="submit" value="Register" /></td>
  </form>
  <form action="/trainers">
    <td><input type="submit" value="Cancel" /></td>
  </form>
  </tr>
</table>
</body>
</body>
</html>