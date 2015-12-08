# Création d'un projet en mode étudiant
Sur Github :
- faire un fork du projet 
- ajouter ses camarades dans le projet
- cloner le projet dans /tmp/$USER/workspace
- modifier le nom du projet dans le pom.xml

# Installation de Maven
## Prérequis
Vous devez avoir un Java 1.7 minimum d'installer sur votre ordinateur.

Pour ubuntu/debian :

    sudo apt-get install openjdk-7-jdk

Pour Windows : https://www.java.com/fr/download/

## Ubuntu/Debian
Pour installer maven, vous pouvez installer le paquet mvn directement depuis les dépots officiels :

    sudo apt-get install mvn

Créer ensuite un dossier ".m2" dans votre répertoire personnel. Il contiendra le fichier de configuration de maven :

```
mkdir $HOME/.m2
touch $HOME/.m2/settings.xml
```

Modifier ce fichier settings.xml avec votre éditeur préféré (Emacs) et copiez y cette configuration :

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

## Windows
Télécharger l'archive maven sur le site officiel : http://maven.apache.org/download.cgi

Décompressez l'archive dans le répertoire où vous souhaitez installer maven.
Je vous conseille ceci : 

     C:\Program Files\Apache\Maven

Voilà, vous avez installé maven. Il faut maintenant configurer les variables d'environnement de Windows pour qu'il l'utilise correctement :

      Panneau de Configuration > Système > Paramètres Avancés > Chercher Variable d'environnement

Une fois sur cette fenètre :
- Vérifiez que la variable "JAVA_HOME" existe et qu'elle pointe bien vers le java JDK installé sur votre ordinateur. Si elle n'existe pas, créez la.
- Créez deux nouvelles variables "M2_HOME" et "MAVEN_HOME" qui pointe vers le dossier d'installation de maven. (C:\Program Files\Apache\Maven de vous suivez les conseils)
- Et enfin, modifier la variable "PATH" en rajoutant ceci à la fin.

      ;%M2_HOME%/bin

Vérifiez en passant que la variable JAVA_HOME est présente dans la variable PATH. Si non, rajoutez ceci :

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
- C'est quoi une architecture REST ?
- Exemple d'un client JavaScript dans src/webapp/all.js et index.html
- Exemple de template MVC :http://localhost:8080/html/user
  - Construit depuis : 
    - src/main/webapp/fr/iutinfo/skeleton/web/UserViews/index.jsp 
    - src/main/java/fr/iutinfo/skeleton/web/UserViews.java
- Exemple Android : TODO
- C'est quoi JDBI : TODO
- C'est quoi SLF4J : TODO

## Liens utiles
- Documentation de Jersey :https://jersey.java.net/documentation/latest/index.html  
- Explication de JAX-RS avec Jersey : http://coenraets.org/blog/2011/12/restful-services-with-jquery-and-java-using-jax-rs-and-jersey/
* Aide sur les IHM en java avec Jersey : http://thierry-leriche-dessirier.developpez.com/tutoriels/java/client-swing-menus-filtres-rest-service/
* Framework HTML/CSS/JS Bootstrap : http://getbootstrap.com/
* Moteur de template : http://www.stringtemplate.org/
