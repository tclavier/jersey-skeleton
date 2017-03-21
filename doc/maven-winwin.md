# Installation de `maven` sous Winwin

Télécharger l'archive `maven` sur le site officiel : [http://maven.apache.org/download.cgi](http://maven.apache.org/download.cgi)

Décompressez l'archive dans le répertoire où vous souhaitez installer `maven`. Conseil : 

     C:\Program Files\Apache\Maven

Voilà, vous avez installé `maven`. Il faut maintenant configurer les variables d'environnement de Windows pour qu'il l'utilise correctement :

      Panneau de Configuration > Système > Paramètres Avancés > Chercher Variable d'environnement

Une fois sur cette fenêtre :

- Vérifiez que la variable `JAVA_HOME` existe et qu'elle pointe bien vers le java JDK installé sur votre ordinateur. Si elle n'existe pas, créez la.
- Créez deux nouvelles variables `M2_HOME` et `MAVEN_HOME` qui pointe vers le dossier d'installation de `maven`. (`C:\Program Files\Apache\Maven` ... si vous suivez les conseils)
- Et enfin, modifier la variable `PATH` en rajoutant ceci à la fin.

    `;%M2_HOME%/bin`

Vérifiez en passant que la variable `JAVA_HOME` est présente dans la variable `PATH`. Si ce n'est pas le cas, rajoutez ceci :

      ;%JAVA_HOME%/bin

Pour vérifier votre installation, ouvrez un terminal et tapez :
  
      mvn -version
