<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <title>User : ${it.name}</title>
    <jsp:include page="/layout/head.jsp"/>
  </head>
  <body>
    <jsp:include page="/layout/navbar.jsp"/>
    <div class="container">
      <div class="row">
        <div class="col-md-6 col-md-offset-3">
          <div class="panel panel-default">
            <div class="panel-heading">
              <h3 class="panel-title">Détail du "User"</h3>
            </div>
            <div class="panel-body">
              Nom : ${it.name}<br/>
              Id : ${it.id}<br/>
              alias : ${it.alias} <br/>
              email : ${it.email} <br/>
              mot de passe : ${it.passwdHash} <br/>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
