<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <jsp:include page="/layout/head.jsp"/>
    <meta charset="utf-8">
    <title>Liste sécurisée des "User"</title>
  </head>
  <body>
    <jsp:include page="/layout/navbar.jsp"/>
    <div class="container">
      <div class="row">
        <div class="col-md-6 col-md-offset-3">
          Bonjour ${it.currentUser.name}
        </div>
      </div>
      <div class="row">
        <div class="col-md-6 col-md-offset-3">
          <h1>Liste sécurisée des "User"</h1>
          <ul class="list-group">
            <c:forEach var="user" items="${it.users}">
            <li class="list-group-item">${user.id} : <a href="user/${user.id}">${user.name}</a>
            </c:forEach>
          </ul>
        </div>
      </div>
    </div>
  </body>
</html>
