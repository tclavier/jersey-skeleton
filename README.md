# Création d'un projet en mode étudiant

Sur github :
- faire un fork du projet 
- ajouter ses camarades dans le projet
- cloner le projet dans /tmp/$USER/workspace
- modifier le nom du projet dans le pom

## Eclipse
Plugin maven m2e depuis http://download.eclipse.org/releases/indigo/ puis import existing maven project

WTP : http://download.eclipse.org/webtools/repository/indigo/
* Web Tools Plateform 3.3.2 
  * JavaScript Development Tools
  * Web Page Editor

## Liens divers

* https://jersey.java.net/documentation/latest/index.html  
* http://coenraets.org/blog/2011/12/restful-services-with-jquery-and-java-using-jax-rs-and-jersey/
* http://thierry-leriche-dessirier.developpez.com/tutoriels/java/client-swing-menus-filtres-rest-service/
* framework HTML/CSS/JS : http://getbootstrap.com/
* Moteur de template : http://www.stringtemplate.org/

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
    
# Autre à détailler

C'est quoi une architecture REST ? exemple d'urls

Client JS voir index.html et all.js

MVC template : http://localhost:8080/html/user
Construit depuis : 
- src/main/webapp/fr/iutinfo/skeleton/web/UserViews/index.jsp 
- src/main/java/fr/iutinfo/skeleton/web/UserViews.java

Client Android

JDBI

SLF4J

# TODO

* Compléter la doc
* 