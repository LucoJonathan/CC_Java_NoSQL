# Architecture - Diagramme Logique

## Hiérarchie des Modèles

```
┌─────────────────────────────────────┐
│           User (Parent)             │
│  - id: String (MongoDB _id)        │
│  - username: String (unique)       │
│  - password: String                │
└──────────────┬──────────────────────┘
               │
        ┌──────┴──────┐
        │             │
        ▼             ▼
    ┌────────────┐  ┌──────────────┐
    │  Patient   │  │   Medecin    │
    ├────────────┤  ├──────────────┤
    │ @Document: │  │ @Document:   │
    │ patients   │  │ medecins     │
    ├────────────┤  ├──────────────┤
    │ numeroSS:  │  │ matricule:   │
    │ String(PK) │  │ String(PK)   │
    │nomPatient: │  │ nomMedecin:  │
    │ String     │  │ String       │
    └────────────┘  └──────────────┘
```

## Relation de Consultation

```
         Patient (1)
            │
            │ Assiste (1,N)
            │
      ┌─────▼──────┐
      │Consultation│
      ├────────────┤
      │numeroConsu: │  ┌──────────────┐
      │ String(PK)  ├──┤ Medecin (0,N)│
      │date:        │  │ Donne        │
      │LocalDateTime│  └──────────────┘
      │prescriptions│
      │List<Presc>  │
      └─────┬──────┘
            │
            │ Prescrit (0,N)
            │
       ┌────▼───────────┐
       │ Prescription   │
       ├────────────────┤
       │codeMedicament  │
       │nbPrise         │
       └────────────────┘
            │
            │ references
            │
       ┌────▼──────────┐
       │ Medicament    │
       ├───────────────┤
       │code: String   │
       │(PK)           │
       │libelle: String│
       └────────────────┘
```

## Collections MongoDB

### 1. Collection: `patients`
```json
{
  "_id": ObjectId(...),
  "numeroSS": "123456789012",
  "nomPatient": "Jean Dupont",
  "username": "jean_dupont",
  "password": "$2a$10$..."
}
```

### 2. Collection: `medecins`
```json
{
  "_id": ObjectId(...),
  "matricule": "DOC001",
  "nomMedecin": "Dr. Martin Lefebvre",
  "username": "dr_martin",
  "password": "$2a$10$..."
}
```

### 3. Collection: `medicaments`
```json
{
  "_id": "MED001",
  "code": "MED001",
  "libelle": "Aspirin 500mg"
}
```

### 4. Collection: `consultations`
```json
{
  "_id": "CONS001",
  "numeroConsultation": "CONS001",
  "date": ISODate("2026-07-06T14:30:00Z"),
  "patientAssiste": {
    "$ref": "patients",
    "$id": ObjectId("...")
  },
  "medecinDonne": {
    "$ref": "medecins",
    "$id": ObjectId("...")
  },
  "prescriptions": [
    {
      "codeMedicament": "MED001",
      "nbPrise": 3
    }
  ]
}
```

## Flux des Opérations CRUD

### Pattern Service → Repository → MongoDB

```
Controller
    ↓
Service (PatientService, MedecinService, etc.)
    ↓
Repository (PatientRepository, MedecinRepository, etc.)
    ↓
MongoDB Database
```

## Exemple: Créer une Consultation

```
1. Client HTTP
   ↓
   POST /api/consultations
   {
     "numeroConsultation": "CONS001",
     "date": "2026-07-06T14:30:00",
     "patientAssiste": {"numeroSS": "123456789012"},
     "medecinDonne": {"matricule": "DOC001"}
   }
   
2. ConsultationController
   → createConsultation()
   
3. ConsultationService
   → save(consultation)
   
4. ConsultationRepository
   → MongoDB insert
   
5. Response 200 OK
   ← Consultation créée avec tous les détails
```

## Exemple: Ajouter une Prescription

```
1. Client HTTP
   ↓
   POST /api/consultations/CONS001/prescriptions
   {
     "codeMedicament": "MED001",
     "nbPrise": 3
   }
   
2. ConsultationController
   → addPrescription()
   
3. ConsultationService
   → addPrescriptionToConsultation()
   → Retrieve consultation
   → Add prescription to list
   → Save updated consultation
   
4. ConsultationRepository
   → MongoDB update
   
5. Response 200 OK
   ← Consultation mise à jour
```

## Types de Relations Implémentées

| Relation | Cardinalité | Implémentation | Type |
|----------|-------------|----------------|------|
| Patient → Consultation | 1,N | @DBRef sur patientAssiste | Foreign Key |
| Medecin → Consultation | 0,N | @DBRef sur medecinDonne | Foreign Key |
| Consultation ↔ Medicament | 0,N | Classe Prescription | Association |

## Validations

- `@NotBlank` sur les champs obligatoires
- `@Indexed(unique = true)` sur username et numeroSS
- Les clés primaires sont uniques par défaut MongoDB
- Gestion des relations via @DBRef

## Avantages de l'Approche NoSQL

✅ **Flexibilité** - Structure JSON naturelle
✅ **Scalabilité** - Facilement distribuable
✅ **Performance** - Requêtes optimisées
✅ **Imbrication** - Prescriptions directement dans Consultation
✅ **Absence de migration** - Schéma flexible
