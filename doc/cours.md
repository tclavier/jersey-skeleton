# Documentation du projet agile

- [Maven](doc/maven.md)
- [Docker](doc/docker.md)
- [REST](doc/rest.md)
- [JDBI](doc/jdbi.md)
- [HTML & JavaScript](doc/htmljs.md)

## API

Dans le paquetage `fr.iutinfo.skeleton.api` vous trouverez l'ensemble des classes de l'API (*Application Programming Interface*)
- `Api` : le point d'entrée des requêtes vers les url commençant par `http://localhost:8080/v1/` la directive `packages("fr.iutinfo.skeleton.api");` permet d'enregistrer toutes les classes de ressources du package.
- `UserResource` : match les URLs de la forme `http://localhost:8080/v1/user` et permet de manipuler des ressources en mémoire de type `User`
- `UserDBResource` : match les urls de la forme `http://localhost:8080/v1/userdb` et permet de manipuler des ressources en base de données de type `User`
- `UserDao` : permet de lire et d'écrire en base des `User`
- `User` : la classe métier utilisée dans cet exemple
- `BDDFactory` : permet d'obtenir un accès à la base de données.

## Javascript

Dans `src/webapp/all.js` et `src/webapp/index.html` vous trouverez un exemple d'interface graphique en *JavaScript* mis en forme avec [bootstrap](http://getbootstrap.com/).

## HTML

Le plugin `template MVC` de *jersey* permet de générer du HTML depuis divers fichiers.

Les pages `http://localhost:8080/html/user` sont construites depuis :

- `src/main/webapp/fr/iutinfo/skeleton/web/UserViews/index.jsp`
- `src/main/java/fr/iutinfo/skeleton/web/UserViews.java`

**Remarque** : Notez l'*include* qui est fait vers `src/main/webapp/layout/head.jsp` afin de factoriser le code de présentation.

## Authentification

## Slf4j
