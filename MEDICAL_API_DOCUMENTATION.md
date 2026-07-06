# Documentation API - Système de Gestion Médical (NoSQL)

## Architecture

### Modèles de Données

```
User (classe parent)
├── Patient (extends User)
│   └── PK: numeroSS, nomPatient
├── Medecin (extends User)
│   └── PK: matricule, nomMedecin
├── Consultation
│   ├── PK: numeroConsultation
│   ├── date: LocalDateTime
│   └── Relations:
│       ├── Patient (1,N) - "Assiste"
│       ├── Medecin (0,N) - "Donne"
│       └── Medicament (0,N) - "Prescrit"
├── Medicament
│   ├── PK: code
│   └── libelle
└── Prescription (classe d'association)
    ├── codeMedicament
    └── nbPrise
```

## Collections MongoDB

- `patients` - Collection des patients
- `medecins` - Collection des médecins
- `consultations` - Collection des consultations
- `medicaments` - Collection des médicaments

## API REST Endpoints

### Patients

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/patients` | Créer un patient |
| GET | `/api/patients` | Récupérer tous les patients |
| GET | `/api/patients/{numeroSS}` | Récupérer un patient par son numéro SS |
| PUT | `/api/patients/{numeroSS}` | Mettre à jour un patient |
| DELETE | `/api/patients/{numeroSS}` | Supprimer un patient |

#### Exemple POST /api/patients
```json
{
  "numeroSS": "123456789012",
  "nomPatient": "Dupont Jean",
  "username": "jean_dupont",
  "password": "password123"
}
```

### Médecins

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/medecins` | Créer un médecin |
| GET | `/api/medecins` | Récupérer tous les médecins |
| GET | `/api/medecins/{matricule}` | Récupérer un médecin par matricule |
| PUT | `/api/medecins/{matricule}` | Mettre à jour un médecin |
| DELETE | `/api/medecins/{matricule}` | Supprimer un médecin |

#### Exemple POST /api/medecins
```json
{
  "matricule": "DOC001",
  "nomMedecin": "Dr. Martin",
  "username": "dr_martin",
  "password": "password123"
}
```

### Médicaments

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/medicaments` | Créer un médicament |
| GET | `/api/medicaments` | Récupérer tous les médicaments |
| GET | `/api/medicaments/{code}` | Récupérer un médicament par code |
| PUT | `/api/medicaments/{code}` | Mettre à jour un médicament |
| DELETE | `/api/medicaments/{code}` | Supprimer un médicament |

#### Exemple POST /api/medicaments
```json
{
  "code": "MED001",
  "libelle": "Aspirin 500mg"
}
```

### Consultations

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/consultations` | Créer une consultation |
| GET | `/api/consultations` | Récupérer toutes les consultations |
| GET | `/api/consultations/{numeroConsultation}` | Récupérer une consultation |
| GET | `/api/consultations/patient/{numeroSS}` | Récupérer les consultations d'un patient |
| GET | `/api/consultations/medecin/{matricule}` | Récupérer les consultations d'un médecin |
| PUT | `/api/consultations/{numeroConsultation}` | Mettre à jour une consultation |
| DELETE | `/api/consultations/{numeroConsultation}` | Supprimer une consultation |
| POST | `/api/consultations/{numeroConsultation}/prescriptions` | Ajouter une prescription |

#### Exemple POST /api/consultations
```json
{
  "numeroConsultation": "CONS001",
  "date": "2026-07-06T14:30:00",
  "patientAssiste": {
    "numeroSS": "123456789012"
  },
  "medecinDonne": {
    "matricule": "DOC001"
  }
}
```

#### Exemple POST /api/consultations/{numeroConsultation}/prescriptions
```json
{
  "codeMedicament": "MED001",
  "nbPrise": 3
}
```

## Réponses HTTP

- **200 OK** : Requête réussie
- **201 Created** : Ressource créée (POST)
- **204 No Content** : Suppression réussie
- **404 Not Found** : Ressource non trouvée
- **400 Bad Request** : Erreur de validation

## Structure des Services

Chaque service inclut les opérations CRUD basiques :
- `create()` - Créer une entité
- `get()` - Récupérer une entité
- `getAll()` - Récupérer toutes les entités
- `update()` - Mettre à jour une entité
- `delete()` - Supprimer une entité

## Relations et Contraintes

1. **Patient → Consultation** (1,N)
   - Relation nommée "Assiste"
   - Un patient peut avoir plusieurs consultations
   - Une consultation concerne un seul patient

2. **Medecin → Consultation** (0,N)
   - Relation nommée "Donne"
   - Un médecin peut donner plusieurs consultations
   - Une consultation peut ne pas avoir de médecin assigné

3. **Medicament ↔ Consultation** (0,N)
   - Relation nommée "Prescrit"
   - Association via la classe `Prescription`
   - Contient le nombre de prises prescrites
