## REST

Dans une architecture [REST](https://fr.wikipedia.org/wiki/Representational_State_Transfer) chaque ressource est accessible par une unique URL, ce sont les verbes HTTP qui définissent les actions CRUD (*Create, Read, Update, Delete*) qu'il est possible de réaliser  sur une ressource.
Mise à part les URLs de créations, toutes les URLs peuvent être appelé autant de fois que l'on veux avec exactement le même résultat.

Pour des ressources simples, le schéma est le suivant :

| Verbe | URL | Action |
|-------|-----|--------|
| POST  | /foo | création d'un Foo |
| GET   | /foo | liste de tous les Foo |
| GET   | /foo?tel=xxx | lit le Foo dont le téléphone est "xxx" |
| GET   | /foo/{id} | lecture du Foo {id} |
| PUT   | /foo/{id} | met à jour le Foo identifié par {id} |
| DELETE| /foo/{id} | supprime le Foo identifié par {id} |

Pour des ressources composées, les URLs vont définir le contenant, par exemple : `/user/id_user/tel/id_tel` pour accéder au téléphone `id_tel` de l'utilisateur `id_user`.

**/!\ Attention** : *identifier le téléphone sous cette forme sous entend que dans notre application, il n'existe pas de téléphone sans propriétaire.*

