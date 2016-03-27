[![Build Status](https://travis-ci.org/iut-lille/jersey-skeleton.svg?branch=master)](https://travis-ci.org/iut-lille/jersey-skeleton)

# Création d'un projet en mode étudiant
Sur Github :
- faire un fork du projet 
- ajouter ses camarades dans le projet
- cloner le projet dans /tmp/$USER/workspace
- modifier le nom du projet dans le pom.xml

# Installation de Maven
## Prérequis
### Java 1.7
Vous devez avoir un Java 1.7 minimum d'installer sur votre ordinateur.

Pour ubuntu ou debian :

    sudo apt-get install openjdk-7-jdk

Pour Windows : https://www.java.com/fr/download/

### Maven
#### Linux
Pour installer maven depuis ubuntu ou debian, vous pouvez installer le paquet mvn directement depuis les dépots officiels :

    sudo apt-get install mvn

Créer ensuite un dossier ".m2" dans votre répertoire personnel. Il contiendra le fichier de configuration de maven :

```
mkdir $HOME/.m2
touch $HOME/.m2/settings.xml
```

Modifier ce fichier settings.xml avec votre éditeur préféré (Emacs / vim) et copiez cette configuration :

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                          http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <localRepository>/tmp/$USER/mvn/repository</localRepository>
    <proxies>
      <proxy>
        <id>proxy</id>
        <active>true</active>
        <protocol>http</protocol>
        <host>cache.univ-lille1.fr</host>
        <port>3128</port>
      </proxy>
    </proxies>
</settings>
```

Pour vérifier votre installation, ouvrez un terminal et taper :
  
      mvn -version


**/!\ Si vous n'êtes pas sur les ordinateurs de l'IUT, ou sur le réseau WIFI de Lille1, passer le paramètre proxy.active à "false" !**

#### Windows
Télécharger l'archive maven sur le site officiel : http://maven.apache.org/download.cgi

Décompressez l'archive dans le répertoire où vous souhaitez installer maven.
Je vous conseille ceci : 

     C:\Program Files\Apache\Maven

Voilà, vous avez installé maven. Il faut maintenant configurer les variables d'environnement de Windows pour qu'il l'utilise correctement :

      Panneau de Configuration > Système > Paramètres Avancés > Chercher Variable d'environnement

Une fois sur cette fenètre :
- Vérifiez que la variable `JAVA_HOME` existe et qu'elle pointe bien vers le java JDK installé sur votre ordinateur. Si elle n'existe pas, créez la.
- Créez deux nouvelles variables `M2_HOME` et `MAVEN_HOME` qui pointe vers le dossier d'installation de maven. (C:\Program Files\Apache\Maven de vous suivez les conseils)
- Et enfin, modifier la variable `PATH` en rajoutant ceci à la fin.

      ;%M2_HOME%/bin

Vérifiez en passant que la variable `JAVA_HOME` est présente dans la variable `PATH`. Si non, rajoutez ceci :

      ;%JAVA_HOME%/bin

Pour vérifier votre installation, ouvrez un terminal et taper :
  
      mvn -version


**/!\ Si vous êtes sur un ordinateur de l'IUT ou sur le réseau WIFI de Lille1, modifiez la configuration de Maven en rajoutant les lignes pour paramètrer le proxy selon l'exemple un peu au dessus. Le fichier de configuration devrait se trouver dans le répertoire "conf" du dossier d'installation de maven.**

# Configuration d'Eclipse
Un plugin maven "m2e" existe dans les dépots d'Eclipse.
Rajoutez le dépot "http://download.eclipse.org/releases/indigo/" dans votre Eclipse et installer le plugin "m2e"

Des plugins de développement web sont aussi disponibles sur le dépot "http://download.eclipse.org/webtools/repository/indigo/". Installez "Web Tools Plateform"

Une fois les plugins installer, vous pouvez importer le projet maven en faisaint un "Import > Import existing maven project"

# Test du projet en local 
Pour lancer le projet sur la machine du développeur et visiter les pages web sur http://localhost:8080/

modifier le pom pour ajouter jetty-maven-plugin :

    <project>
      ...
      <build>
        ...
        <plugins>
          ...
          <plugin>
            <groupId>org.eclipse.jetty</groupId>
            <artifactId>jetty-maven-plugin</artifactId>
            <version>9.3.0.M1</version>
          </plugin>
          ...
        </plugins>
        ...
      </build>
      ...
    </project>

Pour lancer un serveur local

    mvn jetty:run


# Informations diverses
## Point de cours

Ce squelette d'application construit une API et 2 types d'interfaces :
- L'api au centre permet de manipuler l'ensemble des données
- Une interface graphique en javascript exploitant l'API
- Une interface graphique en html généré par le serveur
- Dans le projet android-skeleton, une interface graphique android.

### REST

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

### Api

Dans le package fr.iutinfo.skeleton.api vous trouverez l'ensemble des classes de l'api
- Api : le point d'entrée des requêtes vers les url commençant par http://localhost:8080/v1/ la directive `packages("fr.iutinfo.skeleton.api");` permet d'enregistrer toutes les classes de ressources du package.
- UserResource : match les urls de la forme http://localhost:8080/v1/user et permet de manipuler des ressources en mémoire de type User
- UserDBResource : match les urls de la forme http://localhost:8080/v1/userdb et permet de manipuler des ressources en base de données de type User
- UserDao : permet de lire et d'écrire en base des "User"
- User : la classe métier utilisé dans cet exemple
- BDDFactory : permet d'obtenir un accès à la base de données.
 
### Javascript

Dans `src/webapp/all.js` et `src/webapp/index.html` vous trouverez un exemple d'interface graphique en javascript mis en forme avec [bootstrap](http://getbootstrap.com/).
    
### Html

Le plugin template MVC de jersey permet de générer du html depuis divers fichiers.
Les pages http://localhost:8080/html/user sont construites depuis : 
- `src/main/webapp/fr/iutinfo/skeleton/web/UserViews/index.jsp`
- `src/main/java/fr/iutinfo/skeleton/web/UserViews.java`

Notez l'include qui est fait vers `src/main/webapp/layout/head.jsp` afin de factoriser le code de présentation.

### Authentification

### JDBI

### Slf4j

### Docker

[Docker](http://docker.com/) est un logiciel de gestion de conteneurs applicatifs, il permet d'empaqueter une application et ses dépendances dans un conteneur afin de l'exécuter sur n'importe quel serveur Linux.

Le fichier `Dockerfile` est la recette de cuisine permettant de construire ce conteneur.

Le [hub docker](https://hub.docker.com) est une gigantesque bibliothèque de conteneurs libre, vous pouvez y ajouter le votre, son nom sera alors de la forme : `utilisateur/application`. Vous pouvez aussi demander au hub docker de construire automatique votre conteneur à chaque commit.

Pour lancer la construction sur les machines de l'IUT il faut spécifier le proxy sur la ligne de commande : 

    docker build --build-arg http_proxy=http://cache.univ-lille1.fr:3128 \
    --build-arg https_proxy=http://cache.univ-lille1.fr:3128 -t utilisateur/application .

Pour envoyer le conteneur sur le hub docker

    docker push utilisateur/application

Pour lancer le conteneur en local et visualiser le site sur http://localhost:8080/

    docker run -it -p 8080:8080 utilisateur/application

Pour déployer votre conteneur sur internet, vous pouvez utiliser la plateforme [Deliverous](http://deliverous.com). Pour ce faire, après avoir créé un compte sur la plateforme, vous pouvez ajouter à la racine de votre projet git un fichier `Deliverous` inspiré de celui présent dans ce dépos.


## Liens utiles
- Documentation de Jersey :https://jersey.java.net/documentation/latest/index.html  
- Explication de JAX-RS avec Jersey : http://coenraets.org/blog/2011/12/restful-services-with-jquery-and-java-using-jax-rs-and-jersey/
- Aide sur les IHM en java avec Jersey : http://thierry-leriche-dessirier.developpez.com/tutoriels/java/client-swing-menus-filtres-rest-service/
- Framework HTML/CSS/JS Bootstrap : http://getbootstrap.com/
