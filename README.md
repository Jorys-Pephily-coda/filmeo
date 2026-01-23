# Filmeo - Plateforme de Référencement de Films et Séries

## Description

Filmeo est une plateforme web de référencement de films et séries, inspirée d'IMDB. Elle permet aux utilisateurs de rechercher des œuvres audiovisuelles, consulter leurs détails, noter et commenter les productions ainsi que les artistes, et gérer une liste personnalisée de contenus à regarder.

## Fonctionnalités

### Gestion des Productions
- Référencement de films et séries avec leurs métadonnées complètes
- Informations détaillées : titre, réalisateur, casting, genres, pays producteurs
- Pour les séries : nombre de saisons, d'épisodes, et statut (en cours/terminée)
- Disponibilité sur les plateformes de streaming avec dates de péremption

### Gestion des Artistes
- Fiches complètes des acteurs et réalisateurs
- Informations biographiques : nom, prénom, dates de naissance/décès, nationalité, genre
- Un artiste peut être à la fois acteur et réalisateur

### Plateformes de Streaming
- Catalogue des plateformes disponibles
- Suivi de la disponibilité des productions sur chaque plateforme
- Gestion des dates d'expiration de disponibilité

### Fonctionnalités Utilisateur
- Inscription et authentification sécurisée
- Recherche de productions par titre ou genre
- Système de notation (0-10) et commentaires pour :
  - Les films et séries
  - Les acteurs et réalisateurs
- Liste personnalisée "À voir"
  - Ajout/suppression de productions
  - Retrait automatique lors de la notation d'une production

## Modèle de Données

### Entités Principales
- **User** : Gestion des comptes utilisateurs
- **Production** : Table mère pour films et séries
- **Series** : Attributs spécifiques aux séries (saisons, épisodes, statut)
- **Artist** : Acteurs et réalisateurs
- **Genre** : Catégories de productions
- **Country** : Pays de production et nationalités
- **Streaming_Platform** : Plateformes de diffusion
- **Availability** : Disponibilité des productions sur les plateformes
- **Note_Production** : Évaluations des productions par les utilisateurs
- **Note_Artist** : Évaluations des artistes par les utilisateurs

### Tables de Liaison
- **Casting** : Association productions-artistes
- **Production_Genre** : Association productions-genres
- **WatchLater** : Liste personnalisée des utilisateurs

## Technologies

- **Backend** : Java Spring Boot ![Spring Boot](src/main/resources/static/svg/LogosSpring.svg)
- **Base de données** : MariaDB ![MariaDB](src/main/resources/static/svg/LogosMariadb.svg)
- **Frontend** : Thymeleaf ![Thymeleaf](src/main/resources/static/svg/LogosThymeleaf.svg)

## Architecture

Le projet suit une architecture MVC avec :
- Modélisation relationnelle normalisée
- Authentification et gestion des sessions utilisateur
- API RESTful pour les opérations CRUD
- Séparation claire entre la logique métier et la couche de présentation

## Objectifs Pédagogiques

Ce projet a été développé dans le cadre d'un cours et vise à renforcer les compétences en :
- Modélisation de bases de données relationnelles
- Développement full-stack (frontend et backend)
- Authentification et gestion des permissions
- Implémentation de fonctionnalités interactives utilisateur
- Système de recherche et filtrage avancé

## Installation

```bash
git clone https://github.com/Jorys-Pephily-coda/filmeo.git

# Configurer la base de données

# Lancer l'application
```