# Création d'un projet en mode étudiant

Sur github :
- faire un fork du projet 
- ajouter ses camarades dans le projet
- cloner le projet dans /tmp/$USER/workspace
- modifier le nom du projet dans le pom

## Eclipse
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
        </plugins>
      </build>

Pour lancer un serveur local

    mvn jetty:run
    
# Création du projet depuis rien
## Maven
    
    mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DgroupId=fr.iutinfo -DartifactId=jersey-skeleton

## Init du git

    cd jersey-skeleton
    git init 
    vi .gitignore
    
    *~
    .project
    .classpath
    .settings


    git add ....
    git commit -m "Init empty project"
    git remote add origin git@github.com:tclavier/jersey-skeleton.git
    git push -u origin master

## Init du projet

modifier le pom pour avoir les bonnes versions de jUnit et java.

- pour avoir junit 4.12
- java 1.7

# Eclipse

- plugin maven m2e depuis http://download.eclipse.org/releases/indigo/
- import existing maven project

Dans le pom ajouter la dépendance jersey

