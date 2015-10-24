<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <title>Liste des "User"</title>
    <!-- jQuery -->
    <script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
    <script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>

    <!-- bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
  </head>
  <body>
    <nav class="navbar navbar-default">
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
              <li><a href="/">Javascript</a></li>
              <li class="active"><a href="/html/user">MVC Template (jsp)</a></li>
          </ul>
      </div>
    </nav>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h1>Liste des "User"</h1>
                <ul>
                <c:forEach var="user" items="${it}">
                    <li><a href="user/${user.id}">${user.name}</a>
                </c:forEach>
                </ul>
            </div>
        </div>
    </div>
  </body>
</html>