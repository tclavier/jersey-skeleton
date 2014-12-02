# création du projet
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

modifier le pom 

- pour avoir junit 4.11
- java 1.6

# Eclipse

- plugin maven m2e depuis http://download.eclipse.org/releases/indigo/
- import existing maven project

Dans le pom ajouter :
- la dépendance jersey
- la dépendance jetty

