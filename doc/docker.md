# Docker

[Docker](http://docker.com/) est un logiciel de gestion de conteneurs applicatifs, il permet d'empaqueter une application et ses dépendances dans un conteneur afin de l'exécuter sur n'importe quel serveur Linux. Le nom du projet correspond au concept des conteneurs utilisés dans le transport de marchandises qui a permis de standardiser la forme des conteneurs facilitant la logistique au niveau des ports répartis sur l'ensemble de la planète.

Le fichier `Dockerfile` est la recette de cuisine permettant de construire ce conteneur.

Le [hub docker](https://hub.docker.com) est une gigantesque bibliothèque de conteneurs libre, vous pouvez y ajouter le vôtre, son nom sera alors de la forme : `utilisateur/application`. Vous pouvez aussi demander au *hub docker* de construire automatiquement votre conteneur à chaque *commit*.

Pour lancer la construction sur les machines de l'IUT il faut spécifier le proxy sur la ligne de commande :

    docker build --build-arg http_proxy=http://cache.univ-lille1.fr:3128 \
    --build-arg https_proxy=http://cache.univ-lille1.fr:3128 -t utilisateur/application .

Pour lancer le conteneur en local et visualiser le site sur `http://localhost:8080`, il faut utiliser la commande suivante :

    docker run -it -p 8080:8080 utilisateur/application

Pour déployer votre conteneur sur internet, vous pouvez utiliser la plateforme [Deliverous](http://deliverous.com). Pour ce faire, après avoir créé un compte sur la plateforme, vous pouvez ajouter à la racine de votre projet `git` un fichier `Deliverous` inspiré de celui présent dans ce dépôt.
