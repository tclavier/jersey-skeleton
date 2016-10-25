# Point de cours

## REST

Dans une architechture [REST](https://fr.wikipedia.org/wiki/Representational_State_Transfer) chaque ressource est accessible par une unique URL, ce sont les verbes HTTP qui définissent les actions à faire sur une ressource.
Mise à part les URLs de créations, toutes les URLs peuvent être appelé autant de fois que l'on veux avec exactement le même résultat.

Pour des ressources simples, le schéma est le suivant :

| Verbe | URL | Action |
|-------|-----|--------|
| POST  | /foo | création d'un Foo |
| GET   | /foo | liste de tous les Foo |
| GET   | /foo?tel=xxx | lit le Foo dont le téléphone est "xxx" |
| GET   | /foo/{id} | lecture du Foo {id} |
| PUT   | /foo/{id} | met à jour le Foo identifié par {id} |
| DELETE| /foo/{id} | supprime le Foo identifié par {id} |

Pour des ressources composées, les URLs vont définir le contenant, par exemple : `/user/id_user/tel/id_tel` pour accéder au téléphone `id_tel` de l'utilisateur `id_user`.

*/!\ Attention*, identifier le téléphone sous cette forme sous entend que dans notre application, il n'existe pas de téléphone sans propriétaire

## Api

Dans le package fr.iutinfo.skeleton.api vous trouverez l'ensemble des classes de l'api
- Api : le point d'entrée des requêtes vers les url commençant par http://localhost:8080/v1/ la directive `packages("fr.iutinfo.skeleton.api");` permet d'enregistrer toutes les classes de ressources du package.
- UserResource : match les urls de la forme http://localhost:8080/v1/user et permet de manipuler des ressources en mémoire de type User
- UserDBResource : match les urls de la forme http://localhost:8080/v1/userdb et permet de manipuler des ressources en base de données de type User
- UserDao : permet de lire et d'écrire en base des "User"
- User : la classe métier utilisé dans cet exemple
- BDDFactory : permet d'obtenir un accès à la base de données.

## Javascript

Dans `src/webapp/all.js` et `src/webapp/index.html` vous trouverez un exemple d'interface graphique en javascript mis en forme avec [bootstrap](http://getbootstrap.com/).

## Html

Le plugin template MVC de jersey permet de générer du html depuis divers fichiers.
Les pages http://localhost:8080/html/user sont construites depuis :
- `src/main/webapp/fr/iutinfo/skeleton/web/UserViews/index.jsp`
- `src/main/java/fr/iutinfo/skeleton/web/UserViews.java`

Notez l'include qui est fait vers `src/main/webapp/layout/head.jsp` afin de factoriser le code de présentation.

## Authentification



## JDBI

## Slf4j

## Docker

[Docker](http://docker.com/) est un logiciel de gestion de conteneurs applicatifs, il permet d'empaqueter une application et ses dépendances dans un conteneur afin de l'exécuter sur n'importe quel serveur Linux.

Le fichier `Dockerfile` est la recette de cuisine permettant de construire ce conteneur.

Le [hub docker](https://hub.docker.com) est une gigantesque bibliothèque de conteneurs libre, vous pouvez y ajouter le votre, son nom sera alors de la forme : `utilisateur/application`. Vous pouvez aussi demander au hub docker de construire automatique votre conteneur à chaque commit.

Pour lancer la construction sur les machines de l'IUT il faut spécifier le proxy sur la ligne de commande :

    docker build --build-arg http_proxy=http://cache.univ-lille1.fr:3128 \
    --build-arg https_proxy=http://cache.univ-lille1.fr:3128 -t utilisateur/application .

Pour lancer le conteneur en local et visualiser le site sur http://localhost:8080/

    docker run -it -p 8080:8080 utilisateur/application

Pour déployer votre conteneur sur internet, vous pouvez utiliser la plateforme [Deliverous](http://deliverous.com). Pour ce faire, après avoir créé un compte sur la plateforme, vous pouvez ajouter à la racine de votre projet git un fichier `Deliverous` inspiré de celui présent dans ce dépos.

