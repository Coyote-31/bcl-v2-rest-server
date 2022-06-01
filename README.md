# Big City Library : REST Server

## ✨ Présentation :

Ce projet a pour but la création d’un ensemble d’outils numériques pour les différents acteurs des bibliothèques d'une ville.
1. Un Back-end : RESTFull Serveur (Projet développé ici [GitHub](https://github.com/Coyote-31/big_city_library.rest_server))
2. Un Front-end : RESTFull Client Public ([GitHub](https://github.com/Coyote-31/big_city_library.rest_client_public))
3. Un envoi automatique de mails : BATCH ([GitHub](https://github.com/Coyote-31/batch))

> **IMPORTANT:**
> Ce projet fait partie du cursus de formation "Développeur J2EE" de [OpenClassroom](https://openclassrooms.com/).
> Les informations qu'il comporte ne doivent pas être utilisées dans le cadre d'une pratique réelle. 
> Les informations présentées ici ne servent qu'à illustrer le projet pour le rendre le plus proche possible de la réalité.

## 📱 Application :

L'application est un serveur de type RESTFull développé en **Java EE** sur l'**IDE Visual Studio Code**.
Cette application utilise le framework Spring et les dépendences MapStruct & Lombok.
Elle est packagée via **[Maven](https://maven.apache.org/index.html)** dans un fichier `.war` pour être déployée sur un serveur compatible comme : **[Apache TomEE v9.0](https://tomee.apache.org/)**.

#### Aperçu de l'application :

![Aperçu de l'application](https://i.imgur.com/uodji7N.jpg)

#### Architecture de l'application :

![Architecture de l'application](...)

## 🗃 Base de données :

La base de données est développée avec **MySQL v8.0**.
Elle est intégrée à l'application via des **classes JPA** et l'**ORM Hibernate** de Spring.
Et l'application présente ou recoit les données via des DTOs.

#### Diagramme de l'architecture de la base de données :

![Diagramme de l'architecture de la base de données](https://i.imgur.com/jrJgCXJ.png)

## 🛠 Déploiement :

Pour déployer correctement l'application veuillez suivre ces étapes :

#### 1. Base de données :

--------vvv-------- TODO -------vvv--------
