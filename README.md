# 🏥 MediExpert - Plateforme de Télé-Expertise Médicale

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-10-blue.svg)](https://jakarta.ee/)
[![Maven](https://img.shields.io/badge/Maven-3.9.11-red.svg)](https://maven.apache.org/)
[![Hibernate](https://img.shields.io/badge/Hibernate-6.2-green.svg)](https://hibernate.org/)
[![Tomcat](https://img.shields.io/badge/Tomcat-11.0.11-yellow.svg)](https://tomcat.apache.org/)

## 📋 Table des Matières

- [À Propos](#-à-propos)
- [Contexte du Projet](#-contexte-du-projet)
- [Fonctionnalités](#-fonctionnalités)
- [Architecture Technique](#-architecture-technique)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Utilisation](#-utilisation)
- [Contributeurs](#-contributeurs)

## 🎯 À Propos

**MediExpert** est une plateforme web de télé-expertise médicale développée en Java EE qui optimise le parcours patient en facilitant la coordination entre médecins généralistes et spécialistes. Le système permet une collaboration médicale à distance pour une meilleure qualité de soins.

### Objectifs

- Faciliter la communication entre professionnels de santé
- Optimiser la prise en charge des patients
- Gérer efficacement les demandes d'expertise médicale
- Assurer le suivi des consultations et des coûts

## 📖 Contexte du Projet

Ce projet a été développé dans le cadre d'une formation en développement Java EE. Il vise à créer un système complet de gestion de télé-expertise médicale permettant :

- L'enregistrement et le suivi des patients
- La création de consultations médicales
- La demande d'avis spécialisés
- La gestion des créneaux horaires
- Le calcul automatique des coûts de soins

### Parcours Patient

1. **Accueil par l'infirmier** : Enregistrement et mesure des signes vitaux
2. **File d'attente** : Intégration automatique pour consultation
3. **Consultation généraliste** : Examen et création du dossier
4. **Décision** :
    - Prise en charge directe (diagnostic + traitement)
    - Demande d'expertise spécialisée (télé-consultation)

## ✨ Fonctionnalités

### 👨‍⚕️ Module Infirmier

- ✅ Enregistrement des patients (nouveaux ou existants)
- ✅ Saisie des signes vitaux (tension, fréquence cardiaque, température, etc.)
- ✅ Consultation de la liste des patients du jour
- ✅ Gestion de la file d'attente
- ✅ Tri et filtrage avec Stream API

### 👨‍⚕️ Module Médecin Généraliste

- ✅ Consultation de la file d'attente
- ✅ Création de consultations avec observations
- ✅ Ajout d'actes techniques médicaux
- ✅ Demande d'expertise auprès de spécialistes
- ✅ Recherche de spécialistes par spécialité et tarif (Stream API)
- ✅ Visualisation des créneaux disponibles
- ✅ Calcul automatique des coûts (Lambda expressions)
- ✅ Clôture de consultation

### 👨‍⚕️ Module Médecin Spécialiste

- ✅ Configuration du profil (tarif, spécialité)
- ✅ Gestion des créneaux horaires (30 min par créneau)
- ✅ Consultation des demandes d'expertise
- ✅ Filtrage par statut et priorité (Stream API)
- ✅ Réponse aux demandes avec avis et recommandations
- ✅ Tableau de bord avec statistiques
- ✅ Gestion des indisponibilités

## 🏗️ Architecture Technique

### Stack Technologique

**Backend**
- Java 17
- Jakarta EE 10
- Hibernate 6.2 (JPA)
- Servlets & JSP/JSTL
- Maven 3.9

**Sécurité**
- BCrypt pour le hachage des mots de passe
- Protection CSRF
- Sessions HTTP stateful

**Base de Données**
- PostgreSQL 18
- JPA/Hibernate pour l'ORM
- Scripts SQL d'initialisation

**Serveur**
- Apache Tomcat 11.0.11

**Tests**
- JUnit 5

### Design Patterns Utilisés

- **MVC** : Séparation des préoccupations (Modèle-Vue-Contrôleur)
- **Repository Pattern** : Abstraction de la couche d'accès aux données
- **Service Layer** : Logique métier encapsulée
- **Singleton** : EntityManager et configurations

## 📦 Prérequis

### Logiciels Requis

- **JDK 17** ou supérieur
- **Apache Maven 3.9+**
- **PostgreSQL 15+**
- **Apache Tomcat 10.1+**
- **Git**

### Connaissances Recommandées

- Java SE/EE
- Servlets & JSP
- JPA/Hibernate
- SQL
- HTML/CSS/JavaScript
- Git

## 🚀 Installation

### 1. Cloner le Projet

```bash
git clone https://github.com/MhamedOuhanane/MediExpert.git
cd mediexpert
````

### 3. Exécuter les Scripts SQL

```bash
psql -U mediexpert_user -d mediexpert -f src/main/resources/sql/schema.sql
psql -U mediexpert_user -d mediexpert -f src/main/resources/sql/data.sql
```

### 4. Configurer l'Application

Créez le fichier `src/main/resources/application.properties` :

```properties
# Database Configuration
db.url=jdbc:postgresql://localhost:5432/mediexpert
db.username=mediexpert_user
db.password=votre_mot_de_passe
db.driver=org.postgresql.Driver

# Hibernate Configuration
hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
hibernate.hbm2ddl.auto=update
hibernate.show_sql=false
hibernate.format_sql=true

# Application Configuration
app.name=MediExpert
app.version=1.0.0
```

### 5. Compiler le Projet

```bash
mvn clean install
```

## 💻 Utilisation

### Accès à l'Application

```
http://localhost:8080/mediexpert
```

### Flux de Travail

#### 1. Module Infirmier

1. Se connecter avec le compte infirmier
2. Rechercher ou créer un patient
3. Saisir les signes vitaux
4. Ajouter à la file d'attente

#### 2. Module Généraliste

1. Se connecter avec le compte généraliste
2. Consulter la file d'attente
3. Sélectionner un patient
4. Créer une consultation
5. Choisir entre :
    - Clôturer directement
    - Demander un avis spécialisé

#### 3. Module Spécialiste

1. Se connecter avec le compte spécialiste
2. Configurer le profil (première connexion)
3. Gérer les créneaux horaires
4. Consulter les demandes d'expertise
5. Répondre avec un avis médical

## 🎓 Livrables

### TICKET-001 (Deadline: 10/10/2025)
- ✅ Module Infirmier complet
- ✅ Module Généraliste (scénario A)
- ✅ Enregistrement patients et consultations

### TICKET-002 (Deadline: 17/10/2025)
- ✅ Module Spécialiste complet
- ✅ Système de télé-expertise
- ✅ Gestion des créneaux horaires
- ✅ Calcul des coûts avec Lambda

## 📈 Améliorations Futures

- [ ] Module Administrateur pour gestion du staff
- [ ] Notifications en temps réel (WebSocket)
- [ ] Export PDF des consultations
- [ ] Système de messagerie intégrée
- [ ] Tableau de bord analytique avancé

## 👥 Contributeurs

- **M'hamed Ouhanane** - Développeur principal - [GitHub](https://github.com/MhamedOuhanane)

## 📧 Contact

Pour toute question ou suggestion :

- Email : mhmdeouhnan60@gmail.com
- LinkedIn : [M'hamed Ouhanane](https://www.linkedin.com/in/m-hamed-ouhanane-986688340/?locale=fr_FR)


---

*Développé pour l'apprentissage du développement Java EE*