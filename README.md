# Hvordan Gikk Quizen Backend

This Kotlin-based backend handles submission, storage, and statistics for `Hvordan gikk quizen?`. The API exposes
authenticated admin endpoints, public read access to results, and ships OpenAPI/Swagger docs for easy integration. It is
built on Spring Boot 4, packaged with the Maven wrapper, persists data in PostgreSQL via Spring Data JPA, and integrates
with Google OAuth2 plus Slack webhooks for notifications.

## 🔗 Useful Links

### Localhost

- **Swagger UI:** http://localhost:8080/swagger-ui/index.html

### Production

- **Swagger UI:** https://api.hvordangikkquizen.no/swagger-ui/index.html

## Environments

### .env

There are two .env files, one for local development and one for production:

* `.env.local`
* `.env.prod`

They both look like this:

```dotenv
GOOGLE_CLIENT_ID=<id>
GOOGLE_CLIENT_SECRET=<secret>
DATABASE_URL=<url>
DATABASE_USERNAME=<username>
DATABASE_PASSWORD=<password>
SLACK_WEBHOOK_URL=<url>
```
