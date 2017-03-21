# Architecture générale du projet agile

Ce document présente l'architecture générale du projet agile qui s'organise autour de la création d'un serveur `REST` et de deux clients: un client web (`HTML', 'CSS` + `JavaScript`) et un client mobile (`Androïd`). Ce document présente essentiellement ce qui concerne le serveur `REST` et le client web.

## `REST` = *REpresentational State Transfer*

Dans une architecture [REST](https://fr.wikipedia.org/wiki/Representational_State_Transfer) chaque ressource est accessible par une unique URL et ce sont les verbes `HTTP` qui définissent les actions CRUD (*Create, Read, Update, Delete*) qu'il est possible de réaliser sur une ressource.
Mise à part les URLs de création, toutes les URLs peuvent être appelées autant de fois que l'on veux avec exactement le même résultat (idempotence).

Pour des ressources simples, le schéma est le suivant :

| Verbe | URL | Action |
|-------|-----|--------|
| `POST`  | `/foo` | Création d'un `Foo` |
| `GET`   | `/foo` | Liste de tous les `Foo` |
| `GET`   | `/foo?tel=xxx` | Lit le `Foo` dont le téléphone est `"xxx"` |
| `GET`   | `/foo/{id}` | Lecture du `Foo` identifié par `{id}` |
| `PUT`   | `/foo/{id}` | Mise à jour du `Foo` identifié par `{id}` |
| `DELETE`| `/foo/{id}` | Suppression le `Foo` identifié par `{id}` |

Pour des ressources composées, les URLs vont définir le contenant, par exemple : `/user/id_user/tel/id_tel` pour accéder au téléphone `id_tel` de l'utilisateur `id_user`.

**/!\ Attention** : *identifier le téléphone sous cette forme sous entend que dans notre application, il n'existe pas de téléphone sans propriétaire.*

## API

Dans le paquetage `fr.iutinfo.skeleton.api` vous trouverez l'ensemble des classes de l'API (*Application Programming Interface*)

- `Api` : le point d'entrée des requêtes vers les URL commençant par `http://localhost:8080/v1/` la directive `packages("fr.iutinfo.skeleton.api");` permet d'enregistrer toutes les classes de ressources du paquetage indiqué
- `UserResource` : match les URLs de la forme `http://localhost:8080/v1/user` et permet de manipuler des ressources en mémoire de type `User`
- `UserDBResource` : match les URLs de la forme `http://localhost:8080/v1/userdb` et permet de manipuler des ressources en base de données de type `User`
- `UserDao` : permet de lire et d'écrire en base des `User`
- `User` : la classe métier utilisée dans cet exemple
- `BDDFactory` : permet d'obtenir un accès à la base de données

## JavaScript

Dans `src/webapp/all.js` et `src/webapp/index.html` vous trouverez un exemple d'interface graphique en *JavaScript* mis en forme avec [bootstrap](http://getbootstrap.com/).

## HTML

Le plugin `template MVC` de *jersey* permet de générer du HTML depuis divers fichiers.

Les pages `http://localhost:8080/html/user` sont construites depuis :

- `src/main/webapp/fr/iutinfo/skeleton/web/UserViews/index.jsp`
- `src/main/java/fr/iutinfo/skeleton/web/UserViews.java`

**Remarque** : Notez l'*include* qui est fait vers `src/main/webapp/layout/head.jsp` afin de factoriser le code de présentation.

## Authentification

## JDBI
