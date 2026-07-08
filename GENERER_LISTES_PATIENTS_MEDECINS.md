# Generer 20 patients et 20 medecins

Commande unique (genere `patients_medecins_20.json` a la racine) :

```bash
python3 - <<'PY'
import json

prenoms = [
    "Lucas", "Emma", "Noah", "Lea", "Hugo", "Chloe", "Louis", "Manon", "Jules", "Sarah",
    "Theo", "Camille", "Nathan", "Ines", "Ethan", "Lina", "Tom", "Mila", "Gabriel", "Zoey"
]
noms = [
    "Martin", "Bernard", "Thomas", "Petit", "Robert", "Richard", "Durand", "Dubois", "Moreau", "Laurent",
    "Simon", "Michel", "Lefebvre", "Leroy", "Roux", "David", "Bertrand", "Morel", "Fournier", "Girard"
]
specialites = [
    "Cardiologie", "Dermatologie", "Pediatrie", "Neurologie", "Radiologie",
    "Oncologie", "Psychiatrie", "Gynecologie", "Rhumatologie", "Ophtalmologie",
    "Urologie", "Nephrologie", "Gastro-enterologie", "Pneumologie", "Endocrinologie",
    "ORL", "Anesthesie", "Medecine interne", "Urgences", "Chirurgie"
]

patients = []
medecins = []

for i in range(20):
    prenom = prenoms[i]
    nom = noms[i]
    base = f"{prenom.lower()}.{nom.lower()}"

    patients.append({
        "numeroSS": f"1900101000{i+1:02d}",
        "nomPatient": f"{prenom} {nom}",
        "username": f"patient_{i+1:02d}_{base.replace('.', '_')}",
        "email": f"{base}+patient{i+1:02d}@example.com",
        "password": f"Patient{i+1:02d}!"
    })

    medecins.append({
        "matricule": f"DOC{i+1:03d}",
        "nomMedecin": f"Dr {prenom} {nom}",
        "username": f"medecin_{i+1:02d}_{base.replace('.', '_')}",
        "email": f"{base}+medecin{i+1:02d}@example.com",
        "password": f"Medecin{i+1:02d}!",
        "specialite": specialites[i]
    })

with open("patients_medecins_20.json", "w", encoding="utf-8") as f:
    json.dump({"patients": patients, "medecins": medecins}, f, ensure_ascii=False, indent=2)

print("OK: fichier cree -> patients_medecins_20.json")
PY
```

