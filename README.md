# création du projet
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DgroupId=fr.iutinfo.2t -DartifactId=jersey-skeleton
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

# Configurer Eclipse
- plugin maven m2e depuis http://download.eclipse.org/releases/indigo/

