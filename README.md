# création du projet
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DgroupId=fr.iutinfo.2t -DartifactId=jersey-skeleton
cd jersey-skeleton
git init 
vi .gitignore
    
    *~
    .project
    .classpath
    .settings

# Configurer Eclipse
- plugin maven m2e depuis http://download.eclipse.org/releases/indigo/

