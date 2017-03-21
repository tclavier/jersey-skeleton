[![Build Status](https://travis-ci.org/iut-lille/jersey-skeleton.svg?branch=master)](https://travis-ci.org/iut-lille/jersey-skeleton)

# Squelette d'application web pour la semaine agile @ Univ-Lille

La semaine agile conclut la formation de DUT Informatique du département Informatique de l'IUT "A" de l'Université de Lille - Sciences & Technologies. L'objectif de cette semaine est l'initiation à la démarche agile via la conception d'une application web reposant sur un serveur `REST`, un client web (HTML, CSS et *JavaScript*) et un client mobile (*Androïd*).

Ce projet implique plusieurs technologies permettant de mettre en oeuvre une intégration continue, c'est-à-dire une automatisation de la vérification (via des tests) et du déploiement du logiciel en production.

Le squelette de logiciel proposé ici repose sur l'exploitation de plusieurs technologies facilitant la conception, la compilation, l'éxecution des tests et le déploiement d'applications web.

Pour ce qui concerne la gestion du cycle de vie du logiciel, voici les principaux outils mobilisés :

- [maven](https://maven.apache.org) pour décrire les dépendances et automatiser la construction, les tests et l'exécution d'un projet *Java*,
- [Travis CI](https://travis-ci.org) pour la gestion de l'intégration continue (ie. l'exécution des tests et le déploiement en cas de succès)
- [Docker](https://www.docker.com) pour la gestion du contexte applicatif permettant le déploiement de l'application créée (ie. un UNIX avec un serveur HTTP afin de déployer le serveur),

Pour ce qui est des librairies utilisées pour le développement de l'application web, voici les principales technologies :

- [Jersey](https://jersey.java.net) pour développer le serveur `REST` en *Java*, cette librairie étant l'implémentation du standard `JAX-RS` définissant l'API `REST` pour *Java*,
- [jQuery](https://jquery.com) pour faciliter la manipulation de l'arbre `DOM` et les appels `AJAX`,
- [Boostrap](http://getbootstrap.com) pour faciliter la gestion d'IHM adaptatives (ou *responsive* pour utiliser le terme à la mode ;)), 
- [Java Database Binding Interface](http://jdbi.org) (JDBI) pour faciliter la transition entre objets et base de données,

Le but de ce squelette de projet est de fournir une base facilitant la prise en main de ces multiples techologies afin de pouvoir déployer votre projet dès le premier *sprint* :)

Il est fortement conseillé de lire la [documentation sur l'architecture générale du projet et les éléments techniques](doc/architecture.md)

# Étapes à suivre pour démarrer !

Si vous travaillez sur votre machine personnelle :

1. installer le JDK 1.8 (si vous travaillez sur votre machine personnelle)
  - sous Linux : `sudo apt-get install openjdk-8-jdk`
  - sous Winwin/OS X : [https://www.java.com/fr/download](https://www.java.com/fr/download)
- installer `maven` : 
  - sous Linux : `sudo apt-get install mvn`
  - sous OS X : d'abord installer [brew](https://brew.sh) (équivalent d'`apt`), puis: `brew install maven`
  - [sous Windows](doc/maven-winwin.md) : **INTERDIT DE TRAVAILLER SOUS WINDOWS !!!**

Maintenant que les outils nécessaires sont installés :

1. sur le site `git-iut.univ-lille1.fr` ou `github.com` :
  - faire un *fork* du projet [`jersey-skeleton`](https://github.com/tclavier/jersey-skeleton)
  - ajouter les membres de l'équipe agile
- sur votre machine :
  - cloner le projet dans `~/workspace/`
  - modifier le nom du projet dans le `pom.xml`
  - configurer le *proxy* pour `maven`
  - importer le projet dans `eclipse` en tant que *Projet maven déjà existant*
  - modifier les noms de paquetages
- configuration de `maven`
  - s'il n'existe pas déjà: `mkdir $HOME/.m2`
  - création du fichier de configuration: `touch $HOME/.m2/settings.xml`
  - avec votre éditeur de texte préféré, mettez ce qu'il y a ci-dessous dans `setting.xml`
  - finalement, vérifiez votre installation : `mvn -version`

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                          http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <localRepository>/tmp/USER/mvn/repository</localRepository>
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
**/!\ Si vous êtes sur un ordinateur de l'IUT ou sur le réseau WIFI de Lille1, modifiez la configuration de `maven` en rajoutant les lignes pour paramètrer le *proxy* selon l'exemple. Le fichier de configuration devrait se trouver dans le répertoire `conf` du dossier d'installation de maven.**

**/!\ Si vous n'êtes pas sur les ordinateurs de l'IUT, ou sur le réseau WIFI de Lille1, passer le paramètre `proxy.active` à `false` !**

# Tester votre projet en local

Dans le répertoire du projet, vous pouvez lancer le serveur en local sur la machine de développement:

1. `mvn jetty:run`
2. pointez ensuite votre navigateur sur : [http://localhost:8080](http://localhost:8080)

Si tout se passe bien, vous devriez voir la page d'accueil du projet.

# Test de déploiement sur `deliverous.com`

Reportez vous [au document décrivant l'usage de Docker](doc/docker.md)

# Quelques liens utiles
- [Documentation de `Jersey`](https://jersey.java.net/documentation/latest/index.html)
- [Explication de JAX-RS avec Jersey](http://coenraets.org/blog/2011/12/restful-services-with-jquery-and-java-using-jax-rs-and-jersey)
- [Aide sur les IHM en java avec Jersey](http://thierry-leriche-dessirier.developpez.com/tutoriels/java/client-swing-menus-filtres-rest-service/)
- [Framework HTML/CSS/JS Bootstrap](http://getbootstrap.com)
