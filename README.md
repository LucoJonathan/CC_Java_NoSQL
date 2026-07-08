# DoctorApp API (Spring Boot + MongoDB + JWT)

API REST avec :
- `ping` pour verifier la communication API
- CRUD `User` (MongoDB NoSQL)
- authentification JWT (`login`)

## 1. Lancer l'application en profil dev

Le fichier `src/main/resources/application-dev.properties` est prevu pour le dev local :
- base `doctorapp_dev`
- logs MongoDB pour observer les requetes executees

Commande :

```bash
SPRING_PROFILES_ACTIVE=dev sh mvnw spring-boot:run
```

## 2. Configuration utile

### application.properties (par defaut)
- `MONGODB_URI` (fallback `mongodb://localhost:27017/doctorapp`)
- `JWT_SECRET`
- `JWT_EXPIRATION_MS`

### application-dev.properties
- `spring.data.mongodb.uri=mongodb://localhost:27017/doctorapp_dev`
- logs DEBUG Mongo :
  - `org.springframework.data.mongodb.core.MongoTemplate`
  - `org.mongodb.driver.protocol.command`

## 3. Endpoints

Base URL locale : `http://localhost:8080`

| Methode | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/api/ping` | Non | Test API (`pong`) |
| POST | `/api/users` | Non | Creer un user |
| POST | `/api/auth/login` | Non | Login et recuperation JWT |
| GET | `/api/users` | Oui (Bearer) | Lister les users |
| GET | `/api/users/{id}` | Oui (Bearer) | Recuperer 1 user |
| GET | `/api/patients/page?page=0&size=10` | Oui (Bearer) | Recuperer une page de patients |
| GET | `/api/medecins/page?page=0&size=10` | Oui (Bearer) | Recuperer une page de medecins |
| PUT | `/api/users/{id}` | Oui (Bearer) | Modifier 1 user |
| DELETE | `/api/users/{id}` | Oui (Bearer) | Supprimer 1 user |

## 4. Workflow test JWT (Bruno ou Postman)

1. **Ping**
   - `GET /api/ping`

2. **Creer un utilisateur**
   - `POST /api/users`
   - Body JSON :
```json
{
  "username": "jonathan",
  "password": "password123"
}
```

3. **Se connecter**
   - `POST /api/auth/login`
   - Body JSON :
```json
{
  "username": "jonathan",
  "password": "password123"
}
```
   - Reponse :
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9....",
  "type": "Bearer"
}
```

4. **Appeler un endpoint protege**
   - Exemple : `GET /api/users`
   - Header :
```http
Authorization: Bearer <token>
```

## 5. Exemples cURL (importables facilement dans Postman/Bruno)

```bash
curl -X GET http://localhost:8080/api/ping
```

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"jonathan","password":"password123"}'
```

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"jonathan","password":"password123"}'
```

```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer <token>"
```

```bash
curl -X GET "http://localhost:8080/api/patients/page?page=0&size=10" \
  -H "Authorization: Bearer <token>"
```

```bash
curl -X GET "http://localhost:8080/api/medecins/page?page=0&size=10" \
  -H "Authorization: Bearer <token>"
```

## 6. Observer les donnees NoSQL

Deux options simples :
1. Lire les logs de l'application (profil `dev`) pour voir les commandes Mongo executees.
2. Ouvrir la base avec un client Mongo (ex: MongoDB Compass) :
   - URI : `mongodb://localhost:27017`
   - Database : `doctorapp_dev`
   - Collection : `users`
