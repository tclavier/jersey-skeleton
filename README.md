# Création d'un projet en mode étudiant

Sur gitlab, faire un fork du projet puis modifier le nom du projet dans le pom

# création du projet depuis rien
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

## Conf spécifique pour l'IUT

    git config --global credential.helper cache
    git config http.sslVerify "false"

modifier le pom 

- pour avoir junit 4.12
- java 1.7

# Eclipse

- plugin maven m2e depuis http://download.eclipse.org/releases/indigo/
- import existing maven project

Dans le pom ajouter la dépendance jersey

# Test du projet

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
    
