# U4 Progetto Finale — Social Network API

API REST per un piccolo social network, con gestione di utenti, post e like. Progetto sviluppato con Spring Boot, Spring Security (autenticazione JWT) e PostgreSQL, a partire dal lavoro fatto durante il corso su User, Post e Like.

## Regole di autorizzazione

Per ogni operazione che lo richiede ho scelto la regola che mi sembrava più corretta, basandomi sul ruolo di chi chiama, sulla proprietà della risorsa, o su entrambi.

### Cambio ruolo di un utente — `PATCH /api/users/{userId}/role`

Riservato a chi ha già ruolo `MODERATOR` (`@PreAuthorize("hasAuthority('MODERATOR')")`). È una regola basata solo sul ruolo: cambiare i permessi di un altro utente è un'operazione delicata, quindi va riservata a chi ha già un ruolo di fiducia all'interno del social.

### Aggiornamento di un post — `PUT /api/posts/{postId}`

Può farlo l'autore del post oppure un `MODERATOR`. A differenza del cambio ruolo, qui il controllo non può basarsi solo sul ruolo di chi chiama: dipende anche da chi ha scritto quello specifico post, quindi il controllo è fatto nel service (dove il post è già stato caricato dal database) invece che con `@PreAuthorize`. Se il chiamante non è né l'autore né un moderatore, la richiesta viene rifiutata con `403 Forbidden`.

### Like (aggiungi/rimuovi) — `POST` e `DELETE /api/posts/{postId}/likes`

Nessuna regola di autorizzazione oltre a essere autenticati: l'operazione riguarda sempre e solo l'utente che sta chiamando (l'utente viene preso dal token, mai passato come parametro dal client), quindi non serve nessun controllo aggiuntivo — non si può mai mettere o togliere un like a nome di qualcun altro.

### Tutto il resto (registrazione, login, creazione/lettura post)

Nessuna restrizione oltre a essere autenticati per creare un post; login e registrazione sono pubblici per definizione.

## Cosa trovi nella repository

- `postmanScreen/SocialNetwork-API.postman_collection.json` — collezione Postman con tutte le richieste usate per testare l'API (auth, cambio ruolo, post, like), incluse le richieste di test negativo per dimostrare le regole di autorizzazione sopra
- `postmanScreen/screenshots/` — screenshot delle principali richieste/risposte testate con Postman (`Postman/`) e delle tabelle del database (`pgAdmin/`)

---

La collezione Postman, l'impaginazione di questo README e i DTO (payload di richiesta/risposta) sono stati realizzati con l'assistenza di Claude (AI); i contenuti — le scelte tecniche e le motivazioni descritte sopra — sono miei.
