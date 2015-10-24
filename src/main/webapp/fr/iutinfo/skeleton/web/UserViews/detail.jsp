<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <title>User : ${it.name}</title>
  </head>
  <body>
    <h1>${it.name}</h1>
        ${it.id}
  </body>
</html>