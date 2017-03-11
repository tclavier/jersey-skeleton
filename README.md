[![Build Status](https://travis-ci.org/iut-lille/jersey-skeleton.svg?branch=master)](https://travis-ci.org/iut-lille/jersey-skeleton)

# Création d'un projet en mode étudiant

Sur Gitlab :

- faire un fork du projet 
- ajouter ses camarades dans le projet

Sur votre machine :

- cloner le projet dans `~/workspace/`
- modifier le nom du projet dans le pom.xml
- configurer le proxy pour maven
- importer le projet dans eclipse en tant que projet maven déjà existant
- modifier les noms de packages

# Installation de maven sur une machine perso
## Prérequis
### Java 1.7
Vous devez avoir un Java 1.7 minimum d'installé sur votre ordinateur.

Pour ubuntu ou debian :

    sudo apt-get install openjdk-7-jdk

Pour Windows : https://www.java.com/fr/download/

## Maven
### Linux
Pour installer maven depuis ubuntu ou debian, vous pouvez installer le paquet mvn directement depuis les dépots officiels :

    sudo apt-get install mvn

### Windows
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


**/!\ Si vous êtes sur un ordinateur de l'IUT ou sur le réseau WIFI de Lille1, modifiez la configuration de Maven en rajoutant les lignes pour paramètrer le proxy selon l'exemple. Le fichier de configuration devrait se trouver dans le répertoire "conf" du dossier d'installation de maven.**

# Configuration
## Maven

Créer ensuite un dossier ".m2" dans votre répertoire personnel s'il n'existe pas déjà. Il contiendra le fichier de configuration de maven :

    mkdir $HOME/.m2
    touch $HOME/.m2/settings.xml

Modifier ou créer le fichier `settings.xml` avec votre éditeur préféré (Emacs / vim) et copiez cette configuration :

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

Pour vérifier votre installation, ouvrez un terminal et taper :
  
      mvn -version


**/!\ Si vous n'êtes pas sur les ordinateurs de l'IUT, ou sur le réseau WIFI de Lille1, passer le paramètre proxy.active à "false" !**

## Eclipse

Avec Éclipse Mars, le plugin est installé de base, l'import peut se faire directement !**

# Test du projet en local

Dans le répertoire du serveur, pour lancer le projet sur la machine du développeur et visiter les pages web sur http://localhost:8080/

    mvn jetty:run

Ce qui lance un serveur local

# Liens utiles
- [Points de cours](doc/cours.md)
- Documentation de Jersey :https://jersey.java.net/documentation/latest/index.html
- Explication de JAX-RS avec Jersey : http://coenraets.org/blog/2011/12/restful-services-with-jquery-and-java-using-jax-rs-and-jersey/
- Aide sur les IHM en java avec Jersey : http://thierry-leriche-dessirier.developpez.com/tutoriels/java/client-swing-menus-filtres-rest-service/
- Framework HTML/CSS/JS Bootstrap : http://getbootstrap.com/
