# Application de Gestion Médicale - Doctor App

## Description

Application Spring Boot utilisant **MongoDB** pour gérer les consultations médicales, les patients, les médecins et les médicaments avec des relations NoSQL.

## Prérequis

- Java 25+
- Maven 3.8+
- MongoDB 4.0+

## Installation

### 1. Cloner le projet

```bash
git clone <repository>
cd CC_java
```

### 2. Configurer MongoDB

Assurez-vous que MongoDB est en cours d'exécution sur votre machine.

#### Configuration par défaut
- Host: `localhost`
- Port: `27017`

#### Modifier la configuration (optionnel)

Éditez le fichier `src/main/resources/application.properties` :

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/medical_db
spring.data.mongodb.database=medical_db
```

### 3. Compiler le projet

```bash
mvn clean compile
```

### 4. Lancer l'application

```bash
mvn spring-boot:run
```

L'application sera disponible sur `http://localhost:8080`

## Structure du Projet

```
src/main/java/com/jonathanluco/doctorapp/
├── model/                    # Modèles de données
│   ├── User.java            # Classe parent
│   ├── Patient.java         # Classe Patient
│   ├── Medecin.java         # Classe Médecin
│   ├── Consultation.java    # Classe Consultation
│   ├── Medicament.java      # Classe Médicament
│   └── Prescription.java    # Classe d'association
├── repository/              # Repositories MongoDB
│   ├── PatientRepository.java
│   ├── MedecinRepository.java
│   ├── ConsultationRepository.java
│   └── MedicamentRepository.java
├── service/                 # Services métier
│   ├── PatientService.java
│   ├── MedecinService.java
│   ├── ConsultationService.java
│   └── MedicamentService.java
├── controller/              # Contrôleurs REST
│   ├── PatientController.java
│   ├── MedecinController.java
│   ├── ConsultationController.java
│   └── MedicamentController.java
└── security/                # Configuration sécurité & JWT
```

## Modèles de Données

### Héritage
```
User
├── Patient (numeroSS, nomPatient)
└── Medecin (matricule, nomMedecin)
```

### Relations
1. **Patient ↔ Consultation** (1,N) - "Assiste"
2. **Medecin ↔ Consultation** (0,N) - "Donne"
3. **Medicament ↔ Consultation** (0,N) - "Prescrit" (avec nbPrise)

## Exemple d'utilisation

### 1. Créer un patient

```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{
    "numeroSS": "123456789012",
    "nomPatient": "Jean Dupont",
    "username": "jean_dupont",
    "password": "password123"
  }'
```

### 2. Créer un médecin

```bash
curl -X POST http://localhost:8080/api/medecins \
  -H "Content-Type: application/json" \
  -d '{
    "matricule": "DOC001",
    "nomMedecin": "Dr. Martin Lefebvre",
    "username": "dr_martin",
    "password": "password123"
  }'
```

### 3. Créer un médicament

```bash
curl -X POST http://localhost:8080/api/medicaments \
  -H "Content-Type: application/json" \
  -d '{
    "code": "MED001",
    "libelle": "Aspirin 500mg"
  }'
```

### 4. Créer une consultation

```bash
curl -X POST http://localhost:8080/api/consultations \
  -H "Content-Type: application/json" \
  -d '{
    "numeroConsultation": "CONS001",
    "date": "2026-07-06T14:30:00",
    "patientAssiste": {"numeroSS": "123456789012"},
    "medecinDonne": {"matricule": "DOC001"}
  }'
```

### 5. Ajouter une prescription à une consultation

```bash
curl -X POST http://localhost:8080/api/consultations/CONS001/prescriptions \
  -H "Content-Type: application/json" \
  -d '{
    "codeMedicament": "MED001",
    "nbPrise": 3
  }'
```

## Tests

Lancer les tests unitaires :

```bash
mvn test
```

## Documentation Complète

Voir le fichier `MEDICAL_API_DOCUMENTATION.md` pour la documentation complète de l'API REST.

## Technologies Utilisées

- **Spring Boot 4.1.0** - Framework Web et Data
- **Spring Data MongoDB** - Accès MongoDB
- **Spring Security** - Authentification et sécurité
- **JWT** - Json Web Token pour l'authentification
- **Validation Jakarta** - Validation des données
- **Maven** - Gestion des dépendances

## Licences

Voir LICENSE file.

## Auteur

Jonathan Luco
