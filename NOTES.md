- @PostMapping --> Endpoint post
- @GetMapping --> Endpoint get
- @RequestBody --> Json data
- @PathVariable --> Variable en paramètre d'entrée du endpoint
- @PutMapping --> Post + Replace
- @DeleteMapping --> Delete
- @Schema --> Ajout sur le openApi
- @NotBlank --> Le champs ne peux pas être vide
- @Document --> 
- @Id --> Permet de définir une clé primaire
- @Indexed -->
- @DBRef --> 
- @Repository --> Annotation pour déclarer une classe repository
- @Component --> 
- @Override --> 
- @Autowired --> 
- @Service --> Annotation pour déclarer une classe service
- @Value -->
- @DisplayName --> 
- @Mock --> 
- @InjectMocks --> 
- @BeforeEach --> 
- @Test --> 
- @ExceptionHandler -->

Une classe record est une classe générique qui possède des informations de base d'une classe


NOTE POUR AMeLIORATION : 

Passer en revue le dossier ressource afin de commenter toutes les lignes et les comprendre

Dans les controlleur il ne faut pas que des dto de base ?

Amélioré le mot de passe

Vérifier si le mdp est bien hasher

Vérifier que la javadoc est partout

Tester tout les endpoints sur postman

Vérifier que les email sont bien unique

Revérifier l'intégralité des fichier, des commentaire, des messages d'erreur, du nom des variables

Vérifier les repository car ils m'ont l'air grave suspect

Se renseigner sur le NoSQL de manière général et son application en Java

Type de retour ne doit-il pas être un DTO également ?

A vérif:

Si je créer un medecin, est-ce que je créer également un User au passage ?