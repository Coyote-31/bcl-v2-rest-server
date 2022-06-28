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

![Architecture de l'application](https://i.imgur.com/C0z5X92.png)

## 🗃 Base de données :

La base de données est développée avec **MySQL v8.0**.
Elle est intégrée à l'application via des **classes JPA** et l'**ORM Hibernate** de Spring.
Et l'application présente ou recoit les données via des DTOs.

#### Diagramme de l'architecture de la base de données :

![Diagramme de l'architecture de la base de données](https://i.imgur.com/jrJgCXJ.png)

## 🛠 Déploiement :

Pour déployer correctement l'application veuillez suivre ces étapes :

#### 1. Base de données :

La création de la base de données se fait grâce à l'utilisation d'un des script SQL qui se trouve dans le dossier [`db_dumps`](https://github.com/Coyote-31/big_city_library.rest_server/tree/master/db_dumps) :

- `CreateWithData` :
Création de la **base de données** avec toutes les **tables** et un jeu de **données de démonstration**.

- `Create` :
Création de la **base de données** avec toutes les **tables** vierges de données.

#### 2. Données de connexion à la BDD :

Lorsque la base de données est prête.
Pour faire la connexion entre la BDD et l'application il faut changer le fichier [`application-dev_EXEMPLE.properties`](https://github.com/Coyote-31/big_city_library.rest_server/blob/master/rest_server_controller/src/main/resources/application-dev_EXEMPLE.properties) du module *rest_server_controller* en le renommant `application-dev.properties` et changer les données suivante en fonction des attributs de votre serveur de BDD:
- `spring.datasource.url` : Mettre l'URL du serveur de BDD.
- `spring.datasource.username` : Mettre le nom d'utilisateur du serveur de BDD.
- `spring.datasource.password` : Mettre le mot de passe du serveur de BDD.

#### 3. Données d'envoi de mail :

Pour que l'application puisse envoyer les mails de relance aux utilisateurs qui sont en retards pour le retour d'un prêt , il faut changer les données de connexion à la boite mail.

Pour cela il faut modifier le fichier [`application-dev_EXEMPLE.properties`](https://github.com/Coyote-31/big_city_library.rest_server/blob/master/rest_server_controller/src/main/resources/application-dev_EXEMPLE.properties) du module *rest_server_controller* :

- Renommer le fichier `application-dev_EXEMPLE.properties` en `application-dev.properties` ou créer une copie avec ce nom.

- Modifier les données de `spring.mail.username`, `spring.mail.password` avec les bonnes valeurs pour une adresse Gmail. Sinon regarder la documentation de votre serveur mail et adapter le fichier de configuration.

#### 4. Packaging Maven :

Pour compiler et packager l'application dans un fichier `.war`, il faut utiliser le goal [`install`](https://maven.apache.org/plugins/maven-install-plugin/) de Maven avec la commande `mvn install`. Le fichier se créé alors dans le dossier *target* du module *rest_server_controller*.
> On peut aussi utiliser le goal [`deploy`](https://maven.apache.org/plugins/maven-deploy-plugin/) mais cela demande de configurer le fichier POM, ce que je ne développerai pas ici.

#### 5. Serveur d'application :

La dernière étape est de mettre en ligne le serveur **[Apache TomEE v9.0](https://tomee.apache.org/)**.

Lorsque ce dernier est en ligne, dans l'interface du serveur Apache cliquer sur le bouton `Manager App`.
Après s'être identifier trouver la partie `Deployer > Fichier WAR à déployer` et y déployer le fichier `.war` précédemment généré à l'étape 4.

✅ **Bravo !** *L'application web est maintenant 100% fonctionnelle !*
