<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <title>Liste des "User"</title>
  </head>
  <body>
    <h1>Liste des "User"</h1>
    <ul>
    <c:forEach var="user" items="${it}">
        <li><a href="${user.id}">${user.name}</a>
    </c:forEach>
    </ul>
  </body>
</html>